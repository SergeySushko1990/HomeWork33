package ru.learnUp.learnupjava23.configs;

import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcMutableAclService aclService(
            DataSource dataSource,
            LookupStrategy lookupStrategy,
            EhCacheBasedAclCache aclCache
    ) {
        return new JdbcMutableAclService(
                dataSource,
                lookupStrategy,
                aclCache
        );
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(
                new ConsoleAuditLogger());
    }

    @Bean
    public EhCacheBasedAclCache aclCache(
            EhCacheFactoryBean cacheFactoryBean,
            PermissionGrantingStrategy permissionGrantingStrategy,
            AclAuthorizationStrategy aclAuthorizationStrategy
    ) {
        return new EhCacheBasedAclCache(
                cacheFactoryBean.getObject(),
                permissionGrantingStrategy,
                aclAuthorizationStrategy
        );
    }

    @Bean
    public EhCacheFactoryBean aclEhCacheFactoryBean(EhCacheManagerFactoryBean cacheManager) {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(cacheManager.getObject());
        ehCacheFactoryBean.setCacheName("aclCache");
        return ehCacheFactoryBean;
    }

    @Bean
    public EhCacheManagerFactoryBean aclCacheManager() {
        return new EhCacheManagerFactoryBean();
    }

    @Bean
    public LookupStrategy lookupStrategy(
            DataSource dataSource,
            EhCacheBasedAclCache cache,
            AclAuthorizationStrategy strategy,
            PermissionGrantingStrategy permissionGrantingStrategy
    ) {
        return new BasicLookupStrategy(
                dataSource,
                cache,
                strategy,
                permissionGrantingStrategy
        );
    }

    @Bean
    public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler(
            JdbcMutableAclService aclService
    ) {
        DefaultMethodSecurityExpressionHandler expressionHandler
                = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator
                = new AclPermissionEvaluator(aclService);
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

}

package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.Client;
import ru.learnUp.learnupjava23.dao.entity.Role;
import ru.learnUp.learnupjava23.dao.entity.User;
import ru.learnUp.learnupjava23.dao.repository.RoleRepository;
import ru.learnUp.learnupjava23.dao.service.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserView {

    private String login;
    private String password;
    private Set<RoleView> roles;

    public List<UserView> mapToView(List<User> users) {
        List<UserView> views = new ArrayList<>();
        for (User user : users) {
            UserView view = new UserView();
            Set<RoleView> roles = user.getRoles().stream()
                    .map(role -> new RoleView(role.getRole()))
                    .collect(Collectors.toSet());
            view.setLogin(user.getUsername());
            view.setPassword(user.getPassword());
            view.setRoles(roles);
            views.add(view);
        }
        return views;
    }

    public UserView mapToView(User user) {
        UserView view = new UserView();
        Set<RoleView> roles = user.getRoles().stream()
                .map(role -> new RoleView(role.getRole()))
                .collect(Collectors.toSet());
        view.setLogin(user.getUsername());
        view.setPassword(user.getPassword());
        view.setRoles(roles);
        return view;
    }

    public User mapFromView(UserView view, RoleRepository roleRepository, ClientService clientService) {
        User user = new User();
        Client client = new Client();

        client.setFullName(view.getLogin());
        client.setUser(null);
        clientService.createClient(client);
        Set<Role> roles = view.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getRole()))
                .collect(Collectors.toSet());
        user.setUsername(view.getLogin());
        user.setPassword(view.getPassword());
        user.setRoles(roles);
        user.setClient(clientService.getClientByName(view.getLogin()));
        return user;
    }

}

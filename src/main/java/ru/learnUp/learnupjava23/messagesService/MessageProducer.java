package ru.learnUp.learnupjava23.messagesService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record MessageProducer(String topicName, JmsTemplate jmsTemplate) {

        public MessageProducer(
                @Value("${topic.name}") String topicName,
                JmsTemplate jmsTemplate) {
            this.topicName = topicName;
            this.jmsTemplate = jmsTemplate;
        }

        public void sendMessage (String message){
            try {
                log.debug("Attempting Send message to Topic: " + topicName);
                jmsTemplate.convertAndSend(topicName, message);
            } catch (Exception e) {
                log.error("Receive Exception during send Message: ", e);
            }
        }
}

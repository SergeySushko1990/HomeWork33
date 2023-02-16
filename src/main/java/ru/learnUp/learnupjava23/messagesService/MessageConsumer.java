package ru.learnUp.learnupjava23.messagesService;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

@Component
@Slf4j
public class MessageConsumer implements MessageListener {

    private final FileDirectoryProvider fileDirectoryProvider;

    public MessageConsumer(FileDirectoryProvider fileDirectoryProvider) {
        this.fileDirectoryProvider = fileDirectoryProvider;
    }

    @JmsListener(destination = "${topic.name}")
    @Override
    public void onMessage(Message message) {
        try{
            ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
            String msg = textMessage.getText();
            log.info("Received Message: "+ msg);
        } catch(Exception e) {
            log.error("Received Exception : "+ e);
        }
    }

    public void writeIntoFile(String fileName, String text) throws IOException {
        fileDirectoryProvider.writeString(fileName, text);
    }

}

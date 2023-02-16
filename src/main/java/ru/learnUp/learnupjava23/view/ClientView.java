package ru.learnUp.learnupjava23.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.learnUp.learnupjava23.dao.entity.Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ClientView {

//    private Long id;

    private String fullName;

    private Date birthDate;

    public ClientView mapToView(Client client) {
        ClientView view = new ClientView();
//        view.setId(client.getId());
        view.setFullName(client.getFullName());
        view.setBirthDate(client.getBirthDate());
        return view;
    }

    public List<ClientView> mapToViewList(List<Client> clients) {
        List<ClientView> views = new ArrayList<>();
        clients.forEach(client -> {
            ClientView view = new ClientView();
//            view.setId(client.getId());
            view.setFullName(client.getFullName());
            view.setBirthDate(client.getBirthDate());
            views.add(view);
        });
        return views;
    }


    public Client mapFromView(ClientView view) {
        Client client = new Client();
//        client.setId(view.getId());
        client.setFullName(view.getFullName());
        client.setBirthDate(view.getBirthDate());
        return client;
    }
}

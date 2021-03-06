package dslab.mailbox;

import dslab.model.MailEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MailStorage {

    private final String domain;
    private final ConcurrentHashMap<String, List<MailEntity>> storage = new ConcurrentHashMap<>();

    public MailStorage(String domain){
        this.domain = domain;
    }

    public void save(MailEntity mail) {
        for (String address : mail.getTo()) {
            String[] parts = address.split("@");

           if (domain.equals(parts[1])){
                storage.putIfAbsent(parts[0], Collections.synchronizedList(new ArrayList<>()));
                storage.get(parts[0]).add(mail); // ASK does this need synchronization?
           }
        }
    }

    public List<MailEntity> retrieve(String user) {
        return storage.get(user);
    }

    public String toString() {
        return storage.toString();
    }

}

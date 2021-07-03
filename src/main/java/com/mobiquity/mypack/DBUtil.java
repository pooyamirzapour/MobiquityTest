package com.mobiquity.mypack;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DBUtil {

    Set<Client> clients = new HashSet<>();

    public Boolean insert(Client client) {
        clients.add(client);
        System.out.println("insert done");
        Iterator<Client> it = clients.iterator();
        if (it.hasNext()) {
            Client next = it.next();
            return next.getName().equals(client.getName());
        }
        return false;


    }
}

package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

import java.util.List;

@Component
@Profile("init")
public class initContactsLoader implements ContactsLoader {
    @Value("${app.contacts.file}")
    private String contacts;

    @Override
    public List<Contact> loadContacts() {
        System.out.println("Инициализация контактов через файл!");
        List<Contact> listContacts = new ArrayList<>();

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(contacts))) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arrayContact = line.split(";");
                Contact contact = new Contact(arrayContact[0].trim(), arrayContact[1].trim(), arrayContact[2].trim());
                listContacts.add(contact);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listContacts;
    }
}

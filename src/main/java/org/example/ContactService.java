package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactService {
    @Value("${app.contacts.savefile}")
    private String fileListContacts;
    private List<Contact> contacts = new ArrayList<>();

    private ContactsLoader loader;
    public ContactService(ContactsLoader loader) {
        this.loader = loader;
    }

    @PostConstruct
    public void initContacts() {
        System.out.println("Инициализация контактов через файл!");
        contacts = loader.loadContacts();
    }

    public String listContacts() {
        return contacts.toString();
    }

    public void addContact(String contact) {
        String[] arrayContact = contact.split(";");
        if (arrayContact.length == 3) {
            contacts.add(new Contact(arrayContact[0].trim(), arrayContact[1].trim(), arrayContact[2].trim()));
            System.out.println("Контакт добавлен!");
        }
        else {
            System.out.println("Неверный формат ввода! Используй формат (Ф.И.О.;номер телефона;email");
        }
    }

    public void removeContact(String email) {
        boolean flag = false;
        for (Contact contact : contacts) {
            if (contact.getEmail().equals(email)) {
                contacts.remove(contact);
                flag = true;
                break;
            }
        }
        if (flag) {
            System.out.println("Контакт успешно удален!");
        }
        else {
            System.out.println("Контакт с таким email не найден!");
        }
    }

    public void saveContactToFile() {
        try (FileWriter fileWriter = new FileWriter(fileListContacts)) {
            for (Contact contact : contacts) {
                fileWriter.write(contact.toString() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

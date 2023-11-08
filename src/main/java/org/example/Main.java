package org.example;

import config.AppConfig;
import config.InitAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, InitAppConfig.class);
        ContactService service = context.getBean(ContactService.class);
        System.out.println("Чтобы добавить контакт в список контактов, введите команду \"add\" и контакт в формате" +
                "\"ИМЯ;ТЕЛЕФОН;EMAIL\"" + "\n" + "Чтобы посмотреть весь список контактов, введите команду \"show contacts\"" + "\n" +
                "Чтобы удалить контакт, введите команду \"delete\" и email контакта, который хотите удалить" + "\n" +
                "Чтобы сохранить контакты в файл, введите команду \"save\"");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        while (true) {
            try {
                command = reader.readLine();

                if (command.equals("add")) {
                    String fullName;
                    System.out.println("Введите контакт в формате \"ИМЯ;ТЕЛЕФОН;EMAIL\"");
                    fullName = reader.readLine();
                    service.addContact(fullName);
                }
                else if (command.equals("show contacts")) {
                    System.out.println(service.listContacts());
                }
                else if (command.equals("delete")) {
                    String email;
                    System.out.println("Введите email!");
                    email = reader.readLine();
                    service.removeContact(email);
                } else if (command.equals("save")) {
                    service.saveContactToFile();
                } else if (command.equals("exit")) {
                    System.out.println("Завершение работы приложения!");
                    break;
                }
                else {
                    System.out.println("Неверная команда!\n" + "Список доступных команд:");
                    System.out.println("add - добавить контакт в список контактов в формате \"ИМЯ;ТЕЛЕФОН;EMAIL\"\n" +
                            "show contacts - посмотреть весь список контактов\n" +
                            "delete - удалить контакт (для удаления контакта нужно ввести email этого контакта)\n" +
                            "save - записать контакты в файл");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
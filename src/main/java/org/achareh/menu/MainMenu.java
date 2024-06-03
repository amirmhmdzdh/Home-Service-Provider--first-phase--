package org.achareh.menu;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("========================================= ");
            System.out.println("       * Welcome to Home Service *        ");
            System.out.println("========================================= ");
            System.out.println("1) Customer user panel                    ");
            System.out.println("2) Admin user panel                       ");
            System.out.println("3) specialist user panel                  ");
            System.out.println("0) Exit                                   ");
            System.out.println("------------------------------------------");
            try {
                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> CustomerMenu.customerPanel();

                    case "2" -> AdminMenu.adminPanel();

                    case "3" -> SpecialistMenu.specialistPanel();

                    case "0" -> {
                        return;
                    }
                    default -> System.out.println(">>>> Wrong entry! <<<<");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


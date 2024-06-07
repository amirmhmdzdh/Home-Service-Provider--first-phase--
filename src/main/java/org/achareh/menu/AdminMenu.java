package org.achareh.menu;

import org.achareh.model.service.MainService;
import org.achareh.model.service.SubService;
import org.achareh.model.user.Admin;
import org.achareh.model.user.Specialist;
import org.achareh.model.user.enums.SpecialistStatus;
import org.achareh.service.*;
import org.achareh.utility.ApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminService adminService = ApplicationContext.getAdminService();
    private static final MainServiceService mainServiceService = ApplicationContext.getMainServiceService();
    private static final SubServicesService subServicesService = ApplicationContext.getSubServicesService();
    private static final SpecialistService specialistService = ApplicationContext.getSpecialistService();

    public static void adminPanel() {

        while (true) {
            try {
                System.out.println("========================================");
                System.out.println("        * Admin MENU *                  ");
                System.out.println("========================================");
                System.out.println("1) Sign In                              ");
                System.out.println("0) EXIT                                 ");
                System.out.println("----------------------------------------");

                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> adminSignIn();

                    case "0" -> {
                        return;
                    }
                    default -> System.out.println(" <<< Wrong entry >>>");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void servicePanel() {

        while (true) {
            try {
                System.out.println("============================================= ");
                System.out.println("              * Service MENU *                ");
                System.out.println("============================================= ");
                System.out.println("1) Create Main Service                        ");
                System.out.println("2) Create Sub Service                         ");
                System.out.println("3) Update Sub Service                         ");
                System.out.println("4) Confirm Specialist Information             ");
                System.out.println("5) print Specialist Image                     ");
                System.out.println("6) giveJob                                    ");
                System.out.println("7) Delete the Specialist from the sub-service ");
                System.out.println("0) EXIT                                       ");
                System.out.println("--------------------------------------------- ");

                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> createMainService();

                    case "2" -> createSubService();

                    case "3" -> updateSubService();

                    case "4" -> confirmInformation();

                    case "5" -> printImage();

                    case "6" -> giveJob();

                    case "7" -> deleteSpecialistService();

                    case "0" -> {
                        return;
                    }
                    default -> System.out.println(" <<< Wrong entry >>>");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void adminSignIn() {
        System.out.println("______SignIn______");
        System.out.println();
        System.out.println("Email ->> ");
        String email = scanner.nextLine();
        System.out.println("Password ->> ");
        String password = scanner.nextLine();
        Optional<Admin> optionalAdmin = adminService.findByEmailAndPassword(email, password);

        if (optionalAdmin.isPresent()) {
            System.out.println(">>>>  USER PANEL <<<<");
            servicePanel();
        } else
            System.out.println(">>>> Username or password is incorrect <<<<");
    }

    public static void createMainService() {

        System.out.println("______MainService______");
        System.out.println();
        System.out.println("Enter Main Service ->> ");
        String main = scanner.nextLine();

        List<MainService> all = mainServiceService.findAll();
        boolean subServiceExists = all.stream()
                .anyMatch(m -> m.getName().equals(main));
        if (subServiceExists) {
            System.out.println("Your Main service already exists.");
            return;
        }
        MainService mainService = MainService.builder()
                .name(main)
                .build();
        mainServiceService.saveOrUpdate(mainService);
    }

    public static void createSubService() {
        try {
            showMainService();
            Long select = scanner.nextLong();
            scanner.nextLine();

            Optional<MainService> optionalMainService = mainServiceService.findById(select);
            if (optionalMainService.isPresent()) {
                MainService mainService = optionalMainService.get();
                System.out.println("Enter Sub Service:");
                String nameOfSub = scanner.nextLine();
                System.out.println("Description:");
                String description = scanner.nextLine();
                System.out.println("Base Price:");
                Long basePrice = scanner.nextLong();

                List<SubService> all = subServicesService.findAll();
                boolean subServiceExists = all.stream()
                        .anyMatch(s -> s.getName().equals(nameOfSub));
                if (subServiceExists) {
                    System.out.println("Your sub service already exists.");
                    return;
                }

                SubService subService = SubService.builder()
                        .name(nameOfSub)
                        .description(description)
                        .basePrice(basePrice)
                        .mainService(mainService)
                        .build();

                subServicesService.saveOrUpdate(subService);
            } else {
                System.out.println("Main service not found.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showMainService() {
        System.out.println("______SubService______");
        System.out.println();
        System.out.println("Choose your desired service:");
        mainServiceService.findAll().stream()
                .map(mainService -> mainService.getId() + ") " + mainService.getName())
                .forEach(System.out::println);
    }

    public static void confirmInformation() {
        List<Specialist> all = specialistService.findAll();
        List<String> newSpecialists = new ArrayList<>();
        all.stream()
                .filter(specialist -> specialist.getStatus() == SpecialistStatus.NEW)
                .forEach(specialist -> newSpecialists.add("id:" + specialist.getId() + " - firstName: "
                        + specialist.getFirstName() + " - lastName: " + specialist.getLastName() + " - Email: "
                        + specialist.getEmail() + " - Status: " + specialist.getStatus()));
        try {
            if (!newSpecialists.isEmpty()) {
                for (String item : newSpecialists) {
                    System.out.println("Choose Specialist id " + item);
                }
                System.out.println("Select id to Confirm ->> ");
                Long select = scanner.nextLong();
                scanner.nextLine();
                Optional<Specialist> byId = specialistService.findById(select);
                if (byId.isPresent()) {
                    Specialist specialist = byId.get();
                    specialist.setStatus(SpecialistStatus.CONFIRMED);
                    specialistService.saveOrUpdate(specialist);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            scanner.nextLine();
        }
    }

    public static void giveJob() {

        List<SubService> subServiceList = subServicesService.findAll();
        List<Specialist> newSpecialists = specialistService.findAll().stream()
                .filter(specialist -> specialist.getStatus() == SpecialistStatus.CONFIRMED)
                .toList();

        List<String> specialistStrings = new ArrayList<>();
        newSpecialists.forEach(specialist -> specialistStrings.add("id:" + specialist.getId() + " - firstName: "
                + specialist.getFirstName() + " - lastName: " + specialist.getLastName() + " - Email: "
                + specialist.getEmail() + " - Status: " + specialist.getStatus()));
        try {
            System.out.println("Available subServices: ");
            for (int i = 0; i < subServiceList.size(); i++) {
                System.out.println((i + 1) + ". " + subServiceList.get(i));
            }
            System.out.println("Select a subService (enter the number): ");
            int selectedSubService = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Available specialists: ");
            for (int i = 0; i < specialistStrings.size(); i++) {
                System.out.println((i + 1) + ". " + specialistStrings.get(i));
            }
            System.out.println("Select a specialist (enter the number): ");
            int selectedSpecialist = scanner.nextInt();
            scanner.nextLine();

            if (selectedSubService > 0 && selectedSubService <= subServiceList.size()
                    && selectedSpecialist > 0 && selectedSpecialist <= specialistStrings.size()) {
                SubService subService = subServiceList.get(selectedSubService - 1);
                Specialist specialist = newSpecialists.get(selectedSpecialist - 1);
                specialist.addSubServices(subService);
                specialistService.saveOrUpdate(specialist);
            } else {
                System.out.println("Invalid selection!");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void deleteSpecialistService() {

        List<Specialist> specialistList = specialistService.findAll();
        List<String> newSpecialists = new ArrayList<>();
        specialistList.stream()
                .filter(specialist -> specialist.getSubServicesList() != null)
                .toList()
                .forEach(specialist -> newSpecialists.add(" - firstName: " + specialist.getFirstName() +
                        " - lastName: " + specialist.getLastName() + " - Email: "
                        + specialist.getEmail()));

        System.out.println("Available specialists: ");
        for (int i = 0; i < newSpecialists.size(); i++) {
            System.out.println((i + 1) + ". " + newSpecialists.get(i));
        }
        System.out.println("Select a specialist (enter the number): ");
        int selectedSpecialist = scanner.nextInt();
        scanner.nextLine();
        Specialist specialist = specialistList.get(selectedSpecialist - 1);

        List<SubService> subServiceList = specialist.getSubServicesList();

        System.out.println("Available subServices: ");
        for (int i = 0; i < subServiceList.size(); i++) {
            System.out.println((i + 1) + ". " + subServiceList.get(i));
        }
        System.out.println("Select a subService (enter the number): ");
        int selectedSubService = scanner.nextInt();
        scanner.nextLine();

        if (selectedSubService > 0 && selectedSubService <= subServiceList.size()) {
            SubService subService = subServiceList.get(selectedSubService - 1);

            subService.getSpecialistList().remove(specialist);
            subServicesService.saveOrUpdate(subService);
            System.out.println("Specialist removed from the sub-service successfully.");
        }
    }

    public static void updateSubService() {

        List<SubService> subServiceList = subServicesService.findAll();

        System.out.println("Available SubService to Update :");
        for (int i = 0; i < subServiceList.size(); i++) {
            System.out.println((i + 1) + ". " + subServiceList.get(i));
        }
        System.out.println("Select an subService (enter the number): ");
        int selectedSub = scanner.nextInt();
        scanner.nextLine();

        if (selectedSub > 0 && selectedSub <= subServiceList.size()) {
            SubService subService = subServiceList.get(selectedSub - 1);
            System.out.println("Description ->> ");
            String description = scanner.nextLine();
            subService.setDescription(description);

            System.out.println("base Price ->> ");
            Long bestPrice = scanner.nextLong();
            subService.setBasePrice(bestPrice);

            subServicesService.saveOrUpdate(subService);
        }
    }

    public static void printImage() {
        List<Specialist> newSpecialists = specialistService.findAll().stream()
                .filter(specialist -> specialist.getStatus() == SpecialistStatus.CONFIRMED)
                .toList();

        List<String> specialistStrings = new ArrayList<>();
        newSpecialists.forEach(specialist -> specialistStrings.add(" - firstName: "
                + specialist.getFirstName() + " - lastName: " + specialist.getLastName() + " - Email: "
                + specialist.getEmail() + " - Status: " + specialist.getStatus()));

        System.out.println("Available specialists: ");
        for (int i = 0; i < specialistStrings.size(); i++) {
            System.out.println((i + 1) + ". " + specialistStrings.get(i));
        }
        System.out.println("Select a specialist (enter the number): ");
        int selectedSpecialist = scanner.nextInt();
        scanner.nextLine();

        if (selectedSpecialist > 0 && selectedSpecialist <= specialistStrings.size()) {
            Specialist specialist = newSpecialists.get(selectedSpecialist - 1);

            System.out.println("Specify the address of your storage location: ");
            String filePath = scanner.nextLine();
            try (FileOutputStream image = new FileOutputStream(filePath)) {
                image.write(specialist.getImage());
                System.out.println("Photo saved");
            } catch (IOException e) {
                System.out.println("Error saving photo: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid specialist selection. Please try again.");
        }
    }
}










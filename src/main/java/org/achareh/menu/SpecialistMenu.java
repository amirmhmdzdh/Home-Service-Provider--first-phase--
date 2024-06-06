package org.achareh.menu;

import org.achareh.model.offer.Offer;
import org.achareh.model.offer.enums.OfferStatus;
import org.achareh.model.order.Orders;
import org.achareh.model.order.enums.OrderStatus;
import org.achareh.model.user.Specialist;
import org.achareh.model.user.enums.Role;
import org.achareh.model.user.enums.SpecialistStatus;
import org.achareh.service.OfferService;
import org.achareh.service.OrderService;
import org.achareh.service.SpecialistService;
import org.achareh.utility.ApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpecialistMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SpecialistService specialistService = ApplicationContext.getSpecialistService();
    private static final OrderService orderService = ApplicationContext.getOrderService();
    private static final OfferService offerService = ApplicationContext.getOfferService();
    private static Specialist specialist1;

    public static void specialistPanel() {
        while (true) {
            System.out.println("=========================================");
            System.out.println("        * Specialist MENU *              ");
            System.out.println("=========================================");
            System.out.println("1) Sign Up                               ");
            System.out.println("2) Sign In                               ");
            System.out.println("0) EXIT                                  ");
            System.out.println("-----------------------------------------");
            try {
                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> specialistSignUp();

                    case "2" -> specialistSignIn();

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

    public static void userPanel() {
        while (true) {
            System.out.println("=========================================");
            System.out.println("              * User MENU *              ");
            System.out.println("=========================================");
            System.out.println("1) New Offers                            ");
            System.out.println("2) Edit Password                         ");
            System.out.println("0) EXIT                                  ");
            System.out.println("---------------------------------------- ");
            try {
                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> newOffers();

                    case "2" -> editePassword();

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

    public static void specialistSignUp() {

        System.out.println(" Dear user, to use the site's facilities first log in to your account : ");
        System.out.println("-______SIGNUP______-");
        System.out.println();
        System.out.println("First Name  ->> ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name ->> ");
        String lastName = scanner.nextLine();
        System.out.println("Email ->> ");
        String email = scanner.nextLine();
        System.out.println("Password ->> ");
        String password = scanner.nextLine();
        System.out.println("Please upload a personal photo");
        String imagePath = scanner.nextLine();

        if (!imagePath.matches(".*\\.(jpg|jpeg)$")) {
            System.out.println("Invalid file format. Only JPG and JPEG formats are supported.");
        } else {
            try {
                byte[] imageData = Files.readAllBytes(Paths.get(imagePath));

                if (imageData.length > 300 * 1024) {
                    System.out.println("The desired photo size is not supported!");
                } else {
                    System.out.println("The photo has been read successfully and is ready to use");
                    Specialist specialist = Specialist.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .password(password)
                            .credit(0L)
                            .role(Role.SPECIALIST)
                            .status(SpecialistStatus.NEW)
                            .registrationTime(LocalDateTime.now())
                            .star(0.0)
                            .image(imageData)
                            .build();
                    specialistService.saveOrUpdate(specialist);
                    System.out.println("it is registered . Wait for the information to be confirmed.");
                }
            } catch (IOException e) {
                System.out.println("Error in reading the photo: " + e.getMessage());
            }
        }
    }


    public static void specialistSignIn() {
        System.out.println("__-____SignIn_____-_");
        System.out.println();
        System.out.println("Email ->> ");
        String email = scanner.nextLine();
        System.out.println("Password ->> ");
        String password = scanner.nextLine();
        Optional<Specialist> valid = specialistService.findByEmailAndPassword(email, password);
        if (valid.isPresent()) {
            specialist1 = valid.get();

            if (specialist1.getStatus() == SpecialistStatus.NEW) {
                System.out.println("Please be patient until your information is confirmed");
            } else {
                System.out.println(">>>> Welcome to your user panel <<<<");
                userPanel();
            }
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    public static void editePassword() {
        System.out.println("______edite Password______");
        System.out.println();
        System.out.println("Email ->> ");
        String email = scanner.nextLine();
        System.out.println("old Password ->> ");
        String oldPassword = scanner.nextLine();
        System.out.println("new Password ->> ");
        String newPassword = scanner.nextLine();

        Optional<Specialist> optionalSpecialist = specialistService.findAll().stream()
                .filter(c -> email.equals(c.getEmail()) && oldPassword.equals(c.getPassword()))
                .findFirst();

        if (optionalSpecialist.isPresent()) {
            Specialist specialist = optionalSpecialist.get();
            specialist.setPassword(newPassword);
            specialistService.saveOrUpdate(specialist);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Your Email or old password is not Correct .");
        }
    }


    public static void newOffers() {

        List<Orders> orders = orderService.findAll().stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.WAITING_FOR_SPECIALIST_SUGGESTION)
                .toList();
        List<String> newOrder = new ArrayList<>();
        orders.forEach(o -> newOrder.add("id:" + o.getId() +
                "\n - subService: " + o.getSubServices().getName() +
                "\n - mainService: " + o.getSubServices().getMainService().getName() +
                "\n - ExecutionTime: " + o.getExecutionTime() + " - EndTime: " + o.getEndTime() +
                "\n - ProposedPrice: " + o.getProposedPrice() + " - Description: " + o.getDescription() +
                "\n - Name: " + o.getCustomer().getFirstName() + " - LastName: " + o.getCustomer().getLastName() +
                "\n - Province " + o.getAddress().getProvince() + " | " + o.getAddress().getCity()));

        System.out.println("new Orders: ");
        for (int i = 0; i < newOrder.size(); i++) {
            System.out.println((i + 1) + ". " + newOrder.get(i));
        }
        System.out.println("Select a Orders (enter the number): ");
        int selectedOrders = scanner.nextInt();
        scanner.nextLine();

        if (selectedOrders > 0 && selectedOrders <= newOrder.size()) {
            Orders orders1 = orders.get(selectedOrders - 1);
            System.out.println("ProposedPrice: ");
            Long proposed = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Execution time (yyyy-MM-dd HH:mm) ->");
            String executionTimeString = scanner.nextLine();
            LocalDateTime executionTime = LocalDateTime.parse(executionTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.println("End time (yyyy-MM-dd HH:mm) ->");
            String endTimeString = scanner.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            Offer offer = Offer.builder()
                    .orders(orders1)
                    .proposedPrice(proposed)
                    .executionTime(executionTime)
                    .endTime(endTime)
                    .offerStatus(OfferStatus.WAITING)
                    .specialist(specialist1)
                    .sendTime(LocalDateTime.now())
                    .build();
            offerService.saveOrUpdate(offer);
            orders1.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
            orderService.saveOrUpdate(orders1);
        } else
            System.out.println(">>>Invalid Input");
    }
}
package org.achareh.menu;

import org.achareh.model.address.Address;
import org.achareh.model.comment.Comment;
import org.achareh.model.offer.Offer;
import org.achareh.model.offer.enums.OfferStatus;
import org.achareh.model.order.Orders;
import org.achareh.model.order.enums.OrderStatus;
import org.achareh.model.service.MainService;
import org.achareh.model.service.SubService;
import org.achareh.model.user.Customer;
import org.achareh.model.user.Specialist;
import org.achareh.model.user.enums.Role;
import org.achareh.service.*;
import org.achareh.utility.ApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerService customerService = ApplicationContext.getCustomerService();
    private static final MainServiceService mainServiceService = ApplicationContext.getMainServiceService();
    private static final SubServicesService subServicesService = ApplicationContext.getSubServicesService();
    private static final OrderService orderService = ApplicationContext.getOrderService();
    private static final OfferService offerService = ApplicationContext.getOfferService();
    private static final SpecialistService specialistService = ApplicationContext.getSpecialistService();
    private static final CommentService commentService = ApplicationContext.getCommentService();
    private static Customer customer;
    private static Orders orders;


    public static void customerPanel() {
        while (true) {
            System.out.println("========================================");
            System.out.println("           * Customer MENU *            ");
            System.out.println("========================================");
            System.out.println("1) Sign Up                              ");
            System.out.println("2) Sign In                              ");
            System.out.println("0) EXIT                                 ");
            System.out.println("----------------------------------------");
            try {
                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> customerSignUp();

                    case "2" -> customerSignIn();

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

    public static void customerOrder() {
        while (true) {
            System.out.println("========================================");
            System.out.println("            * Order MENU *              ");
            System.out.println("========================================");
            System.out.println("1) Edit Password                        ");
            System.out.println("2) Watch and Order                      ");
            System.out.println("3) Track orders                         ");
            System.out.println("4) Notification of status and payment   ");
            System.out.println("5) Register comments                    ");
            System.out.println("0) EXIT                                 ");
            System.out.println("----------------------------------------");
            try {
                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> editePassword();

                    case "2" -> watchAndOrder();

                    case "3" -> trackOrders();

                    case "4" -> notificationOfStatusAndPayment();

                    case "5" -> comments();

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

    public static void customerSignUp() {

        System.out.println(" Dear user, to use the site's facilities, first log in to your account : ");
        System.out.println("______SIGNUP______");
        System.out.println();
        System.out.println("First Name  ->> ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name ->> ");
        String lastName = scanner.nextLine();
        System.out.println("Email ->> ");
        String email = scanner.nextLine();
        System.out.println("Password ->> ");
        String password = scanner.nextLine();
        System.out.println("Province ->> ");
        String province = scanner.nextLine();
        System.out.println("City ->> ");
        String city = scanner.nextLine();
        System.out.println("avenue ->> ");
        String avenue = scanner.nextLine();
        System.out.println("houseNumber");
        String houseNumber = scanner.nextLine();
        System.out.println("How much do you charge your wallet?");
        Long charge = scanner.nextLong();
        Address address = Address.builder()
                .province(province)
                .city(city)
                .avenue(avenue)
                .houseNumber(houseNumber)
                .build();

        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(Role.CUSTOMER)
                .registrationTime(LocalDateTime.now())
                .credit(charge)
                .build();
        customer.addAddress(address);
        try {
            customerService.saveOrUpdate(customer);
        } catch (Exception e) {
            System.out.println("An error occurred while saving the customer: " + e.getMessage());
        }
    }


    public static void customerSignIn() {
        System.out.println("______SignIn______");
        System.out.println();
        System.out.println("Email ->> ");
        String email = scanner.nextLine();
        System.out.println("Password ->> ");
        String password = scanner.nextLine();
        Optional<Customer> optionalCustomer = customerService.findByEmailAndPassword(email, password);

        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
            System.out.println(">>>> Welcome to your user panel <<<<");
            customerOrder();
        } else
            System.out.println(">>>> Username or password is incorrect <<<<");
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

        Optional<Customer> optionalCustomer = customerService.findAll().stream()
                .filter(c -> email.equals(c.getEmail()) && oldPassword.equals(c.getPassword()))
                .findFirst();

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setPassword(newPassword);
            customerService.saveOrUpdate(customer);
        } else {
            System.out.println("Your Email or old password is not Correct .");
        }
    }

    public static void watchAndOrder() {
        try {
            List<MainService> mainServices = mainServiceService.findAll().stream().toList();
            List<Address> addresses = customer.getAddressList().stream().toList();

            System.out.println("Available mainServices: ");
            for (int i = 0; i < mainServices.size(); i++) {
                System.out.println((i + 1) + ". " + mainServices.get(i));
            }
            System.out.println("Select a mainService (enter the number): ");
            long selectedMainService = scanner.nextLong();
            scanner.nextLine();
            List<SubService> subServices = subServicesService.getSubServicesByMainServiceId(selectedMainService);
            System.out.println("Available subServices: ");
            for (int i = 0; i < subServices.size(); i++) {
                System.out.println((i + 1) + ". " + subServices.get(i));
            }
            System.out.println("Select a subService (enter the number): ");
            int selectedSubService = scanner.nextInt();
            scanner.nextLine();

            if (selectedSubService > 0 && selectedSubService <= subServices.size()
                    && selectedMainService > 0 && selectedMainService <= mainServices.size()) {
                SubService subService = subServices.get(selectedSubService - 1);

                System.out.println("Available addresses: ");
                for (int i = 0; i < addresses.size(); i++) {
                    System.out.println((i + 1) + ". " + addresses.get(i));
                }
                String answer = "";
                while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                    System.out.println("Is this address correct? (yes/no)");
                    answer = scanner.nextLine();
                }
                if (answer.equalsIgnoreCase("no")) {
                    System.out.println("Please update your information");
                } else {
                    System.out.println("Select an address (enter the number): ");
                    int selectedAddress = scanner.nextInt();
                    scanner.nextLine();
                    Address address = addresses.get(selectedAddress - 1);

                    System.out.println("Proposed price ->");
                    Long proposedPrice = scanner.nextLong();
                    scanner.nextLine();
                    if (proposedPrice >= subService.getBasePrice()) {
                        System.out.println("Description ->");
                        String description = scanner.nextLine();
                        System.out.println("Execution time (yyyy-MM-dd HH:mm) ->");
                        String executionTimeString = scanner.nextLine();
                        LocalDateTime executionTime = LocalDateTime
                                .parse(executionTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        if (executionTime.isAfter(LocalDateTime.now())) {
                            System.out.println("End time (yyyy-MM-dd HH:mm) ->");
                            String endTimeString = scanner.nextLine();
                            LocalDateTime endTime = LocalDateTime
                                    .parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                            if (endTime.isAfter(executionTime)) {
                                orders = Orders.builder()
                                        .subServices(subService)
                                        .address(address)
                                        .proposedPrice(proposedPrice)
                                        .description(description)
                                        .executionTime(executionTime)
                                        .endTime(endTime)
                                        .customer(customer)
                                        .orderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SUGGESTION)
                                        .build();
                                List<Orders> all = orderService.findAll();
                                boolean orderServiceExists = all.stream()
                                        .anyMatch(o -> o.getExecutionTime().equals(executionTime));
                                if (orderServiceExists) {
                                    System.out.println("Your order already exists.");
                                    return;
                                }


                                orderService.saveOrUpdate(orders);
                            } else
                                System.out.println("Invalid endTime");
                        } else
                            System.out.println("invalid executionTime");
                    } else
                        System.out.println("It is less than the base amount");
                }
            }

        } catch (
                NoSuchElementException e) {
            System.out.println("Invalid input.");
        }
    }

    public static void trackOrders() {

        List<Offer> offerList = offerService.findAll().stream()
                .filter(offer -> offer.getOfferStatus() == OfferStatus.WAITING)
                .toList();
        List<String> newOffer = new ArrayList<>();
        offerList.forEach(o -> newOffer.add("id:" + o.getId() +
                "\n -send offer: " + o.getSendTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\n - nameSpecialist: " + o.getSpecialist().getFirstName() + o.getSpecialist().getLastName() +
                "\n - proposedPrice: " + o.getProposedPrice() +
                "\n - ExecutionTime: " + o.getExecutionTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " - EndTime: " + o.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        System.out.println("new Offers: ");
        for (int i = 0; i < newOffer.size(); i++) {
            System.out.println((i + 1) + ". " + newOffer.get(i));
        }
        System.out.println("Select a Offer (enter the number): ");
        int selectedOffer = scanner.nextInt();
        scanner.nextLine();

        if (selectedOffer > 0 && selectedOffer <= newOffer.size()) {
            Offer offer = offerList.get(selectedOffer - 1);
            orders = offer.getOrders();
            orders.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_TO_COME);
            offer.setOfferStatus(OfferStatus.ACCEPTED);
            offer.setOrders(orders);
            orderService.saveOrUpdate(orders);
            offerService.saveOrUpdate(offer);


        }
    }

    public static void notificationOfStatusAndPayment() {
        Optional<Orders> optionalOrders = orderService.findAll().stream()
                .filter(orders -> orders.getOrderStatus() == OrderStatus.WAITING_FOR_SPECIALIST_TO_COME)
                .findFirst();

        if (optionalOrders.isPresent()) {
            Orders orders1 = optionalOrders.get();

            String userResponse = "";

            while (!userResponse.equalsIgnoreCase("yes") && !userResponse.equalsIgnoreCase("no")) {
                System.out.println("Did the specialist start working on the spot? (yes/no)");
                userResponse = scanner.nextLine();
            }

            if (userResponse.equalsIgnoreCase("yes")) {
                orders1.setOrderStatus(OrderStatus.STARTED);
                orderService.saveOrUpdate(orders1);
            } else {
                System.out.println("Our experts will follow up now.");
                return;
            }

            Optional<Orders> optionalOrders2 = orderService.findAll().stream()
                    .filter(orders -> orders.getOrderStatus() == OrderStatus.STARTED &&
                            orders.getCustomer().getId().equals(customer.getId()))
                    .findFirst();

            if (optionalOrders2.isPresent()) {
                Orders orders2 = optionalOrders.get();

                userResponse = "";
                while (!userResponse.equalsIgnoreCase("yes") && !userResponse.equalsIgnoreCase("no")) {
                    System.out.println("Did the specialist finish his/her work? (yes/no)");
                    userResponse = scanner.nextLine();
                }

                if (userResponse.equalsIgnoreCase("yes")) {
                    orders2.setOrderStatus(OrderStatus.DONE);
                    System.out.println("Thank you for choosing us.");
                    orderService.saveOrUpdate(orders2);

                    paymentOrders(orders2);
                    orders2.setOrderStatus(OrderStatus.PAID);

                } else {
                    System.out.println("Our experts will follow up now.");
                }

            }
        }
    }

    public static void paymentOrders(Orders orders) {
        List<Offer> offerList = offerService.findAll().stream()
                .filter(offer -> offer.getOfferStatus() == OfferStatus.ACCEPTED
                        && offer.getOrders().getCustomer().getId().equals(customer.getId()))
                .toList();
        List<String> newOffer = new ArrayList<>();
        offerList.forEach(o -> newOffer.add("id: " + o.getId() +
                "\n - amount owed: " + o.getProposedPrice() + " - Wallet balance: " + customer.getCredit() +
                "\n - subService:  " + o.getOrders().getSubServices().getName() +
                " - Your specialist, sir or madam: " + o.getSpecialist().getLastName() +
                "\n - ExecutionTime: " + o.getExecutionTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " - EndTime: " + o.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        System.out.println("debts:");
        for (int i = 0; i < newOffer.size(); i++) {
            System.out.println((i + 1) + ". " + newOffer.get(i));
        }
        System.out.println("Select a debt (enter the number): ");
        int selectedDebt = scanner.nextInt();
        scanner.nextLine();

        if (selectedDebt > 0 && selectedDebt <= newOffer.size()) {
            Offer offer = offerList.get(selectedDebt - 1);

            Long wallet = customer.getCredit();
            Long debt = offer.getProposedPrice();
            Specialist specialist = offer.getSpecialist();

            if (wallet >= debt) {
                Long money = wallet - debt;
                customer.setCredit(money);
                Orders orders1 = offer.getOrders();

                orders1.setOrderStatus(OrderStatus.PAID);

                orderService.saveOrUpdate(orders);
                customerService.saveOrUpdate(customer);

                Long specialistCredit = specialist.getCredit() + debt;
                specialist.setCredit(specialistCredit);
                specialistService.saveOrUpdate(specialist);

            } else {
                System.out.println("Insufficient credit. Payment cannot be processed.");
            }
        }
    }

    public static void comments() {
        List<Orders> ordersList = orderService.findAll().stream()
                .filter(orders1 -> orders1.getOrderStatus() == OrderStatus.PAID)
                .toList();
        List<String> newOrder = new ArrayList<>();
        ordersList.forEach(o -> newOrder.add("id: " + o.getId() +
                "\n - nameSpecialist: " + o.getSubServices().getName() + " " + o.getSubServices().getMainService() +
                "\n - to the amount: " + o.getProposedPrice() +
                "\n - ExecutionTime: " + o.getExecutionTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " - EndTime: " + o.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        System.out.println("order to comment:");
        for (int i = 0; i < newOrder.size(); i++) {
            System.out.println((i + 1) + ". " + newOrder.get(i));
        }
        System.out.println("Select an order to comment (enter the number): ");
        int selectedOrder = scanner.nextInt();
        scanner.nextLine();

        if (selectedOrder > 0 && selectedOrder <= newOrder.size()) {
            Orders orders1 = ordersList.get(selectedOrder - 1);

            System.out.println("If you are satisfied with the performance of our expert, rate from 1 to 5: ");
            Double star = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Write a comment: ");
            String txt = scanner.nextLine();

            Comment comment = Comment.builder()
                    .textComment(txt)
                    .star(star)
                    .orders(orders1)
                    .build();

            Optional<Offer> acceptedOffer = orders1.getOfferList().stream()
                    .filter(o -> o.getOfferStatus().equals(OfferStatus.ACCEPTED))
                    .findFirst();
            if (acceptedOffer.isPresent()) {
                Specialist specialist = acceptedOffer.get().getSpecialist();
                specialist.setStar(star);
            }
            commentService.saveOrUpdate(comment);
            orders1.setComment(comment);
            orderService.saveOrUpdate(orders1);
        }
    }
}


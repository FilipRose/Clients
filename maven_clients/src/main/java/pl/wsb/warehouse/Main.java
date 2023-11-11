package pl.wsb.warehouse;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CreateClient clientManager = new CreateClient();
        Scanner  scanner = new Scanner(System.in);

        while(true) {
            System.out.println("1. Create a new Client");
            System.out.println("2. Activate premium account");
            System.out.println("3. Get client full name");
            System.out.println("4. Get client creation date");
            System.out.println("5. Check if the client is premium");
            System.out.println("6. Get the number of clients");
            System.out.println("7. Get the number of premium clients");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();
                    String clientId = clientManager.creteNewClient(firstName, lastName);
                    System.out.println("Client created with ID: " + clientId);
                break;
                case 2:
                    System.out.print("Enter client ID: ");
                    String activateClientId = scanner.nextLine();
                    try {
                        clientManager.activatePremiumAccount(activateClientId);
                        System.out.print("Premium account activated for client with ID: " + activateClientId);
                    }
                    catch (ClientNotFoundException e) {
                        System.out.println("Client not found: " + activateClientId);
                    }
                    break;
                case 3:
                    System.out.print("Enter client ID: ");
                    String nameClientId = scanner.nextLine();
                    try {
                        String fullName = clientManager.getClientFullName(nameClientId);
                        System.out.println("Client full name: " + fullName);
                    } catch (ClientNotFoundException e) {
                        System.out.println("Client not found: " + nameClientId);
                    }
                    break;
                case 4:
                    System.out.print("Enter client ID: ");
                    String creationDateClientId = scanner.nextLine();
                    try {
                        LocalDate creationDate = clientManager.getClientCreationDate(creationDateClientId);
                        System.out.println("Client creation date: " + creationDate);
                    } catch (ClientNotFoundException e) {
                        System.out.println("Client not found: " + creationDateClientId);
                    }
                    break;
                case 5:
                    System.out.print("Enter client ID: ");
                    String premiumClientId = scanner.nextLine();
                    try {
                        boolean isPremium = clientManager.isPremiumClient(premiumClientId);
                        if (isPremium) {
                            System.out.println("Client is premium.");
                        } else {
                            System.out.println("Client is not premium.");
                        }
                    } catch (ClientNotFoundException e) {
                        System.out.println("Client not found: " + premiumClientId);
                    }
                case 6:
                    int numberOfClients = clientManager.getNumberOfClients();
                    System.out.println("Number of clients: " + numberOfClients);
                    break;
                case 7:
                    int numberOfPremiumClients = clientManager.getNumberOfPremiumClients();
                    System.out.println("Number of premium clients: " + numberOfPremiumClients);
                    break;
                case 0:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

        }
    }
}



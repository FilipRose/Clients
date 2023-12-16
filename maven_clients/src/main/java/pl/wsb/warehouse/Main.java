package pl.wsb.warehouse;

import pl.wsb.warehouse.Classes.CreateClient;
import pl.wsb.warehouse.Classes.MetalWarehouses;
import pl.wsb.warehouse.Enums.SupportedMetalType;
import pl.wsb.warehouse.Exceptions.ClientNotFoundException;
import pl.wsb.warehouse.Exceptions.FullWarehouseException;
import pl.wsb.warehouse.Exceptions.ProhibitedMetalTypeException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CreateClient clientManager = new CreateClient();
        MetalWarehouses metalWarehouse = new MetalWarehouses();
        Scanner  scanner = new Scanner(System.in);
        String clientId = "";

        while(true) {
            System.out.println("1. Create a new Client");
            System.out.println("2. Activate premium account");
            System.out.println("3. Get client full name");
            System.out.println("4. Get client creation date");
            System.out.println("5. Check if the client is premium");
            System.out.println("6. Get the number of clients");
            System.out.println("7. Get the number of premium clients");
            System.out.println("8. Add metal ingot to warehouse");
            System.out.println("9. Get metal types to mass stored by client");
            System.out.println("10. Get total volume occupied by client");
            System.out.println("11. Get stored metal types by client");
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
                    clientId = clientManager.creteNewClient(firstName, lastName);
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
                    break;
                case 6:
                    int numberOfClients = clientManager.getNumberOfClients();
                    System.out.println("Number of clients: " + numberOfClients);
                    break;
                case 7:
                    int numberOfPremiumClients = clientManager.getNumberOfPremiumClients();
                    System.out.println("Number of premium clients: " + numberOfPremiumClients);
                    break;
                case 8:
                    System.out.print("Enter client ID: ");
                    clientId = scanner.nextLine();
                    if (clientId != null && !clientId.trim().isEmpty()) {
                        System.out.print("Enter metal type (COPPER, TIN, IRON, etc.): ");
                        String metalTypeStr = scanner.nextLine();
                        try {
                            SupportedMetalType metalType = SupportedMetalType.valueOf(metalTypeStr.toUpperCase());
                            System.out.print("Enter mass of metal ingot: ");
                            double mass = scanner.nextDouble();
                            scanner.nextLine(); // Consume the newline character

                            try {
                                metalWarehouse.addMetalIngot(clientId, metalType, mass);
                                System.out.println("Metal ingot added to the warehouse for client with ID: " + clientId );
                            } catch (ClientNotFoundException | ProhibitedMetalTypeException | FullWarehouseException e) {
                                System.out.println("Error adding metal ingot: " + e.getMessage());
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid metal type.");
                        }
                    } else {
                        System.out.println("Please create a client first.");
                    }
                    break;

                case 9:
                    System.out.print("Enter client ID: ");
                    clientId = scanner.nextLine();
                    if (clientId != null) {
                        Map<SupportedMetalType, Double> metalTypesToMass = metalWarehouse.getMetalTypesToMassStoredByClient(clientId);
                        System.out.println("Metal types to mass stored by client " + clientId + ": " + metalTypesToMass);
                    } else {
                        System.out.println("Please create a client first.");
                    }
                    break;

                case 10:
                    System.out.print("Enter client ID: ");
                    clientId = scanner.nextLine();
                    if (clientId != null && !clientId.trim().isEmpty()) {
                        double totalVolume = metalWarehouse.getTotalVolumeOccupiedByClient(clientId);
                        System.out.println("Total volume occupied by client " + clientId + ": " + totalVolume + " kg");
                    } else {
                        System.out.println("Please create a client first.");
                    }
                    break;

                case 11:
                    System.out.print("Enter client ID: ");
                    clientId = scanner.nextLine();
                    if (clientId != null && !clientId.trim().isEmpty()) {
                        List<SupportedMetalType> storedMetalTypes = metalWarehouse.getStoredMetalTypesByClient(clientId);
                        System.out.println("Stored metal types by client " + clientId + ": " + storedMetalTypes);
                    } else {
                        System.out.println("Please create a client first.");
                    }
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



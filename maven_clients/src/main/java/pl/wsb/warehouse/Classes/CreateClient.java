package pl.wsb.warehouse.Classes;

import pl.wsb.warehouse.Exceptions.ClientNotFoundException;
import pl.wsb.warehouse.Interfaces.Clients;
import pl.wsb.warehouse.Models.WarehouseInfo;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CreateClient implements Clients {

    private final Map<String, ClientInfo> clientsMap = new HashMap<>();
    @Override
    public String creteNewClient(String firstName, String lastName) {

        if (!isValidName(firstName) || !isValidName(lastName)) {
            throw new ClientNotFoundException("The name and surname should be at least 3 letters long and contain only letters.");
        }

        String clientId = generateUniqueClientId();
        ClientInfo client = new ClientInfo(clientId, firstName, lastName, LocalDate.now(), false, null);
        clientsMap.put(clientId, client);
        return clientId.trim();
    }

    private boolean isValidName(String name) {
        return name != null && name.length() >= 3 && name.matches("[a-zA-Z]+");
    }

    @Override
    public void activatePremiumAccount(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            clientsMap.get(clientId).setPremiumAccount(true);
        }
        else throw new ClientNotFoundException("A client with this id does not exist");
    }

    @Override
    public String getClientFullName(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            ClientInfo client = clientsMap.get(clientId);
            return client.getFirstName() + " " + client.getLastName();
        } else throw new ClientNotFoundException("A client with this id does not exist");
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            return clientsMap.get(clientId).getCreationDate();
        } else throw new ClientNotFoundException("A client with this id does not exist");
    }

    @Override
    public boolean isPremiumClient(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            return clientsMap.get(clientId).isPremiumAccount();
        } else throw new ClientNotFoundException("A client with this id does not exist");
    }

    @Override
    public int getNumberOfClients() {
        return clientsMap.size();
    }

    @Override
    public int getNumberOfPremiumClients() {
        return (int) clientsMap.values().stream().filter(ClientInfo::isPremiumAccount).count();
    }

    private String generateUniqueClientId() {
        byte[] randomBytes = new byte[16];
        new SecureRandom().nextBytes(randomBytes);
        String clientId = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return "Client-" + clientId;
    }

    private static class ClientInfo {
        private final String ID;
        private final String firstName;
        private final String lastName;
        private final LocalDate creationDate;

        private boolean isPremiumAccount;
        private final WarehouseInfo warehouseInfo;


        public ClientInfo(String ID, String firstName, String lastName, LocalDate creationDate, boolean isPremiumAccount, WarehouseInfo warehouseInfo) {
            this.ID = ID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.creationDate = creationDate;
            this.isPremiumAccount = isPremiumAccount;
            this.warehouseInfo = warehouseInfo;
        }

        public String getID() {
            return ID;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public LocalDate getCreationDate() {
            return creationDate;
        }

        public WarehouseInfo getWarehouseInfo() {
            return warehouseInfo;
        }

        public boolean isPremiumAccount() {
            return isPremiumAccount;
        }

        public void setPremiumAccount(boolean premiumAccount) {
            isPremiumAccount = premiumAccount;
        }
    }
}

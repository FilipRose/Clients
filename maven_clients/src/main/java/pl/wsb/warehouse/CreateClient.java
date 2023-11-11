package pl.wsb.warehouse;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CreateClient implements Clients{

    private final Map<String, ClientInfo> clientsMap = new HashMap<>();

    @Override
    public String creteNewClient(String firstName, String lastName) {
        String clientId = generateUniqueClientId();
        ClientInfo client = new ClientInfo(firstName, lastName, LocalDate.now(), false);
        clientsMap.put(clientId, client);
        return clientId;
    }

    @Override
    public void activatePremiumAccount(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            clientsMap.get(clientId).setPremiumAccount(true);
        }
        else throw new ClientNotFoundException();
    }

    @Override
    public String getClientFullName(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            ClientInfo client = clientsMap.get(clientId);
            return client.getFirstName() + " " + client.getLastName();
        } else throw new ClientNotFoundException();
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            return clientsMap.get(clientId).getCreationDate();
        } else throw new ClientNotFoundException();
    }

    @Override
    public boolean isPremiumClient(String clientId) throws ClientNotFoundException {
        if(clientsMap.containsKey(clientId)) {
            return clientsMap.get(clientId).isPremiumAccount();
        } else throw new ClientNotFoundException();
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
        private final String firstName;
        private final String lastName;
        private final LocalDate creationDate;
        private boolean isPremiumAccount;

        public ClientInfo(String firstName, String lastName, LocalDate creationDate, boolean isPremiumAccount) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.creationDate = creationDate;
            this.isPremiumAccount = isPremiumAccount;
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
        public boolean isPremiumAccount() {
            return isPremiumAccount;
        }
        public void setPremiumAccount(boolean isPremiumAccount) {
            this.isPremiumAccount = isPremiumAccount;
        }
    }
}

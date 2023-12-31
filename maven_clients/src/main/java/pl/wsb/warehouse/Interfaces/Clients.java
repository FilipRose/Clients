package pl.wsb.warehouse.Interfaces;
import pl.wsb.warehouse.Exceptions.ClientNotFoundException;

import java.time.LocalDate;

public interface Clients {
    String creteNewClient(String firstName, String lastName);
    void activatePremiumAccount(String clientId) throws ClientNotFoundException;
    String getClientFullName(String clientId) throws ClientNotFoundException;
    LocalDate getClientCreationDate(String clientId) throws ClientNotFoundException;
    boolean isPremiumClient(String clientId) throws ClientNotFoundException;
    int getNumberOfClients();
    int getNumberOfPremiumClients();
}
//    /**
//     * Creates a new client and stores their personal information.
//     * @param firstName Client first name.
//     * @param lastName Client last name.
//     * @return Created user's identifier.
//     */
//    String createNewClient(String firstName, String lastName);
//
//    /**
//     * Sets the customer account as a premium account.
//     * @param clientId Client identifier returned after its creation.
//     * @return Client identifier returned after its creation.
//     * @throws ClientNotFoundException Thrown when the client doesn't exists.
//     */
//    String activatePremiumAccount(String clientId);
//
//    /**
//     * @param clientId Client identifier returned after its creation.
//     * @return Client full name consisting of client's first and last name eg. "John Doe".
//     * @throws ClientNotFoundException Thrown when the client doesn't exists.
//     */
//    String getClientFullName(String clientId);
//
//    /**
//     * @param clientId Client identifier returned after its creation.
//     * @return LocalDate when the client was created.
//     * @throws ClientNotFoundException Thrown when the client doesn't exists.
//     */
//    LocalDate getClientCreationDate(String clientId);
//
//    boolean isPremiumClient(String clientId);
//
//    int getNumberOfClients();
//
//    int getNumberOfPremiumClients();
package pl.wsb.warehouse.Classes;

import org.junit.jupiter.api.Test;
import pl.wsb.warehouse.Exceptions.ClientNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateClientTest {

    private  CreateClient createClientUnderTests = new CreateClient();
    @Test
    void createNewClient_validName_returnsClientId() {

        String firstName = "Jan";
        String lastName = "Kowalski";

        String clientId = createClientUnderTests.creteNewClient(firstName, lastName);

        assertNotNull(clientId);
        assertTrue(clientId.trim().startsWith("Client-"));
    }

    @Test
    void createNewClient_invalidName_throwsClientNotFoundException() {

        String firstName = "J4n";
        String lastName = "123";

        assertThrows(ClientNotFoundException.class, () -> {
            createClientUnderTests.creteNewClient(firstName, lastName);
        });
    }

    @Test
    void activatePremiumAccount_existingClient_activatesPremiumAccount() {

        String firstName = "Michael";
        String lastName = "Smith";
        String clientId = createClientUnderTests.creteNewClient(firstName, lastName);

        assertDoesNotThrow(() -> {
            createClientUnderTests.activatePremiumAccount(clientId);
        });

        assertTrue(createClientUnderTests.isPremiumClient(clientId));
    }

    @Test
    void activatePremiumAccount_nonExistingClient_throwsClientNotFoundException() {

        String nonExistingClientId = "NonExistingClientID";

        assertThrows(ClientNotFoundException.class, () -> {
            createClientUnderTests.activatePremiumAccount(nonExistingClientId);
        });
    }
    @Test
    void getClientFullName_existingClient_returnsFullName() {

        String firstName = "John";
        String lastName = "Doe";
        String clientId = createClientUnderTests.creteNewClient(firstName, lastName);

        assertDoesNotThrow(() -> {
            String fullName = createClientUnderTests.getClientFullName(clientId);

            assertEquals("John Doe", fullName);
        });
    }

    @Test
    void getClientFullName_nonExistingClient_throwsClientNotFoundException() {

        String nonExistingClientId = "NonExistingClientID";

        assertThrows(ClientNotFoundException.class, () -> {
            createClientUnderTests.getClientFullName(nonExistingClientId);
        });
    }

    @Test
    void getClientCreationDate_ExistingClient_ReturnsCreationDate() {

        String firstName = "John";
        String lastName = "Doe";
        String clientId = createClientUnderTests .creteNewClient(firstName, lastName);


        assertDoesNotThrow(() -> {
            LocalDate creationDate = createClientUnderTests .getClientCreationDate(clientId);


            assertEquals(LocalDate.now(), creationDate);
        });
    }

    @Test
    void getClientCreationDate_NonExistingClient_ThrowsClientNotFoundException() {

        String nonExistingClientId = "NonExistingClientID";

        assertThrows(ClientNotFoundException.class, () -> {
            createClientUnderTests .getClientCreationDate(nonExistingClientId);
        });
    }

    @Test
    void isPremiumClient_ExistingPremiumClient_ReturnsTrue() {

        String firstName = "Jan";
        String lastName = "Nowak";
        String clientId = createClientUnderTests.creteNewClient(firstName, lastName);
        createClientUnderTests.activatePremiumAccount(clientId);


        assertDoesNotThrow(() -> {
            boolean isPremium = createClientUnderTests.isPremiumClient(clientId);


            assertTrue(isPremium);
        });
    }

    @Test
    void isPremiumClient_ExistingNonPremiumClient_ReturnsFalse() {

        String firstName = "Kacper";
        String lastName = "Adamczyk";
        String clientId = createClientUnderTests.creteNewClient(firstName, lastName);

        assertDoesNotThrow(() -> {
            boolean isPremium = createClientUnderTests.isPremiumClient(clientId);

            assertFalse(isPremium);
        });
    }

    @Test
    void isPremiumClient_NonExistingClient_ThrowsClientNotFoundException() {

        String nonExistingClientId = "NonExistingClientID";

        assertThrows(ClientNotFoundException.class, () -> {
            createClientUnderTests.isPremiumClient(nonExistingClientId);
        });
    }
    @Test
    void getNumberOfClients_EmptyClientsMap_ReturnsZero() {

        int numberOfClients = createClientUnderTests.getNumberOfClients();

        assertEquals(0, numberOfClients);
    }

    @Test
    void getNumberOfClients_SomePremiumClients_ReturnsCorrectCount() {

        String client1 = createClientUnderTests.creteNewClient("John", "Markus");
        String client2 = createClientUnderTests.creteNewClient("Marry", "Jane");
        String client3 = createClientUnderTests.creteNewClient("Bob", "Kowalski");
        createClientUnderTests.activatePremiumAccount(client1);
        createClientUnderTests.activatePremiumAccount(client2);
        createClientUnderTests.activatePremiumAccount(client3);

        int numberOfPremiumClients = createClientUnderTests.getNumberOfPremiumClients();

        assertEquals(3, numberOfPremiumClients);
    }

    @Test
    void getNumberOfPremiumClients_EmptyClientsMap_ReturnsZero() {

        int numberOfPremiumClients = createClientUnderTests.getNumberOfPremiumClients();

        assertEquals(0, numberOfPremiumClients);
    }

    @Test
    void getNumberOfPremiumClients_SomePremiumClients_ReturnsCorrectCount() {

        String client1 = createClientUnderTests.creteNewClient("John", "Doe");
        String client2 = createClientUnderTests.creteNewClient("Jane", "Smith");
        String client3 = createClientUnderTests.creteNewClient("Bob", "Johnson");
        createClientUnderTests.activatePremiumAccount(client1);
        createClientUnderTests.activatePremiumAccount(client2);

        int numberOfPremiumClients = createClientUnderTests.getNumberOfPremiumClients();

        assertEquals(2, numberOfPremiumClients);
    }
}
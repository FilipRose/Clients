package pl.wsb.warehouse.Classes;
import org.junit.jupiter.api.Test;
import pl.wsb.warehouse.Enums.SupportedMetalType;
import pl.wsb.warehouse.Exceptions.ClientNotFoundException;
import pl.wsb.warehouse.Exceptions.FullWarehouseException;
import pl.wsb.warehouse.Exceptions.ProhibitedMetalTypeException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;


class MetalWarehousesTest {

    private MetalWarehouses metalWarehousesUnderTests = new MetalWarehouses();
    @Test
    void addMetalIngot_ValidInput_Success() throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";
        SupportedMetalType metalType = SupportedMetalType.GOLD;
        double mass = 5.0;

        metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass);

        Map<SupportedMetalType, Double> storedMetalData = metalWarehousesUnderTests.getMetalTypesToMassStoredByClient(clientId);
        assertTrue(storedMetalData.containsKey(metalType));
        assertEquals(mass, storedMetalData.get(metalType));
    }
    @Test
    void addMetalIngot_InvalidClient_ThrowsClientNotFoundException() {
        String clientId = null;
        SupportedMetalType metalType = SupportedMetalType.GOLD;
        double mass = 5.0;

        assertThrows(ClientNotFoundException.class, () -> metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass));
    }

    @Test
    void addMetalIngot_ProhibitedMetalType_ThrowsProhibitedMetalTypeException() {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";
        SupportedMetalType metalType = null;
        double mass = 5.0;

        assertThrows(ProhibitedMetalTypeException.class, () -> metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass));
    }

    @Test
    void addMetalIngot_FullWarehouse_ThrowsFullWarehouseException() throws ClientNotFoundException, ProhibitedMetalTypeException {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";
        SupportedMetalType metalType = SupportedMetalType.GOLD;
        double mass = MetalWarehouses.MAX_WAREHOUSE_CAPACITY + 1.0;

        assertThrows(FullWarehouseException.class, () -> metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass));
    }
    @Test
    void getMetalTypesToMassStoredByClient_ClientWithNoData_EmptyMap() {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";

        Map<SupportedMetalType, Double> storedMetalData = metalWarehousesUnderTests.getMetalTypesToMassStoredByClient(clientId);

        assertTrue(storedMetalData.isEmpty());
    }

    @Test
    void getTotalVolumeOccupiedByClient_ClientWithNoData_ZeroVolume() {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";

        double totalVolume = metalWarehousesUnderTests.getTotalVolumeOccupiedByClient(clientId);

        assertEquals(0.0, totalVolume);
    }

    @Test
    void getStoredMetalTypesByClient_ClientWithNoData_EmptyList() {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";

        List<SupportedMetalType> storedMetalTypes = metalWarehousesUnderTests.getStoredMetalTypesByClient(clientId);

        assertTrue(storedMetalTypes.isEmpty());
    }

    @Test
    void getMetalTypesToMassStoredByClient_ClientWithData_NonEmptyMap() throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";
        SupportedMetalType metalType = SupportedMetalType.GOLD;
        double mass = 5.0;
        metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass);

        Map<SupportedMetalType, Double> storedMetalData = metalWarehousesUnderTests.getMetalTypesToMassStoredByClient(clientId);

        assertTrue(storedMetalData.containsKey(metalType));
        assertEquals(mass, storedMetalData.get(metalType));
    }

    @Test
    void getTotalVolumeOccupiedByClient_ClientWithData_NonZeroVolume() throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";
        SupportedMetalType metalType = SupportedMetalType.IRON;
        double mass = 5.0;
        metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass);

        double totalVolume = metalWarehousesUnderTests.getTotalVolumeOccupiedByClient(clientId);

        assertTrue(totalVolume > 0.0);
    }

    @Test
    void getStoredMetalTypesByClient_ClientWithData_NonEmptyList() throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {

        String clientId = "Client-bmbxj_muV20hh1A2VXsi6Q";
        SupportedMetalType metalType = SupportedMetalType.IRON;
        double mass = 5.0;
        metalWarehousesUnderTests.addMetalIngot(clientId, metalType, mass);

        List<SupportedMetalType> storedMetalTypes = metalWarehousesUnderTests.getStoredMetalTypesByClient(clientId);

        assertTrue(storedMetalTypes.contains(metalType));
    }
}
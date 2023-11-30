package pl.wsb.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MetalWarehouses implements Warehouse{
    private final Map<String , Map<SupportedMetalType, Double>> warehouseData = new HashMap<>();


    private static final double MAX_WAREHOUSE_CAPACITY = 100000.0;
    @Override
    public void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
            throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {

        if (!isMetalTypeSupported(metalType)) {
            throw new ProhibitedMetalTypeException("Prohibited metal type: " + metalType);
        }

        double totalVolume = getTotalVolumeOccupiedByClient(clientId);
        Map<SupportedMetalType, Double> clientMetalData = warehouseData.get(clientId);

        if (clientMetalData == null) {
            clientMetalData = new HashMap<>();
            warehouseData.put(clientId, clientMetalData);
        }

        if (totalVolume + (mass * metalType.getDensity()) > MAX_WAREHOUSE_CAPACITY) {
            throw new FullWarehouseException("Warehouse is full for client: " + clientId);
        }

        clientMetalData.put(metalType, clientMetalData.getOrDefault(metalType, 0.0) + mass);
    }

    @Override
    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId) {
        return warehouseData.getOrDefault(clientId, new HashMap<>());
    }

    @Override
    public double getTotalVolumeOccupiedByClient(String clientId) {
        return warehouseData.getOrDefault(clientId, new HashMap<>())
                .values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    @Override
    public List<SupportedMetalType> getStoredMetalTypesByClient(String clientId) {
        return new ArrayList<>(warehouseData.getOrDefault(clientId, new HashMap<>()).keySet());
    }
    private boolean isMetalTypeSupported(SupportedMetalType metalType) {
        for (SupportedMetalType supportedType : SupportedMetalType.values()) {
            if (supportedType == metalType) {
                return true;
            }
        }
        return false;
    }

}

package pl.wsb.warehouse.Interfaces;

import pl.wsb.warehouse.Exceptions.ClientNotFoundException;
import pl.wsb.warehouse.Exceptions.FullWarehouseException;
import pl.wsb.warehouse.Exceptions.ProhibitedMetalTypeException;
import pl.wsb.warehouse.Enums.SupportedMetalType;

import java.util.List;
import java.util.Map;

public interface Warehouse {

    void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
            throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException;

    Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId);

    double getTotalVolumeOccupiedByClient(String clientId);

    List<SupportedMetalType> getStoredMetalTypesByClient(String clientId);

}

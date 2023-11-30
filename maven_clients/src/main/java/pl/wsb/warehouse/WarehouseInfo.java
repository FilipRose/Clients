package pl.wsb.warehouse;

public class WarehouseInfo {

    private SupportedMetalType metalType;

    private double mass;

    public WarehouseInfo(SupportedMetalType metalType, double mass) {
        this.metalType = metalType;
        this.mass = mass;
    }

    public SupportedMetalType getMetalType() {
        return metalType;
    }

    public void setMetalType(SupportedMetalType metalType) {
        this.metalType = metalType;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}

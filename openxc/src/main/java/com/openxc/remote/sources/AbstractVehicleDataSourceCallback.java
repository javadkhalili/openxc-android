package com.openxc.remote.sources;

public abstract class AbstractVehicleDataSourceCallback implements
        VehicleDataSourceCallbackInterface {

    abstract public void receive(String name, Double value);
    abstract public void receive(String name, Double value, Double event);

    public void receive(String name, Integer value) {
        receive(name, new Double(value));
    }

    public void receive(String name, Boolean value) {
        receive(name, new Double(value.booleanValue() ? 1 : 0));
    }

    public void receive(String name, String value) {
        receive(name, new Double(value.toUpperCase().hashCode()));
    }

    public void receive(String name, String value, String event) {
        receive(name, new Double(value.toUpperCase().hashCode()),
               new Double(event.toUpperCase().hashCode()));
    }
}
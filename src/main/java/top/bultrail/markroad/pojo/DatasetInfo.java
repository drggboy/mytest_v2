package top.bultrail.markroad.pojo;
public class DatasetInfo {
    private String name;
    private int sensorSize;
    private int gatewaySize;

    public DatasetInfo(String name, int sensorSize, int gatewaySize) {
        this.name = name;
        this.sensorSize = sensorSize;
        this.gatewaySize = gatewaySize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSensorSize() {
        return sensorSize;
    }

    public void setSensorSize(int sensorSize) {
        this.sensorSize = sensorSize;
    }

    public int getGatewaySize() {
        return gatewaySize;
    }

    public void setGatewaySize(int gatewaySize) {
        this.gatewaySize = gatewaySize;
    }

    @Override
    public String toString() {
        return "Dataset: " + name + ", Sensor Number: " + sensorSize + ", Gateway Number: " + gatewaySize;
    }
}

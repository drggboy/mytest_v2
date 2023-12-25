package top.bultrail.markroad.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * dataset_name
 */
@Data
public class DatasetName implements Serializable {
    private String name;

    private Integer sensorSize;

    private Integer gatewaySize;

    private Integer crossingSize;

    private Double locationLng;

    private Double locationLat;

    private static final long serialVersionUID = 1L;
}
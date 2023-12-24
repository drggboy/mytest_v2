package top.bultrail.markroad.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * Point2 by Batis
 */
@Data
public class Point2 implements Serializable {
    private Integer id;

    private String roadname;

    private Integer numberinroad;

    private String lng;

    private String lat;

    private Integer roadId;

    private static final long serialVersionUID = 1L;
}
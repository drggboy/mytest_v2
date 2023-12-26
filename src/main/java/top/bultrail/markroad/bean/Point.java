package top.bultrail.markroad.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    private int id;
    private String roadName;
    private String numberInRoad;
    private String Lng;
    private String Lat;

//    public Point() {
//    }
//
//    public Point(int id, String roadName, String numberInRoad, String lng, String lat) {
//        this.id = id;
//        this.roadName = roadName;
//        this.numberInRoad = numberInRoad;
//        Lng = lng;
//        Lat = lat;
//    }
//
//    public Point(String roadName, String numberInRoad, String lng, String lat) {
//        this.roadName = roadName;
//        this.numberInRoad = numberInRoad;
//        Lng = lng;
//        Lat = lat;
//    }
//
//    @Override
//    public String toString() {
//        return "Point{" +
//                "id=" + id +
//                ", roadName='" + roadName + '\'' +
//                ", numberInRoad='" + numberInRoad + '\'' +
//                ", Lng='" + Lng + '\'' +
//                ", Lat='" + Lat + '\'' +
//                '}';
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getRoadName() {
//        return roadName;
//    }
//
//    public void setRoadName(String roadName) {
//        this.roadName = roadName;
//    }
//
//    public String getNumberInRoad() {
//        return numberInRoad;
//    }
//
//    public void setNumberInRoad(String numberInRoad) {
//        this.numberInRoad = numberInRoad;
//    }
//
//    public String getLng() {
//        return Lng;
//    }
//
//    public void setLng(String lng) {
//        Lng = lng;
//    }
//
//    public String getLat() {
//        return Lat;
//    }
//
//    public void setLat(String lat) {
//        Lat = lat;
//    }
}

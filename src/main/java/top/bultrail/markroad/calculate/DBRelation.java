package top.bultrail.markroad.calculate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import top.bultrail.markroad.bean.Point;
import org.springframework.stereotype.Component;
import top.bultrail.markroad.mapper.DatasetNameMapper;
import top.bultrail.markroad.mapper.PointMapper;
import top.bultrail.markroad.pojo.DatasetInfo;

import org.springframework.transaction.annotation.Transactional;
import top.bultrail.markroad.pojo.DatasetLocation;

@Component
public class DBRelation {
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private DatasetNameMapper datasetNameMapper;

    @Transactional
    public void quick_write_new2(String[][] cross_points, String[][][] gateway_array, String[][][] sensor_array) {
        // 处理 sensor_array
        String tableName = "sensor";
        for (int i = 0; i < sensor_array.length; i++) {
            for (int j = 0; j < sensor_array[i].length; j++) {
                String lng = sensor_array[i][j][0];
                String lat = sensor_array[i][j][1];
                pointMapper.insertDynamic(tableName, lng, lat);
            }
        }

        tableName = "gateway";
        for (int i = 0; i < gateway_array.length; i++) {
            for (int j = 0; j < gateway_array[i].length; j++) {
                String lng = gateway_array[i][j][0];
                String lat = gateway_array[i][j][1];
                pointMapper.insertDynamic(tableName, lng, lat);
            }
        }

        tableName = "crossing";
        for (int i = 0; i < cross_points.length; i++) {
            String lng = cross_points[i][0];
            String lat = cross_points[i][1];
            pointMapper.insertDynamic(tableName, lng, lat);
        }
    }

    @Transactional
    public void clear2() {
        pointMapper.truncateSensor();
        pointMapper.truncateGateway();
        pointMapper.truncateCross();
    }

    @Transactional
    public void saveDataset2(String setName, List<Double> currentLocation) {
        try {
            datasetNameMapper.insertDatasetInfo(setName, currentLocation.get(0), currentLocation.get(1));
            String[] keys = new String[]{"sensor", "gateway", "crossing"};
            for (String key : keys) {
                String newTableName = key + "_" + setName;
                pointMapper.createTableLike(newTableName, key);
                pointMapper.copyDataToNewTable(newTableName, key);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常信息到控制台
            throw e; // 重新抛出异常，以便进行进一步处理
        }
    }

    //删除数据集
    @Transactional
    public void deleteDataset2(String setName) {
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        // 删除三个数据表
        for (String key : keys) {
            String tableName = key + "_" + setName;
            pointMapper.dropTable(tableName);
        }
        datasetNameMapper.deleteByPrimaryKey(setName);
    }


    public List<DatasetInfo> searchDatasetInfo2() {
        return datasetNameMapper.selectDatasetInfo();
    }

    @Transactional
    public void tdata2(String name) {
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            pointMapper.transferData(key, name);
        }
    }

    @Transactional
    public List<Double> datasetLoad2(String setName) {
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            pointMapper.transferData(key, setName);
        }
        DatasetLocation location = datasetNameMapper.selectDatasetLocation(setName);
        List<Double> locationList = new ArrayList<>();
        if (location != null) {
            locationList.add(location.getLocationLng());
            locationList.add(location.getLocationLat());
        }
        return locationList;
    }

    @Transactional
    public void tdataG(String num) {
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            pointMapper.transferData(key, "guo" + num);
        }
    }

    // 读取senor
    public List<List<String>> readSensor2() {
        List<Point> points = pointMapper.selectAllSensors();
        List<List<String>> bk = new ArrayList<>();
        for (Point p : points) {
            List<String> lis = new ArrayList<>();
            lis.add(Integer.toString(p.getId()));
            lis.add(p.getRoadName());
            lis.add(p.getNumberInRoad());
            lis.add(p.getLng());
            lis.add(p.getLat());
            bk.add(lis);
        }
        return bk;
    }

    public List<List<String>> readGateway2() {
        List<Point> points = pointMapper.selectAllGateways();
        List<List<String>> bk = new ArrayList<>();
        for (Point p : points) {
            List<String> lis = new ArrayList<>();
            lis.add(Integer.toString(p.getId()));
            lis.add(p.getRoadName());
            lis.add(p.getNumberInRoad());
            lis.add(p.getLng());
            lis.add(p.getLat());
            bk.add(lis);
        }
        return bk;
    }

    public List<List<String>> readCrossing2() {
        List<Point> points = pointMapper.selectAllCrossings();
        List<List<String>> bk = new ArrayList<>();
        for (Point p : points) {
            List<String> lis = new ArrayList<>();
            lis.add(p.getRoadName());
            lis.add(p.getNumberInRoad());
            lis.add(p.getLng());
            lis.add(p.getLat());
            bk.add(lis);
        }
        return bk;
    }
}

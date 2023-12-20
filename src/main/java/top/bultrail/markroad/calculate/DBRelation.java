package top.bultrail.markroad.calculate;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import top.bultrail.markroad.bean.Point;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Component;
import top.bultrail.markroad.pojo.DatasetInfo;

@Component
public class DBRelation {

    private QueryRunner qr = new QueryRunner();

    //新建连接
    private Connection setConnection() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        Connection conn;
        try {
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
            // 用户 mytest
//            cpds.setJdbcUrl("jdbc:mysql://10.45.39.13:3306/Marks_new");
//            cpds.setUser("mytest");
//            cpds.setPassword("12345678");

            // 用户 mytest2
            cpds.setJdbcUrl("jdbc:mysql://10.45.39.13:3306/Marks2");
            cpds.setUser("mytest2");
            cpds.setPassword("12345Qq@");

            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(10);
            conn = cpds.getConnection();
        } catch (PropertyVetoException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
    // 一键保存
    public void write_all(String roadName, List<String[]> strList1, String pointType) {
        Connection conn = setConnection();
        if ("sensor".equals(pointType)) {
            for (int i = 0; i < strList1.size(); i++) {
                try {
                    //准备SQL语句
                    String sql = "INSERT INTO sensor(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {roadName, i, strList1.get(i)[0], strList1.get(i)[1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if ("gateway".equals(pointType)) {
            for (int i = 0; i < strList1.size(); i++) {
                try {
                    String sql = "INSERT INTO gateway(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {roadName, i, strList1.get(i)[0], strList1.get(i)[1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if ("cros".equals(pointType)) {
            for (int i = 0; i < strList1.size(); i++) {
                try {
                    String sql = "INSERT INTO crossing(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {roadName, i, strList1.get(i)[0], strList1.get(i)[1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //写入数据
    public void write_new(String roadName, List<String[]> strList1, String pointType) {
        Connection conn = setConnection();
        if ("sensor".equals(pointType)) {
            for (int i = 0; i < strList1.size(); i++) {
                try {
                    //准备SQL语句
                    String sql = "INSERT INTO sensor(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {roadName, i, strList1.get(i)[0], strList1.get(i)[1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if ("gateway".equals(pointType)) {
            for (int i = 0; i < strList1.size(); i++) {
                try {
                    String sql = "INSERT INTO gateway(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {roadName, i, strList1.get(i)[0], strList1.get(i)[1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if ("cros".equals(pointType)) {
            for (int i = 0; i < strList1.size(); i++) {
                try {
                    String sql = "INSERT INTO crossing(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {roadName, i, strList1.get(i)[0], strList1.get(i)[1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void quick_write_new(String[][] cross_points, String[][][] gateway_array, String[][][] sensor_array) {
        Connection conn = setConnection();
        for (int i = 0; i < sensor_array.length; i++) {
            for(int j=0; j< sensor_array[i].length; j++){
                try {
                    //准备SQL语句
                    String sql = "INSERT INTO sensor(Lng,Lat) VALUES (?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {sensor_array[i][j][0], sensor_array[i][j][1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < gateway_array.length; i++) {
            for(int j=0; j< gateway_array[i].length; j++){
                try {
                    //准备SQL语句
                    String sql = "INSERT INTO gateway(Lng,Lat) VALUES (?, ?)";
//                    System.out.println(sql);
                    Object[] obj = {gateway_array[i][j][0], gateway_array[i][j][1]};
                    //执行，抛出异常
                    qr.update(conn, sql, obj);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < cross_points.length; i++) {
            try {
                String sql = "INSERT INTO crossing(Lng,Lat) VALUES (?, ?)";
//                    System.out.println(sql);
                Object[] obj = {cross_points[i][0], cross_points[i][1]};
                //执行，抛出异常
                qr.update(conn, sql, obj);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //清空数据表 新增
    public void clear() {
        Connection conn = setConnection();
        try {
            qr.update(conn, "TRUNCATE TABLE sensor");
            qr.update(conn, "TRUNCATE TABLE gateway");
            qr.update(conn, "TRUNCATE TABLE crossing");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //排序 新增
    public void sort() {
        Connection conn = null;
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            String sql = "SELECT * FROM " + key + " ORDER BY numberInRoad, " + key + "_id";
            try {
                conn = setConnection();
                List<Point> rs = qr.query(conn, sql, new BeanListHandler<>(Point.class));
                qr.update(conn, "TRUNCATE TABLE " + key);
//                System.out.println(rs.size());
                for(Point p : rs) {
//                    System.out.println(p);
                    String sql2 = "INSERT INTO " + key +"(roadName,numberInRoad,Lng,Lat) VALUES (?, ?, ?, ?)";
                    Object[] obj = {p.getRoadName(), p.getNumberInRoad(), p.getLng(), p.getLat()};
                    qr.update(conn, sql2, obj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //保存数据集
    public void saveDataset(String setName, List<Double> currentLocation) {
        Connection conn = setConnection();
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            String newTableName = key + "_" + setName;
            String sql = "CREATE TABLE " + newTableName + " LIKE " + key + ";";
            try {
                qr.update(conn, sql);
                sql = "INSERT INTO " + newTableName + " SELECT * FROM " + key + ";";
                qr.update(conn, sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Insert into dataset_name table
        String datasetNameTable = "dataset_name";
        String sensorSize = "SELECT COUNT(*) FROM sensor_" + setName;
        String gatewaySize = "SELECT COUNT(*) FROM gateway_" + setName;
        String crossingSize = "SELECT COUNT(*) FROM crossing_" + setName;
        String insertSql = "INSERT INTO " + datasetNameTable + " (name, sensor_size, gateway_size, crossing_size, location_lng, location_lat) " +
                "VALUES (?, (" + sensorSize + "), (" + gatewaySize + "), (" + crossingSize + "), (" + currentLocation.get(0) + "), (" + currentLocation.get(1) + "))";
        try {
            PreparedStatement statement = conn.prepareStatement(insertSql);
            statement.setString(1, setName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除数据集
    public void deleteDataset(String setName) {
        Connection conn = setConnection();
        String[] keys = new String[]{"sensor", "gateway", "crossing"};

        // 删除三个数据表
        for (String key : keys) {
            String tableName = key + "_" + setName;
            String sql = "DROP TABLE IF EXISTS " + tableName;
            try {
                qr.update(conn, sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // 删除 dataset_name 表中对应的条目
        String datasetNameTable = "dataset_name";
        String deleteSql = "DELETE FROM " + datasetNameTable + " WHERE name = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(deleteSql);
            statement.setString(1, setName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 获取数据集列表
    public List<String> searchSetnames() {
        List<String> setNames = new ArrayList<>();
        Connection conn = setConnection();
        String sql = "SELECT name FROM dataset_name";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String setName = resultSet.getString("name");
                setNames.add(setName);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return setNames;
    }

    public List<DatasetInfo> searchDatasetInfo() {
        List<DatasetInfo> datasetInfoList = new ArrayList<>();
        Connection conn = setConnection();
        String sql = "SELECT name, sensor_size, gateway_size FROM dataset_name";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int sensorSize = resultSet.getInt("sensor_size");
                int gatewaySize = resultSet.getInt("gateway_size");

                DatasetInfo datasetInfo = new DatasetInfo(name, sensorSize, gatewaySize);
                datasetInfoList.add(datasetInfo);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasetInfoList;
    }


    //备份 新增
    public void bkup() {

        Connection conn = setConnection();
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            String sql = "INSERT INTO " + key + "_backup(roadName, numberInRoad, Lng, Lat, road_id) SELECT roadName, numberInRoad, Lng, Lat, road_id FROM " + key;
            try {
                qr.update(conn, sql);
                }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tdata(String num) {

        Connection conn = setConnection();
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            String sql = "INSERT INTO " + key + " SELECT * FROM " + key + "_" + num;
            try {
                qr.update(conn, sql);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Double> datasetLoad(String setName) {
        Connection conn = setConnection();
        // 加载数据集
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            String sql = "INSERT INTO " + key + " SELECT * FROM " + key + "_" + setName;
            try {
                qr.update(conn, sql);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // 获取数据集位置
        List<Double> locationList = new ArrayList<>();
        try {
            String sql = "SELECT location_lng, location_lat FROM dataset_name WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, setName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double locationLng = resultSet.getDouble("location_lng");
                double locationLat = resultSet.getDouble("location_lat");
                locationList.add(locationLng);
                locationList.add(locationLat);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }

    public void tdataG(String num) {

        Connection conn = setConnection();
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {
            String sql = "INSERT INTO " + key + " SELECT * FROM " + key + "_guo" + num;
            try {
                qr.update(conn, sql);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void res() {

        Connection conn = setConnection();
        String[] keys = new String[]{"sensor", "gateway", "crossing"};
        for (String key : keys) {

            String sql = "INSERT INTO " + key + " SELECT * FROM " + key + "_backup";
            try {
                qr.update(conn, sql);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 读取senor
    public List<List<String>> readSensor() {

        String sql = "SELECT * FROM sensor";
        List<String> lis;
        List<List<String>> bk = new ArrayList();
        try {
            Connection conn = setConnection();
            List<Point> rs = qr.query(conn, sql, new BeanListHandler<>(Point.class));
            for(Point p : rs) {
                lis = new ArrayList();
                lis.add(Integer.toString(p.getId()));
                lis.add(p.getRoadName());
                lis.add(p.getNumberInRoad());
                lis.add(p.getLng());
                lis.add(p.getLat());
                bk.add(lis);
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bk;
    }

    public List<List<String>> readGateway() {

        String sql = "SELECT * FROM gateway";
        List<String> lis;
        List<List<String>> bk = new ArrayList();
        try {
            Connection conn = setConnection();
            List<Point> rs = qr.query(conn, sql, new BeanListHandler<>(Point.class));
            for(Point p : rs) {
                lis = new ArrayList();
//                lis.add(p.getId());
                lis.add(Integer.toString(p.getId()));
                lis.add(p.getRoadName());
                lis.add(p.getNumberInRoad());
                lis.add(p.getLng());
                lis.add(p.getLat());
                bk.add(lis);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bk;
    }

    public List<List<String>> readCrossing() {

        String sql = "SELECT * FROM crossing";
        List<String> lis;
        List<List<String>> bk = new ArrayList();
        try {
            Connection conn = setConnection();
            List<Point> rs = qr.query(conn, sql, new BeanListHandler<>(Point.class));
            for(Point p : rs) {
                lis = new ArrayList();
                lis.add(p.getRoadName());
                lis.add(p.getNumberInRoad());
                lis.add(p.getLng());
                lis.add(p.getLat());
                bk.add(lis);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bk;
    }
}

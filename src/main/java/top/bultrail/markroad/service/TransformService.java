package top.bultrail.markroad.service;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import top.bultrail.markroad.calculate.CountSet;
import top.bultrail.markroad.calculate.DBRelation;
import top.bultrail.markroad.calculate.FileRelation;
import top.bultrail.markroad.calculate.cal_LP;
import top.bultrail.markroad.config.RelatedProperties;
import top.bultrail.markroad.calculate.CountSet;
import top.bultrail.markroad.pojo.Coordinates;
import top.bultrail.markroad.pojo.Point;
import top.bultrail.markroad.pojo.DatasetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.bultrail.markroad.pojo.QuickSave;

import java.util.*;


@Service
public class TransformService {
    @Autowired
    RelatedProperties relatedProperties;

    @Autowired
    CountSet countSet;

    @Autowired
    CountSet countSetCm;

    @Autowired
    FileRelation fileRelation;

    @Autowired
    DBRelation dbRelation;

    @Autowired
    RedisTemplate redisTemplate;


    //保存文件数据到数据库 新增
//    public void saveDB(Point points) {
//        String roadName = points.getRoadName();
//        String pointType = points.getPointType();
//        String[][] points1 = points.getPoints();
//        List<String[]> strings = Arrays.asList(points1);
//        dbRelation.write_new(roadName, strings, pointType);
//    }

    public void quicksaveDB(QuickSave quickSave) {
        String[][] cross_points = quickSave.getCross_points();
        String[][][] sensor_array = quickSave.getSensor_array();
        String[][][] gateway_array = quickSave.getGateway_array();
        dbRelation.clear2();
//        dbRelation.quick_write_new(cross_points, gateway_array, sensor_array);
        dbRelation.quick_write_new2(cross_points, gateway_array, sensor_array);
    }

    public void saveDataset(String datasetName, List<Double> currentLocation) {
        dbRelation.saveDataset2(datasetName, currentLocation);
    }

    public void deleteDataset(String datasetName) {
        dbRelation.deleteDataset2(datasetName);
    }

//    public List<String> searchSetnames() {
//        List<String> setNames;
//        setNames = dbRelation.searchSetnames();
//        return  setNames;
//    }

    public List<DatasetInfo> searchDatasetInfo() {
        List<DatasetInfo> datasetInfos;
        datasetInfos = dbRelation.searchDatasetInfo2();
        return  datasetInfos;
    }

    //清空数据库 新增
    public void clearDB() {
        dbRelation.clear2();
    }

    //排序 新增
//    public void sortDB() {
//        dbRelation.sort();
//    }

    //备份 新增
//    public void bkDB() {
//        dbRelation.bkup();
//    }

    //162 新增
    public void td207() {
        dbRelation.clear2();
        dbRelation.tdata2("207");
    }

    //461 新增
    public void td461() {
        dbRelation.clear2();
        dbRelation.tdata2("461");
    }

    //518 新增
    public void td1052() {
        dbRelation.clear2();
        dbRelation.tdata2("1052");
    }

    public void td1361() {
        dbRelation.clear2();
        dbRelation.tdata2("1361");
    }

    public List<Double> datasetLoad(String setName) {
        dbRelation.clear2();
        List<Double> location = dbRelation.datasetLoad2(setName);
        return location;
    }

    public void tdGuo(int num) {
        dbRelation.clear2();
        dbRelation.tdataG(num + "");
    }

//    //还原 新增
//    public void resDB() {
//        dbRelation.clear2();
//        dbRelation.res();
//    }

    /**
     * 使用各种算法计算
     * @return
     * @throws Exception
     */
    public HashMap<String,List<List<String>>> calAlgorithm(String algorithm) throws Exception {

        // 从数据库读出的sensor、gateway数据
        List<List<String>> sensor = dbRelation.readSensor2();
        List<List<String>> gateway = dbRelation.readGateway2();
        // 进行处理，在属性间添加逗号形成字符串
        List<String> all_sensor = new ArrayList<String>();
        List<String> all_gateway = new ArrayList<String>();
        // 存放结果数据返回至前端
        HashMap<String, List<List<String>>> hs = new HashMap<>();

        Iterator<List<String>> it1 = sensor.iterator();
        Iterator<List<String>> it2 = gateway.iterator();
        while (it1.hasNext()) {
            List<String> it1next = it1.next();
            String temp1 = it1next.get(0) + "," + it1next.get(1) + it1next.get(2) + "," +  it1next.get(3) + "," + it1next.get(4);
            all_sensor.add(temp1);
        }
        while (it2.hasNext()) {
            List<String> it2next = it2.next();
            String temp2 = it2next.get(0) + "," + it2next.get(1) + it2next.get(2) + "," +  it2next.get(3) + "," + it2next.get(4);
            all_gateway.add(temp2);
        }

        // 存放0、1结果数组
        int[] sol;
        try {
            switch (algorithm) {
                case "GA":
                    sol = countSet.calByGA(all_gateway, all_sensor);
                    break;
                case "Greedy":
                    sol = countSet.calByGreedy(all_gateway, all_sensor);
                    break;
                case "Aco":
                    sol = countSet.calByAco(all_gateway, all_sensor);
                    break;
                case "BB":
                    sol = countSet.calByBB(all_gateway, all_sensor);
                    break;
                case "LP":
                    sol = countSet.calByorLP_new(all_gateway, all_sensor);
//                    sol = countSet.calByMatLP_new(all_gateway, all_sensor);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
            }
        } catch (Exception e) {
            System.out.println("错误信息："+e.getMessage());
            throw e;
        }

        ArrayList<Integer> chosed_gateway_no = new ArrayList<>();
        for(int i = 0; i < sol.length; i++){
            if(sol[i] == 1){
                chosed_gateway_no.add(i+1);
            }
        }

        // 计算结果，返回选中的网关坐标
        ArrayList<String> resultWithCoordinate = new ArrayList<>();
        // 计算结果，返回选中的网关信息，与all_gateway格式一致，用于分析
//        ArrayList<String> resultTmp = new ArrayList<>();
        for (String s : all_gateway) {
            String[] split = s.split(",");
            if (chosed_gateway_no.contains(Integer.parseInt(split[0]))) {
                String data = split[2] + "," + split[3];
                resultWithCoordinate.add(data);
//                resultTmp.add(s);
            }
        }
        //获取所有传感器的数据放入map，一起返回
        List<List<String>> all_gateway02 = new ArrayList<>();
        all_gateway02.add((resultWithCoordinate));
        hs.put("gatewayList", all_gateway02);
        hs.put("sensorList", sensor);

        //生成分析结果
//        fileRelation.creatResult(all_sensor,resultTmp);
        return hs;
    }


    /**
     * 用有向贪心计算，数据保存在txt中,计算上传的文件数据相关 把传感器与网关的信息存入map返回 新增
     * @return
     * @throws Exception
     */
    public HashMap<String,List<List<String>>> calByLinner_upload(String flag) throws Exception {
        // 从数据库读出的sensor、gateway数据
        List<List<String>> sensor = dbRelation.readSensor2();
        List<List<String>> gateway = dbRelation.readGateway2();
        // 进行处理，在属性间添加逗号形成字符串
        List<String> all_sensor = new ArrayList<String>();
        List<String> all_gateway = new ArrayList<String>();
        // 存放结果数据返回至前端
        HashMap<String, List<List<String>>> hs = new HashMap<>();

        Iterator<List<String>> it1 = sensor.iterator();
        Iterator<List<String>> it2 = gateway.iterator();
        while (it1.hasNext()) {
            List<String> it1next = it1.next();
            String temp1 = it1next.get(0) + "," + it1next.get(1) + it1next.get(2) + "," +  it1next.get(3) + "," + it1next.get(4);
            all_sensor.add(temp1);
        }
        while (it2.hasNext()) {
            List<String> it2next = it2.next();
            String temp2 = it2next.get(0) + "," + it2next.get(1) + it2next.get(2) + "," +  it2next.get(3) + "," + it2next.get(4);
            all_gateway.add(temp2);
        }

        // 存放0、1结果数组
        int[] sol;
        try {
            sol = countSet.calByLinner(all_gateway, all_sensor, flag);
        } catch (Exception e) {
            System.out.println("错误信息："+ e);
//            System.out.println("错误信息："+e.getMessage());
            throw e;
        }

        ArrayList<Integer> chosed_gateway_no = new ArrayList<>();
        for(int i = 0; i < sol.length; i++){
            if(sol[i] == 1){
                chosed_gateway_no.add(i+1);
            }
        }

        // 计算结果，返回选中的网关坐标
        ArrayList<String> resultWithCoordinate = new ArrayList<>();
        // 计算结果，返回选中的网关信息，与all_gateway格式一致，用于分析
//        ArrayList<String> resultTmp = new ArrayList<>();
        for (String s : all_gateway) {
            String[] split = s.split(",");
            if (chosed_gateway_no.contains(Integer.parseInt(split[0]))) {
                String data = split[2] + "," + split[3];
                resultWithCoordinate.add(data);
//                resultTmp.add(s);
            }
        }

        //获取所有传感器的数据放入map，一起返回
        List<List<String>> all_gateway02 = new ArrayList<>();
        all_gateway02.add((resultWithCoordinate));
        hs.put("gatewayList", all_gateway02);
        hs.put("sensorList", sensor);

        //生成分析结果
//        fileRelation.creatResult(all_sensor,resultTmp);
        return hs;
    }

}
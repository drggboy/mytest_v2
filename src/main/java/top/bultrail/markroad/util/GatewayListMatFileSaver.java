package top.bultrail.markroad.util;

import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import java.util.*;


public class GatewayListMatFileSaver {

    public void saveGatewayListToMatFile(HashMap<String, List<List<String>>> dataMap, String filePath) throws Exception {
        // 从 dataMap 中提取 gatewayList
        List<String> gatewayList = dataMap.get("gatewayList").get(0);

        // 假设每个 List<String> 中只有一个字符串，且每个字符串包含两个以逗号分隔的数值（经度和纬度）
        double[][] gatewayArray = new double[gatewayList.size()][2]; // 假设每个坐标只有两个维度：经度和纬度
        for (int i = 0; i < gatewayList.size(); i++) {
            String[] coords = gatewayList.get(i).split(","); // 假设每个 List<String> 只有一个字符串元素
            gatewayArray[i][0] = Double.parseDouble(coords[0]); // 经度
            gatewayArray[i][1] = Double.parseDouble(coords[1]); // 纬度
        }

        // 创建一个包含数据的 MLDouble 对象
        MLDouble mlGatewayList = new MLDouble("gatewayList", gatewayArray);

        // 准备要保存的 MLArray 对象列表
        ArrayList<MLArray> list = new ArrayList<>();
        list.add(mlGatewayList);

        // 写入 .mat 文件
        new MatFileWriter(filePath, list);
    }
}
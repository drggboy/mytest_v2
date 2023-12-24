package top.bultrail.markroad;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.bultrail.markroad.mapper.PointMapper;
import top.bultrail.markroad.pojo.Point2;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointMapperTest {

    @Autowired
    private PointMapper pointMapper;

    @Test
    public void testSelectAndPrint() {
        // 假设您要查询的Point2对象的ID
        Integer pointId = 1;

        // 使用mapper执行查询
        Point2 point = pointMapper.selectByPrimaryKey(pointId);
        // 打印查询到的Point2对象
        if (point != null) {
            System.out.println("Point2 Details:");
            System.out.println("ID: " + point.getId());
            System.out.println("Road Name: " + point.getRoadname());
            System.out.println("Number in Road: " + point.getNumberinroad());
            System.out.println("Longitude: " + point.getLng());
            System.out.println("Latitude: " + point.getLat());
            System.out.println("Road ID: " + point.getRoadId());
        } else {
            System.out.println("No Point2 found with ID: " + pointId);
        }
    }
}


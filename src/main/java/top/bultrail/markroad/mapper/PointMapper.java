package top.bultrail.markroad.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.bultrail.markroad.pojo.Point2;

@Repository
public interface PointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Point2 record);

    int insertSelective(Point2 record);

    Point2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Point2 record);

    int updateByPrimaryKey(Point2 record);

    int insertDynamic(@Param("tableName") String tableName, @Param("lng") String lng, @Param("lat") String lat);

    void truncateSensor();

    void truncateGateway();
    void truncateCross();
    void createTableLike(String newTableName, String originalTableName);
    void copyDataToNewTable(String newTableName, String originalTableName);
    void insertDatasetInfo(String setName, Double locationLng, Double locationLat);
    void dropTable(String tableName);
}
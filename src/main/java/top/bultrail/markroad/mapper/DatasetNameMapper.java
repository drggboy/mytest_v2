package top.bultrail.markroad.mapper;

import org.springframework.stereotype.Repository;
import top.bultrail.markroad.pojo.DatasetName;

@Repository
public interface DatasetNameMapper {
    int deleteByPrimaryKey(String name);

    int insert(DatasetName record);

    int insertSelective(DatasetName record);
    void insertDatasetInfo(String setName, Double locationLng, Double locationLat);

    DatasetName selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(DatasetName record);

    int updateByPrimaryKey(DatasetName record);
}
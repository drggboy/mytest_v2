package top.bultrail.markroad.mapper;

import org.springframework.stereotype.Repository;
import top.bultrail.markroad.pojo.DatasetInfo;
import top.bultrail.markroad.pojo.DatasetLocation;
import top.bultrail.markroad.pojo.DatasetName;

import java.util.List;

@Repository
public interface DatasetNameMapper {
    int deleteByPrimaryKey(String name);

    int insert(DatasetName record);

    int insertSelective(DatasetName record);
    void insertDatasetInfo(String setName, Double locationLng, Double locationLat);
    DatasetName selectByPrimaryKey(String name);
    int updateByPrimaryKeySelective(DatasetName record);

    int updateByPrimaryKey(DatasetName record);
    List<DatasetInfo> selectDatasetInfo();
    DatasetLocation selectDatasetLocation(String setName);
}
package top.bultrail.markroad.mapper;

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
}
package top.bultrail.markroad.handler;

import top.bultrail.markroad.calculate.DBRelation;
import top.bultrail.markroad.config.RelatedProperties;
import top.bultrail.markroad.service.BaseService;
import top.bultrail.markroad.service.TransformService;
import top.bultrail.markroad.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BaseHandler {
    @Autowired
    DBRelation dbRelation;

    @RequestMapping(value={"/sup","/api/sup"})
    @ResponseBody
    public ResultEntity<List<List<String>>> sensorUpload() {
        List<List<String>> sensorList = dbRelation.readSensor2();
        return ResultEntity.sucessWithData(sensorList);
    }

    @RequestMapping(value={"/gup","/api/gup"})
    @ResponseBody
    public ResultEntity<List<List<String>>> gatewayUpload() {
        List<List<String>> gatewayList = dbRelation.readGateway2();
        return ResultEntity.sucessWithData(gatewayList);
    }

    @RequestMapping(value={"/cup","/api/cup"})
    @ResponseBody
    public ResultEntity<List<List<String>>> crossingUpload() {
        List<List<String>> crossingList = dbRelation.readCrossing2();
        return ResultEntity.sucessWithData(crossingList);
    }

}

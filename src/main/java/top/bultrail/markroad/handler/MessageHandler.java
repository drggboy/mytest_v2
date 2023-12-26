package top.bultrail.markroad.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.bultrail.markroad.pojo.Point;
import top.bultrail.markroad.pojo.QuickSave;
import top.bultrail.markroad.service.TransformService;
import top.bultrail.markroad.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.bultrail.markroad.pojo.DatasetInfo;



@Controller
public class MessageHandler {
    @Autowired
    TransformService transformService;

    // 一键保存
    @ResponseBody
    @RequestMapping(value = {"/hhquicksaveNew", "/api/hhquicksaveNew"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "/hhquicksaveNew", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultEntity<String>> quicksave_new(@RequestBody QuickSave quick_save) {
        // 将数据保存在数据库
        try {
            transformService.quicksaveDB(quick_save);
            System.out.println("Data Save Success!");
            return ResponseEntity.ok(ResultEntity.successWithoutData());
//            if (quick_save.getCross_points() == null) {
//                return ResultEntity.failed("You haven't set marks yet！");
//            } else {
//                transformService.quicksaveDB(quick_save);
//                return ResultEntity.successWithoutData();
//            }
        } catch (Exception e) {
            return new ResponseEntity<>(ResultEntity.failed(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 保存数据集
    @ResponseBody
    @RequestMapping(value = {"/api/saveDataset"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<String>> saveDataset(@RequestBody Map<String, Object> requestBody) {
        String setName = (String) requestBody.get("datasetName");
        List<Double> currentLocation = (List<Double>) requestBody.get("current_location");
        try {
            transformService.saveDataset(setName, currentLocation);
            System.out.println("Dataset saved successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(ResultEntity.failed(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.successWithoutData());
    }

    // 删除数据集
    @ResponseBody
    @RequestMapping(value = {"/api/deleteDataset"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<String>> deleteDataset(@RequestParam(value = "datasetName") String setName) {
        try {
            transformService.deleteDataset(setName);
            System.out.println("Dataset deleted successfully");
            return ResponseEntity.ok(ResultEntity.successWithoutData());
        } catch (Exception e) {
            return new ResponseEntity<>(ResultEntity.failedWithMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 获取数据集信息
    @ResponseBody
    @RequestMapping(value = {"/api/searchDatasetInfo"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<List<DatasetInfo>>> searchDatasetInfo() {
        try {
            List<DatasetInfo> datasetInfos;
            datasetInfos = transformService.searchDatasetInfo();
            System.out.println("Search Datasets Information Successfully");
            return ResponseEntity.ok(ResultEntity.successWithDatasetInfo(datasetInfos,null));
        } catch (Exception e) {
            ResultEntity<List<DatasetInfo>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 一键保存
    @ResponseBody
    @RequestMapping(value = {"/hhquicksave","/api/hhquicksave"}, method = RequestMethod.POST)
    public ResultEntity<String> quicksave(QuickSave quick_save) {
        // 将数据保存在数据库
        try {
            transformService.quicksaveDB(quick_save);
            return ResultEntity.successWithoutData();
//            if (quick_save.getCross_points() == null) {
//                return ResultEntity.failed("You haven't set marks yet！");
//            } else {
//                transformService.quicksaveDB(quick_save);
//                return ResultEntity.successWithoutData();
//            }
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }
    }


    //清空数据库 新增
    @ResponseBody
    @RequestMapping(value = {"/hhcl", "/api/hhcl"}, method = RequestMethod.POST)
    public ResultEntity<String> clear() {
        transformService.clearDB();
        return ResultEntity.successWithoutData();

    }

    //数据集加载
    @ResponseBody
    @RequestMapping(value = {"/api/datasetLoad"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<List<Double>>> datasetLoad(@RequestParam(value = "datasetName") String setName) {
        try {
            List<Double> location = transformService.datasetLoad(setName);
            System.out.println("Dataset Load Success");
            return ResponseEntity.ok(ResultEntity.successWithDataDouble(location, null));
        } catch (Exception e) {
            return new ResponseEntity<>(ResultEntity.failedWithMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //用贪心算法计算，数据保存在txt 实现
    @ResponseBody
    @RequestMapping(value = {"/hhe","/api/hhe"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<HashMap<String, List<List<String>>>>> calByGreedy(@RequestParam(value = "crosFlag") String flag) {
        HashMap<String, List<List<String>>> strings = null;
        try {
            strings = transformService.calAlgorithm("Greedy");
            System.out.println("Success");
        } catch (Exception e) {
            ResultEntity<HashMap<String, List<List<String>>>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.sucessWithData(strings));
    }

    //有向贪心
    @ResponseBody
    @RequestMapping(value = {"/hhln","/api/hhln"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<HashMap<String, List<List<String>>>>> calByLinner(@RequestParam(value = "crosFlag") String flag) {
        HashMap<String, List<List<String>>> strings = null;
        try {
            strings = transformService.calByLinner_upload(flag);
            System.out.println("Success");
        } catch (Exception e) {
            ResultEntity<HashMap<String, List<List<String>>>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.sucessWithData(strings));
    }

    //用分支限界算法计算，数据保存在txt
    @ResponseBody
    @RequestMapping(value = {"/hhbb","/api/hhbb"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<HashMap<String, List<List<String>>>>> calByBB(@RequestParam(value = "crosFlag") String flag) {
        HashMap<String, List<List<String>>> strings = null;
        try {
            strings = transformService.calAlgorithm("BB");
            System.out.println("Success");
        } catch (Exception e) {
            ResultEntity<HashMap<String, List<List<String>>>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.sucessWithData(strings));
    }

    //用线性规划计算
    @ResponseBody
    @RequestMapping(value = {"/hhLP","/api/hhLP"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<HashMap<String, List<List<String>>>>> calByLP(@RequestParam(value = "crosFlag") String flag) {
        HashMap<String, List<List<String>>> strings = null;
        try {
            strings = transformService.calAlgorithm("LP");
            System.out.println("Success");
        } catch (Exception e) {
            ResultEntity<HashMap<String, List<List<String>>>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.sucessWithData(strings));
    }

    //用蚁群算法计算
    @ResponseBody
    @RequestMapping(value = {"/hhaco","/api/hhaco"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<HashMap<String, List<List<String>>>>> calByAco(@RequestParam(value = "crosFlag") String flag) {
        HashMap<String, List<List<String>>> strings = null;
        try {
            strings = transformService.calAlgorithm("Aco");
            System.out.println("Success");
        } catch (Exception e) {
            ResultEntity<HashMap<String, List<List<String>>>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.sucessWithData(strings));
    }

    //用遗传算法计算
    @ResponseBody
    @RequestMapping(value = {"/calByGA","/api/calByGA"}, method = RequestMethod.POST)
    public ResponseEntity<ResultEntity<HashMap<String, List<List<String>>>>> calByGA(@RequestParam(value = "crosFlag") String flag) {
        HashMap<String, List<List<String>>> strings = null;
        try {
            strings = transformService.calAlgorithm("GA");
            System.out.println("Success");
        } catch (Exception e) {
            ResultEntity<HashMap<String, List<List<String>>>> errorResponse = ResultEntity.failedWithMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ResultEntity.sucessWithData(strings));
    }

}

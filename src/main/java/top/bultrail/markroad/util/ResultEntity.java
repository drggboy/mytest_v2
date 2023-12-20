package top.bultrail.markroad.util;

import top.bultrail.markroad.pojo.DatasetInfo;

import java.util.List;

/**
 * hhh
 * @param <T>
 */
public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    // 封装当前请求的处理结果是成功还是失败
    private String result;
    // 请求处理失败后的信息
    private String message;
    // 要返回的数据
    private T data;

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public ResultEntity() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity<E>(SUCCESS,null,null);
    }

    public static <E> ResultEntity<E> sucessWithData(E data) {
        return new ResultEntity<E>(SUCCESS,null,data);
    }
    public static <E> ResultEntity<E> failed(E data) {
        return new ResultEntity<E>(FAILED,null, data);
    }
    public static <E> ResultEntity<E> failedWithMessage(String message) {
        return new ResultEntity<E>(FAILED, message,null);
    }


    public static ResultEntity<List<String>> successWithData(List<String> strings,String message) {
        return new ResultEntity<List<String>>(SUCCESS, message, strings);
    }

    public static ResultEntity<List<Double>> successWithDataDouble(List<Double> listDouble,String message) {
        return new ResultEntity<List<Double>>(SUCCESS, message, listDouble);
    }

    public static ResultEntity<List<DatasetInfo>> successWithDatasetInfo(List<DatasetInfo> datasetInfos, String message) {
        return new ResultEntity<List<DatasetInfo>>(SUCCESS, message, datasetInfos);
    }

}

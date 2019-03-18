package cn.autowired.tgh.base;

import cn.autowired.tgh.common.enumcase.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: Bacchusgift
 * @Date: 2019/3/18 14:24
 * @version: 1.0.0
 */
public class ResultData implements Serializable {

    @JsonIgnore
    public  ErrorCode errCode;

    @JsonProperty
    public String err_code;

    @JsonProperty
    public String err_msg;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NonNull
    public  Object data;

    private ResultData(ErrorCode errCode, Object data) {
        this.data = data;
        this.errCode = errCode;
        this.err_code = errCode.toString();
        this.err_msg = errCode.getErrInfo();
    }

    private ResultData(ErrorCode errCode) {
        this.errCode = errCode;
        this.err_code = errCode.toString();
        this.err_msg = errCode.getErrInfo();
    }


    public ResultData(){}

    public static ResultData ok(Object data) {

        return new ResultData(ErrorCode.SUCCESS,data);
    }

    public static ResultData ok() {
        return new ResultData(ErrorCode.SUCCESS);
    }

    public static ResultData ok(String[]keys,Object[]values) {
        if (keys.length != values.length){
            throw new IllegalArgumentException();
        }
        Map map = new HashMap();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i],values[i]);
        }
        return new ResultData(ErrorCode.SUCCESS,map);
    }

    public  static ResultData bad(ErrorCode errCode) {
        return new ResultData(errCode);
    }

}

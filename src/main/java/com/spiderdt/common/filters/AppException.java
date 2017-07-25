package com.spiderdt.common.filters;

import com.spiderdt.common.utils.Constants;

/**
 * Created by fivebit on 2017/6/30.
 */
public class AppException extends  Exception{
    private int status = Constants.APP_ERROR_STATUS;

    /** application specific error code */
    private String code;

    private Object data;

    /**
     *
     * @param status
     * @param code
     * @param  data
     */
    public AppException(Integer status, String code, Object data){
        super((String) data);
        this.code = code;
        this.data = data;
    }

    public AppException(String code, Object data) {
        super((String) data);
        this.code = code;
        this.data = data;
    }

    public AppException(String code) {
        super((String) "");
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


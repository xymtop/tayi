package com.xymtop.tayi.browser.entity;

import lombok.Data;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 4:23
 */


@Data
public class Result {
    //结果
    private boolean result;
    //信息
    private String message;
    //数据
    private Object data;


    //ok
    public static Result ok() {
        Result result = new Result();
        result.setResult(true);
        result.setMessage("ok");
        return result;
    }

//    error
    public static Result error() {
        Result result = new Result();
        result.setResult(false);
        result.setMessage("error");
        return result;
    }

    //error
    public static Result error(Object data) {
        Result result = new Result();
        result.setResult(false);
        result.setMessage("error");
        result.setData(data);
        return result;
    }

    //ok
    public static Result ok(Object data) {
        Result result = new Result();
        result.setResult(true);
        result.setMessage("ok");
        result.setData(data);
        return result;
    }


    //ok
    public static Result ok(String message) {
        Result result = new Result();
        result.setResult(true);
        result.setMessage(message);
        return result;
    }



    //ok
    public static Result ok(String message, Object data) {
        Result result = new Result();
        result.setResult(true);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


}

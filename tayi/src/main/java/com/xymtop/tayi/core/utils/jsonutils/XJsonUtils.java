package com.xymtop.tayi.core.utils.jsonutils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 6:01
 */

@Component
public class XJsonUtils {
    //从json字符串到java对象
    public <T> T jsonToObj(String json, Class<T> clazz) {
        JSONObject parseObj = JSONUtil.parseObj(json);
        T t = parseObj.toBean(clazz);
        return t;
    }

    //从java对象到json字符串
    public String objToJson(Object obj) {
        JSONObject jsonObject = JSONUtil.parseObj(obj);
        String jsonStr = jsonObject.toString();
        return jsonStr;
    }

}

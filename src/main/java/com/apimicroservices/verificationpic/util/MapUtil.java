package com.apimicroservices.verificationpic.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String , Object> getMap() {
       return new HashMap<String, Object>();
    }

    public static Map<String , Object> getMap(int code) {
        HashMap<String, Object> map = new HashMap<>();
        if (code == 200) {
            map.put("code", "200");
            map.put("msg", "success");
        }
        if (code == -1) {
            map.put("code", "-1");
            map.put("msg", "服务器内部错误");
        }
        if (code == 403) {
            map.put("code", "403");
            map.put("msg", "禁止访问");
        }if (code == 404) {
            map.put("code", "404");
            map.put("msg", "资源不存在");
        }
        if (code == 405) {
            map.put("code", "405");
            map.put("msg", "错误的请求类型");
        }
        if (code == 501) {
            map.put("code", "501");
            map.put("msg", "数据库错误");
        }
        if (code == 502) {
            map.put("code", "502");
            map.put("msg", "并发异常，请重试");
        }if (code == 600) {
            map.put("code", "600");
            map.put("msg", "缺少参数");
        }
        if (code == 601) {
            map.put("code", "601");
            map.put("msg", "无权操作:缺少 token");
        }
        if (code == 602) {
            map.put("code", "602");
            map.put("msg", "签名错误");
        }
        if (code == 700) {
            map.put("code", "700");
            map.put("msg", "暂无数据");
        }
        if (code == 701) {
            map.put("code", "701");
            map.put("msg", "该功能暂未开通");
        }
        if (code == 702) {
            map.put("code", "702");
            map.put("msg", "资源余额不足");
        }
        if (code == 901) {
            map.put("code", "901");
            map.put("msg", "token错误");
        }if (code == 300) {
            map.put("code", "300");
            map.put("msg", "缺少key参数");
        }
        return map;
    }
}

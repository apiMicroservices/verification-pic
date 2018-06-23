package com.apimicroservices.verificationpic.dao;

public interface PicDao {
    /**
     * 设置减值
     * @param key 键
     * @param value 值
     * @param timeout 超时时间 单位：秒
     */
    void save(String key , String value ,long timeout);

    /**
     * 获得键对应的值
     * @param key 键
     * @return 存储的值
     */
    String get(String key);

    /**
     * 获取配置信息
     * @param key 配置信息的键
     * @return 对应的配置信息
     */
    String getConfig(String key);

    /**
     * 设置配置信息
     * @param key
     * @param value
     */
    void setConfig(String key , String value);
}

package com.apimicroservices.verificationpic.service;

import com.apimicroservices.verificationpic.util.MapUtil;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface VerficationPicService {
    /**
     * 生成验证码
     * @param key 验证码标识
     * @return 验证码对象
     */
    BufferedImage getKaptchaBean(String key) throws Exception;

    /**
     * 使用默认验证码生成器生成验证码，不依赖redis，服务降级处理使用，此时生成的验证码并不能进行校验
     * @param key 验证码标识
     * @return 验证码对象
     */
    BufferedImage getDefaultKaptchaBean(String key);

    /**
     * 验证验证码
     * @param key 验证码标识
     * @param code 验证码内容
     * @return 验证结果
     */
    boolean check(String key, String code);

    /**
     * 设置验证码生成器
     * @param key 配置键
     * @param code 配置值
     */
    Map<String,Object> config(String key, String code) throws Exception;
}

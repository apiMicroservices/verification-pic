package com.apimicroservices.verificationpic.entity;


/**
 * 验证码生成器支持的配置选项
 */
public class ConfigBean {
    /**
     * 图片边框，合法值：yes , no
     */
    public static String border = "yes";
    /**
     * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
     */
    public static String border_color = "black";
    /**
     * 边框厚度，合法值：>0
     */
    public static String border_thickness = "1";
    /**
     * 图片宽
     */
    public static String image_width="200";
    /**
     * 图片高
     */
    public static String image_height="45";
    /**
     * 验证码长度
     */
    public static String textproducer_char_length = "4";
    /**
     * 字体
     */
    public static String textproducer_font_names = "宋体,楷体,微软雅黑";
    /**
     * 字体大小
     */
    public static String textproducer_font_size = "40";
    /**
     * 字体颜色，合法值： r,g,b  或者 white,black,blue.
     */
    public static String textproducer_font_color = "blue";
    /**
     * 文字间隔
     */
    public static String textproducer_char_space = "2";
    /**
     * 干扰 颜色，合法值： r,g,b 或者 white,black,blue.
     */
    public static String noise_color = "black";
    /**
     * 图片样式：
     水纹com.google.code.kaptcha.impl.WaterRipple
     鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
     阴影com.google.code.kaptcha.impl.ShadowGimpy
     */
    public static String obscurificator_impl = "com.google.code.kaptcha.impl.WaterRipple";
    /**
     * 背景颜色渐变，开始颜色
     */
    public static String background_clear_from = "LIGHT_GRAY";
    /**
     * 背景颜色渐变， 结束颜色
     */
    public static String background_clear_to = "LIGHT_GRAY";
    /**
     * 文本集合，验证码值从此集合中获取
     */
    public static String textproducer_char_string = "1234567890";

}

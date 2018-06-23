package com.apimicroservices.verificationpic.service;

import com.apimicroservices.verificationpic.dao.PicDao;
import com.apimicroservices.verificationpic.entity.ConfigBean;
import com.apimicroservices.verificationpic.util.MapUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
@Service
public class VerficationPicServiceImp implements VerficationPicService {
    @Autowired
    PicDao picDao;

    @Override
    public BufferedImage getKaptchaBean(String key) throws Exception {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        ConfigBean configBean = new ConfigBean();
        Class clazz = Class.forName("com.apimicroservices.verificationpic.entity.ConfigBean");
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            String name = field.getName();
            String config = picDao.getConfig(name);
            String s = "kaptcha." + name.replaceAll("_", ".");
            if (config == null || "".equals(config)) {
                properties.setProperty(s, (String) field.get(configBean));
            } else {
                properties.setProperty(s, config);
            }
        }
        defaultKaptcha.setConfig(new Config(properties));
        String createText = defaultKaptcha.createText();
        String timeout = picDao.getConfig("timeout");
        if (timeout == null || "".equals(timeout)) {
            picDao.save(key, createText, 3*60);
        } else {
            picDao.save(key, createText, Long.parseLong(timeout));
        }

        return defaultKaptcha.createImage(createText);
    }

    @Override
    public BufferedImage getDefaultKaptchaBean(String key) {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "125");
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.char.string", "1234567890");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha.createImage("0000");
    }

    @Override
    public boolean check(String key, String code) {
        String s = picDao.get(key);
        return s != null && s.equals(code);
    }

    @Override
    public Map<String,Object> config(String key, String code) throws Exception{
        ConfigBean configBean = new ConfigBean();
        Class clazz = Class.forName("com.apimicroservices.verificationpic.entity.ConfigBean");
        Field[] fields = clazz.getFields();
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            list.add(field.getName());
        }
        if (list.contains(key)) {
            picDao.setConfig(key, code);
            return MapUtil.getMap(200);
        } else {
            Map<String, Object> map = MapUtil.getMap(-1);
            map.put("msg", "配置参数key值错误");
            return map;
        }
    }

}

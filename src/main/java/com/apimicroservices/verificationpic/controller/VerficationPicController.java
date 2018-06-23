package com.apimicroservices.verificationpic.controller;

import com.apimicroservices.verificationpic.service.VerficationPicService;
import com.apimicroservices.verificationpic.util.MapUtil;
import com.apimicroservices.verificationpic.util.StringUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Documented;
import java.util.Map;
@Api(value="验证码服务",description="验证码服务",tags={"验证码服务接口"})
@Controller
@RequestMapping("/verification/pic")
public class VerficationPicController {
    Logger log = LoggerFactory.getLogger(VerficationPicController.class);
    @Autowired
    VerficationPicService verficationPicService;

    @ApiOperation(value = "生成验证码",consumes = "application/x-www-form-urlencoded")
    @HystrixCommand(fallbackMethod = "defaultKaptchaFallback")
    @RequestMapping(path = "/get" , method = {RequestMethod.POST,RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "验证码标识，建议用时间戳或者随机数",paramType = "query",defaultValue = "12",dataType = "String",required = true)
    })
    public void defaultKaptcha(String key, HttpServletResponse httpServletResponse) throws Exception {
        log.info("生成验证码，key："+key);
        if (key == null || "".equals(key)) {
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            String err = "{\"code\": 300,\"msg\": \"缺少key参数\"}";
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getOutputStream().write(err.getBytes("UTF-8"));
            return;
        }
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        byte[] captchaChallengeAsJpeg = null;
        BufferedImage bufferedImage = verficationPicService.getKaptchaBean(key);
        ImageIO.write(bufferedImage, "jpg", jpegOutputStream);
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @ApiOperation("验证验证码")
    @HystrixCommand(fallbackMethod = "defaultFallback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "生成验证码时的标识",paramType = "query",defaultValue = "12",dataType = "String",required = true),
            @ApiImplicitParam(name="code",value="用户输入的验证码",paramType = "query",required=true)
    })
    @RequestMapping(path = "/check" , method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> check(String key,String code){
        log.info("验证验证码，key："+key+"code:"+code);
        if (StringUtil.isEmpty(key)) {
            Map<String, Object> map = MapUtil.getMap(300);
            map.put("msg", "缺少key参数");
            return map;
        }
        if (StringUtil.isEmpty(code)) {
            Map<String, Object> map = MapUtil.getMap(0);
            map.put("msg", "缺少code参数");
            return MapUtil.getMap(300);
        }
        if (verficationPicService.check(key, code)) {
            return MapUtil.getMap(200);
        } else {
            Map<String, Object> map = MapUtil.getMap(-1);
            map.put("msg", "验证码错误");
            return map;
        }
    }

    @ApiOperation("配置验证码")
    @HystrixCommand(fallbackMethod = "defaultFallback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "配置的属性选项",paramType = "query",defaultValue = "textproducer_char_length",required = true),
            @ApiImplicitParam(name="code",value="属性选项对应的值",paramType = "query",defaultValue = "4",required=true)
    })
    @RequestMapping(path = "/config" , method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> config(String key,String code) throws Exception {
        log.info("设置验证码，key："+key+"code:"+code);
        if (StringUtil.isEmpty(key)) {
            Map<String, Object> map = MapUtil.getMap(300);
            map.put("msg", "缺少key参数");
            return map;
        }
        if (StringUtil.isEmpty(code)) {
            Map<String, Object> map = MapUtil.getMap(0);
            map.put("msg", "缺少code参数");
            return MapUtil.getMap(300);
        }
        return verficationPicService.config(key, code);
    }

    public void defaultKaptchaFallback(String key , HttpServletResponse httpServletResponse,Throwable throwable) throws Exception {
        log.warn("服务降级处理"+throwable.getMessage());
        log.info("生成验证码，key："+key);
        if (key == null || "".equals(key)) {
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            String err = "{\"code\": 300,\"msg\": \"缺少key参数\"}";
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getOutputStream().write(err.getBytes("UTF-8"));
            return;
        }
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        byte[] captchaChallengeAsJpeg = null;
        BufferedImage bufferedImage = verficationPicService.getDefaultKaptchaBean(key);
        ImageIO.write(bufferedImage, "jpg", jpegOutputStream);
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    public Map<String,Object> defaultFallback(String key,String code,Throwable throwable) {
        log.warn("服务降级处理"+throwable.getMessage());
        if (StringUtil.isEmpty(key)) {
            Map<String, Object> map = MapUtil.getMap(300);
            map.put("msg", "缺少key参数");
            return map;
        }
        if (StringUtil.isEmpty(code)) {
            Map<String, Object> map = MapUtil.getMap(0);
            map.put("msg", "缺少code参数");
            return MapUtil.getMap(300);
        }
        return MapUtil.getMap(-1);
    }

}

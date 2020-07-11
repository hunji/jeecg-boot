package org.jeecg.common.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hunji
 * @version 1.0
 * 智奇企业微信发送工具类
 * @date 2020-7-6 12:18
 */
@Component
public class WeChatUtil {

    @Value(value="${jeecg.wechatServiceUrl}")
    private String weChatServiceUrl;

    private static String staticWeChatServiceUrl;

    @PostConstruct
    public void setUserName(){
        staticWeChatServiceUrl = weChatServiceUrl;
    }

    public static String sendInfo(String toUser,String content){

        JSONObject postBody = new JSONObject();
        postBody.put("touser", toUser);
        postBody.put("content", content);

        String str = HttpRequest.post(staticWeChatServiceUrl)
                .body(JSONUtil.parse(postBody).toString())
                .execute().body();
        return str;
    }
}


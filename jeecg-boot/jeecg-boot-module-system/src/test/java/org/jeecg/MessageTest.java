package org.jeecg;

import cn.hutool.core.date.DateUnit;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.modules.message.handle.impl.EmailSendMsgHandle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author hunji
 * @version 1.0
 * @date 2020-10-27 17:50
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageTest {
    @Test
    public void sendEmail(){
        EmailSendMsgHandle msgHandle = new EmailSendMsgHandle();
        String es_receiver = "wy.guo@zqrail.cn";
        String es_title = "jeecg测试邮件";
        String es_content = "测试内容";
        msgHandle.SendMsg(es_receiver ,es_title ,es_content );

    }
    @Test
    public void sendSMS() throws ClientException {
        String smsmode="0";
        boolean b=false;
        String mobile = "15534993486";
        JSONObject obj = new JSONObject();
        obj.put("code", "123456");
        if (CommonConstant.SMS_TPL_TYPE_0.equals(smsmode)) {
            //登录模板
            b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.LOGIN_TEMPLATE_CODE);
        } else if(CommonConstant.SMS_TPL_TYPE_2.equals(smsmode)) {
            //忘记密码模板
            b =DySmsHelper.sendSms(mobile, obj, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
        }
    }

}

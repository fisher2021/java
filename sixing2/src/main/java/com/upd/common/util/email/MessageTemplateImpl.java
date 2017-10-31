package com.upd.common.util.email;

import org.springframework.stereotype.Component;

/**
 * Created by zhangshao on 2015/12/9.
 */
@Component
public class MessageTemplateImpl  implements  MessageTemplate{
    @Override
    public String getForgotPasswordSubject() {
        String subject = "找回密码";
        return subject;
    }

    @Override
    public String getForgotPasswordText() {
        return null;
    }


    @Override
    public String getRegisterSubject() {
        return "邮箱激活";
    }

    @Override
    public String getRegisterText() {
        return null;
    }

}

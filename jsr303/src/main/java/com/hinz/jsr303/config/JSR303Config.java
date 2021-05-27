package com.hinz.jsr303.config;

import com.hinz.jsr303.bean.UserInfo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class JSR303Config {



    @Bean
    public UserInfo getUserInfo(){
        return UserInfo.builder().userName("quanhz").build();
    }

}

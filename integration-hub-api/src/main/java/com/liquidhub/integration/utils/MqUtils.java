package com.liquidhub.integration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.Topic;

@Component
public class MqUtils {

    @Autowired (required = true)
    JmsTemplate jmsTemplate;

    public MqUtils() {
    }

    private static MqUtils instance ;

    @PostConstruct
    public void createStaticInstance() {
        instance = this ;
    }

    public static MqUtils getInstance() {
        return instance;
    }

    public MqUtils(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate ;
    }


    @Autowired
    @Qualifier("userCreateTopic")
    Topic publishUserCreateDestination ;

    @Autowired
    @Qualifier("userUpdateTopic")
    Topic publishUserUpdateDestination  ;

    public void publishUserCreateMessage( String message) {
        jmsTemplate.convertAndSend(publishUserCreateDestination, message);
    }

    public void publishUserUpdateMessage( String message ) {
        jmsTemplate.convertAndSend(publishUserUpdateDestination, message);
    }
}

package com.liquidhub.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/ihub-api-appContext.xml")
public class UserIdLookUpServiceTest {

    @Autowired
    @Qualifier("UserIdLookUpService")
    UserIdLookUpService lookUpService;

    String appName ="UNIT_TEST";
    String appId = "123456";

    @PostConstruct
    public void setup() {

    }

    @Test
    public void getUserEid(){

        String result = lookUpService.getUserEid(appName, appId);

        if (result == "") {
            String eid = lookUpService.getUUID() ;
            lookUpService.addUserAppID(appName, appId, eid);
            result = lookUpService.getUserEid(appName, appId);
            lookUpService.removeUserAppID(appName, appId, eid);
        }

        assert result == "" ? false : true ;

    }

    @Test
    public void getUserAppId(){

        String result = "";

        if (result == "") {
            String eid = lookUpService.getUUID() ;
            lookUpService.addUserAppID(appName, appId, eid);
            result = lookUpService.getUserAppId(appName, eid);
            lookUpService.removeUserAppID(appName, appId, eid);
        }

        assert result == "" ? false : true ;
    }


    @Test
    public void  addUserAppID(){
        String eid = lookUpService.getUUID() ;
        lookUpService.addUserAppID(appName, appId, eid);
        String result = lookUpService.getUserAppId(appName, eid);
        lookUpService.removeUserAppID(appName, appId, eid);
        assert result == "" ? false : true ;

    }

    @Test
    public void getUUID(){
        String eid = lookUpService.getUUID();
        assert eid.length() >  0 ? true : false;

    }

}

package com.lh.test;

import com.liquidhub.integration.MessageProcessor;
import com.liquidhub.integration.beans.User;
import com.liquidhub.integration.utils.IHubUtils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/ihub-api-appContext.xml")
//@ActiveProfiles(value = "local")
public class UpdateContactTest {

    Log logger = LogFactory.getLog(this.getClass());

    @Autowired (required = true)
            @Qualifier(value = "ContactUpdateSync")
    MessageProcessor messageProcessor;

    @Test
    public void updateContaact() {
    	User user = new User();
        user.setFirstName("anonymous22Contact_FN_Updated");
        user.setLastName("anonymous22Contact_LN_Updated");
        user.setEmail("anonymous22contact_fn_Updated@test1.com");        
        user.setFirstNameUpdated("anonymous33Contact_FN_Updated");
        user.setLastNameUpdated("anonymous33Contact_LN_Updated");
        user.setEmailUpdated("anonymous33contact_fn_Updated@test1.com");

        try {

            String json  = IHubUtils.getUserAsJSon(user);
            //                    = new MqUtils(jmsTemplate);
            messageProcessor.process(json);

           // MqUtils.getInstance().publishUserCreateMessage(json);
//
           // MqUtils.getInstance().publishUserUpdateMessage(json);
//
            User user1  = IHubUtils.getUserfromJSon(json);


        } catch (IOException e) {
            logger.error(e, e);
            assert false ;
        }

    }
}
package com.liquidhub.integration;

import com.liquidhub.integration.beans.Address;
import com.liquidhub.integration.beans.User;
import com.liquidhub.integration.utils.IHubUtils;
import com.liquidhub.integration.utils.MqUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/ihub-api-appContext.xml")
//@ActiveProfiles(value = "local")
public class IHubUtilsTest {

    Log logger = LogFactory.getLog(this.getClass());

    @Autowired (required = true)
    JmsTemplate jmsTemplate;

    @Test
    public void convertUser() {
        User user = new User();
        user.setFirstName("Ajay");
        user.setLastName("Yeluri");
        user.setEid("ayeluri@test.com");

        Address address = new Address();
        address.setAddressType("HOME");
        address.setLine1("xyx...");
        user.addAddress(address.getAddressType(), address);

        try {
            String json  = IHubUtils.getUserAsJSon(user);
//                    = new MqUtils(jmsTemplate);
            logger.info(json);
            MqUtils.getInstance().publishUserCreateMessage(json);

            MqUtils.getInstance().publishUserUpdateMessage(json);

            User user1  = IHubUtils.getUserfromJSon(json);



        } catch (IOException e) {
            logger.error(e, e);
            assert false ;
        }

    }
}
package com.liquidhub.integration.ws;

import com.liquidhub.integration.beans.User;
import com.liquidhub.integration.utils.IHubUtils;
import com.liquidhub.integration.utils.MqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWs {

//TODO testing checkin
    @Autowired
    MqUtils mqUtils;

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public int add(@RequestParam(value="fname") String fname, @RequestParam(value="lname") String lname,
                   @RequestParam(value="email") String email) {

        //Create a JSON User Object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);

        String message = IHubUtils.getUserAsJSon(user);
        mqUtils.publishUserCreateMessage(message);


        //publish this String message to Active MQ

        return 200;
    }



}

package com.liquidhub.integration.ws;

import com.liquidhub.integration.beans.Greeting;
import com.liquidhub.integration.beans.User;
import com.liquidhub.integration.utils.IHubUtils;
import com.liquidhub.integration.utils.MqUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserWs {

//TODO testing checkin
    @Autowired
    MqUtils mqUtils;

    Log logger = LogFactory.getLog(this.getClass());


    @RequestMapping(value = "/ping" , method = RequestMethod.GET )
    public @ResponseBody String echo1( @RequestParam (value="name", defaultValue = "") String name) {
        logger.info("Ping Triggered ");
        return "Hello " + name + " !" ;
    }

    @RequestMapping(value = "/echo/{name}"  )
    public Greeting echo(@PathVariable String name) {
        logger.info("Echo Triggered ");
        return new Greeting("Hello " + name + " !" );
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public User add(
            @RequestParam(value="eid") String eid,
            @RequestParam(value="fname", defaultValue = "") String fname,
                   @RequestParam(value="lname", defaultValue = "") String lname,
                   @RequestParam(value="email") String email) throws IOException {

        //Create a JSON User Object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setEid(eid);

        String message = IHubUtils.getUserAsJSon(user);
        MqUtils.getInstance().publishUserCreateMessage(message);

        return user ;
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public User update(@RequestParam(value="fname", defaultValue = "") String fname,
                    @RequestParam(value="lname", defaultValue = "") String lname,
                    @RequestParam(value="email", defaultValue = "") String email) throws IOException {

        //Create a JSON User Object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);

        String message = IHubUtils.getUserAsJSon(user);
        MqUtils.getInstance().publishUserUpdateMessage(message);

        return user ;
    }

}

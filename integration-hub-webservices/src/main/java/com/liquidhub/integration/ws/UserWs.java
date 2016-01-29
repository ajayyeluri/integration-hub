package com.liquidhub.integration.ws;

import com.liquidhub.integration.UserIdLookUpService;
import com.liquidhub.integration.beans.Greeting;
import com.liquidhub.integration.beans.User;
import com.liquidhub.integration.utils.IHubUtils;
import com.liquidhub.integration.utils.MqUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.IOException;

@RestController
public class UserWs {

    @Autowired
    MqUtils mqUtils;

    @Autowired
    @Qualifier("UserIdLookUpService")
    UserIdLookUpService lookUpService;

    Log logger = LogFactory.getLog(this.getClass());


    /**
     * Gets a UID and in case it does not exist will create one , associate it to the app and return the EID for usage
     * @param name
     * @param id
     * @return
     */
    @RequestMapping(value = "/eid/fetch" , method = RequestMethod.GET)
    public @ResponseBody String addOrLookupEID( @RequestParam (value="app_name") String name,
                                             @RequestParam (value="app_id") String id ) {
        String result = lookUpService.getUserEid(name, id);
        if (result.length() == 0 ) {
            String uuid = lookUpService.getUUID();
            lookUpService.addUserAppID(name, id, uuid);
            return uuid ;
        }
        return result ;
    }

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
                       @RequestParam(value="mname", defaultValue = "") String mname,
                       @RequestParam(value="jobtitle", defaultValue = "") String jobtitle,
                       @RequestParam(value="greeting", defaultValue = "") String greeting,
                       @RequestParam(value="dob", defaultValue = "") String dob,
                       @RequestParam(value="sex", defaultValue = "") char sex,
                       @RequestParam(value="eid", defaultValue = "") String eid,
                       @RequestParam(value="email", defaultValue = "") String email) throws IOException {


        //Create a JSON User Object
        User user = new User();
        user.setEmail(email);
        user.setFirstName(fname);
        user.setLastName(lname);
        user.setGender(sex);
        user.setEid(eid);
        user.setJobTitle(jobtitle);
        user.setGreeting(greeting);

        String message = IHubUtils.getUserAsJSon(user);
        MqUtils.getInstance().publishUserUpdateMessage(message);

        return user ;
    }

    @RequestMapping(value = "/update-user/json", method = RequestMethod.POST)
    public User update(@RequestParam(value="payload") String payload
                       ) throws IOException {

        logger.info(payload);

        User user1 = IHubUtils.getUserfromJSon(payload);
        MqUtils.getInstance().publishUserUpdateMessage(payload);

        return user1 ;
    }

    @RequestMapping(value = "/update-user/json2")
    public User updatewithGet(@RequestParam(value="payload") String payload
    ) throws IOException {

        logger.debug(payload);

        BASE64Decoder decoder = new BASE64Decoder();
        String json = new String (decoder.decodeBuffer(payload));

        logger.info(json );

        User user1 = IHubUtils.getUserfromJSon(json);
        MqUtils.getInstance().publishUserUpdateMessage(json);

        return user1 ;
    }


}

package com.liquidhub.integration.utils;

import com.liquidhub.integration.beans.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class IHubUtils {

    static Log logger = LogFactory.getLog(IHubUtils.class.getName());

    public static String getUserAsJSon(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString  =  mapper.writeValueAsString(user);
        logger.info(jsonString);
        return jsonString;
    }


    public static User getUserfromJSon(String userString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user  = mapper.readValue(userString, User.class);
        logger.info(user.toString());
        return user ;
    }


}

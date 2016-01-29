package com.liquidhub.integration;

import com.liferay.portal.model.User;

import java.io.IOException;

/**
 * Created by ayeluri on 1/28/2016.
 */
public interface WebServiceClientInterface {
    String getEID (User user) throws IOException;

    String getEID(String screenName, String url) throws IOException;

    void callUpdateWebService(User user) throws IOException;

    void callUpdateWebService(User user, String url) throws IOException;

    void callUpdateWebService2(User user) throws IOException;

    void callUpdateWebService2(User user, String url) throws IOException;

    void callWebService(User user) throws IOException;

    void callWebService(User user, String url) throws IOException;
}

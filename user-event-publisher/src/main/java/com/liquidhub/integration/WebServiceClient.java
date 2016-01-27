package com.liquidhub.integration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServiceClient {

    public void callUpdateWebService(User user) {

        String url = PortalUtil.getPortalProperties().getProperty("user.update.url");
        System.out.println("User Add URL ---- " + url );

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("fname", user.getFirstName()));
        urlParameters.add(new BasicNameValuePair("lname", user.getLastName()));
        urlParameters.add(new BasicNameValuePair("mname", user.getMiddleName()));
        urlParameters.add(new BasicNameValuePair("email", user.getEmailAddress()));

        urlParameters.add(new BasicNameValuePair("eid", user.getScreenName()));

        try {
            List<Address> addresses =  user.getAddresses();
            if (addresses!=null && addresses.size() > 0 ) {
                Address address  = addresses.get(1);
                //TODO
                //add address params for address 1 only
                //address-line1
                //address-line2
                //city
                //state
                //zip


            }
        } catch (SystemException e) {
            e.printStackTrace();
        }

        try {
            urlParameters.add(new BasicNameValuePair("dob", String.valueOf(user.getBirthday().getTime())));
            urlParameters.add(new BasicNameValuePair("sex", user.getFemale() ? "F" : "M"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            HttpResponse response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void callWebService(User user) {

        String url = PortalUtil.getPortalProperties().getProperty("user.add.url");
        System.out.println("User Add URL ---- " + url );

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("fname", user.getFirstName()));
        urlParameters.add(new BasicNameValuePair("lname", user.getLastName()));
        urlParameters.add(new BasicNameValuePair("mname", user.getMiddleName()));
        urlParameters.add(new BasicNameValuePair("email", user.getEmailAddress()));
        urlParameters.add(new BasicNameValuePair("eid", user.getScreenName()));
        try {
            urlParameters.add(new BasicNameValuePair("dob", String.valueOf(user.getBirthday().getTime())));
            urlParameters.add(new BasicNameValuePair("sex", user.getFemale() ? "F" : "M"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            HttpResponse response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

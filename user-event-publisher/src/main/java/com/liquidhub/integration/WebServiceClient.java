package com.liquidhub.integration;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class WebServiceClient {

    public static final String USER_WEBSERVICES_URL = "user.webservices.url";

    //user.webservices.url=http://10.60.1.165:9083/inegration-hub-webservices-1.0-SNAPSHOT/eid/fetch?app_name=test&app_id=1234

    String appName = "liferay";

    public String getEID (User user) throws IOException {
        String url = PortalUtil.getPortalProperties().getProperty(USER_WEBSERVICES_URL);
        return getEID(user, url+"/eid/fetch");

    }

    public String getEID (User user, String url ) throws IOException{


        System.out.println("User /eid/fetch URL ---- " + url );

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet post = new HttpGet(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("app_name", appName));
        urlParameters.add(new BasicNameValuePair("app_id", user.getScreenName()));

        HttpResponse response = null;
            response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

    }

    public void callUpdateWebService(User user) throws IOException {
        String url = PortalUtil.getPortalProperties().getProperty(USER_WEBSERVICES_URL) + "/update-user";
        System.out.println("User Add URL ---- " + url );

          callUpdateWebService(user, url );
    }

    public void callUpdateWebService(User user, String url ) throws IOException {


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("fname", user.getFirstName()));
        urlParameters.add(new BasicNameValuePair("lname", user.getLastName()));
        urlParameters.add(new BasicNameValuePair("mname", user.getMiddleName()));
        urlParameters.add(new BasicNameValuePair("email", user.getEmailAddress()));

        String eid = getEID(user);

        urlParameters.add(new BasicNameValuePair("eid", eid));

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

                urlParameters.add(new BasicNameValuePair("street1", address.getStreet1()));
                urlParameters.add(new BasicNameValuePair("street2", address.getStreet2()));
                urlParameters.add(new BasicNameValuePair("street4", address.getStreet3()));
                urlParameters.add(new BasicNameValuePair("city", address.getCity()));
                urlParameters.add(new BasicNameValuePair("zip", address.getZip()));
            //  urlParameters.add(new BasicNameValuePair("region", address.getRegion()));
            //  urlParameters.add(new BasicNameValuePair("regionId", address.getRegionID()));
            //  urlParameters.add(new BasicNameValuePair("state", address.getCountry()));
            //  urlParameters.add(new BasicNameValuePair("type", address.getType()));
            //  urlParameters.add(new BasicNameValuePair("type", address.getType()));
            //  urlParameters.add(new BasicNameValuePair("type", address.getType()));
            //  urlParameters.add(new BasicNameValuePair("primary", address.getPrimary()));
                
                
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
        /*try {
			Contact contact = user.getContact();
			//To Add any contact details if needed 
						
		} catch (PortalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
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


    public void callWebService(User user) throws IOException {


        String url = PortalUtil.getPortalProperties().getProperty(USER_WEBSERVICES_URL)+"/add-user";
        System.out.println("User Add URL ---- " + url );

        String eid = getEID(user);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("fname", user.getFirstName()));
        urlParameters.add(new BasicNameValuePair("lname", user.getLastName()));
        urlParameters.add(new BasicNameValuePair("mname", user.getMiddleName()));
        urlParameters.add(new BasicNameValuePair("email", user.getEmailAddress()));
        urlParameters.add(new BasicNameValuePair("eid", eid));
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

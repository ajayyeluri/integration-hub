import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.User;
import com.liquidhub.integration.WebServiceClient;
import com.liquidhub.integration.utils.IHubUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayeluri on 1/28/2016.
 */
public class WebServiceClientTest {

    @Test
    public void getEid() {
        try {
            String eid = (new WebServiceClient()).getEID("ayeluri", "http://10.60.1.165:9083/inegration-hub-webservices-1.0-SNAPSHOT/eid/fetch");
            System.out.println(eid);
            eid = (new WebServiceClient()).getEID("ayeluri", "http://10.60.1.165:9083/inegration-hub-webservices-1.0-SNAPSHOT/eid/fetch");
            System.out.println(eid);eid = (new WebServiceClient()).getEID("ayeluri", "http://10.60.1.165:9083/inegration-hub-webservices-1.0-SNAPSHOT/eid/fetch");
            System.out.println(eid);eid = (new WebServiceClient()).getEID("ayeluri", "http://10.60.1.165:9083/inegration-hub-webservices-1.0-SNAPSHOT/eid/fetch");
            System.out.println(eid);
            assert (eid==""||eid==null )? false : true ;
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
        }

    }

    @Test
    public void callUpdateWebService2() throws IOException {

        String eid = "test1234";

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        com.liquidhub.integration.beans.User user1 = new com.liquidhub.integration.beans.User();
        user1.setGreeting("g1");
        user1.setJobTitle("g2");
        user1.setFirstName("g2");
        user1.setLastName("g2");
        user1.setEmail("g2");
        user1.setEid(eid);

        com.liquidhub.integration.beans.Address address = new com.liquidhub.integration.beans.Address();
        address.setLine1("test 123");
        address.setAddressType("home");
        user1.addAddress("home", address);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://10.60.1.165:9083/integration-hub-webservices-1.0-SNAPSHOT/update-user/json");

        try {
            urlParameters.add(new BasicNameValuePair("payload", IHubUtils.getUserAsJSon(user1)));

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

import com.liferay.portal.model.User;
import com.liquidhub.integration.WebServiceClient;
import org.junit.Test;

import java.io.IOException;

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
}

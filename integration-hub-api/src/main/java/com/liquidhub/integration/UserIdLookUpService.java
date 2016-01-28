package com.liquidhub.integration;

/**
 * Created by ayeluri on 1/27/2016.
 */
public interface UserIdLookUpService {

    public String getUserEid(String appName, String appId);

    public String getUserAppId(String appName, String eid);

//    void  setUserEid(String appName, String appId);

    public void  addUserAppID(String appName, String appId, String eid);

    public void  removeUserAppID(String appName, String appId, String eid);


    public String getUUID();
}

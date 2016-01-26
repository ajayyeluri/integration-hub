This application  helps sync user information from Liferay to sales force

Pre-Req
- Java 8
- MVN 3
- Liferay 6.2 EE SP 14
- Active MQ
- Tomcat 7 or above

Instructions:

1. Build the apps
        mvn install ( this builds all of the apps )


2. Add the following properties to tomcat runtime
    jms.host = <hostname> default localhost
    jms.port = <port> default 61616

    Open CATALINA_HOME/LIB
    Add properties file ihub.properties
    add the following properties to ihub.properties
    sf.username = <username to login to SF?
    sf.password = <password>[<security code>]
    sf.url = <sales force URL>


3. Start tomcat
    deploy the war files to $CATALINA_HOME/webapps
        integration-hub-sf-sync
        integration-hub-webservices
    Assume that the web service war is running @ http://localhost:8080/integration-hub-webservices
    Test the deployment
        Open a web browser
        Go to url for a ping test
            http://localhost:8080/integration-hub-webservices-1.0.0-SNAPSHOT/ping



4. Go to Liferay Home Directory
    Open portal-ext.properties
    set the below property
    user.add.url = http://[localhost][:8080]/integration-hub-webservices-1.0.0-SNAPSHOT/add-user
    copy the user-event-publisher-hook[-version].war to LIFERAY_HOME/deploy directory

5.  Configure Liferay
    Start Liferay
    login as admin
    go to control panel >> Configuration >> Custom Fields
    Add a new custom field to the user object
    Field Name : sent-to-ihub
    Field Type : boolean

6. Testing connectivity
    Register as a new user in liferay
    The user should show up as a contact in salesforce

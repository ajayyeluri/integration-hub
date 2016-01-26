package com.liquidhub.integration;


import com.liquidhub.integration.beans.User;
import com.liquidhub.integration.utils.IHubUtils;
import com.sforce.soap.enterprise.*;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

public abstract class MessageProcessorImpl implements MessageProcessor {

    protected Log logger = LogFactory.getLog(this.getClass());
    String username ;
    String password ;

    protected EnterpriseConnection connection;


    protected com.liquidhub.integration.beans.User getUser(String userJSon) throws IOException {
        return IHubUtils.getUserfromJSon(userJSon);
    }

    boolean initGood ;

    public void process(String message) {
        try {
            init();
            User user = getUser(message);
            if( ! processMessage(user)); processingError(message, "Error Processing Message", null);
        } catch (Exception e) {
            logger.error(e, e);
            processingError(message, "Error Processing Message", e);
        }
    }

    protected void processingError (String originalMessage, String error, Exception e ) {

    }

    public abstract boolean processMessage(User user);
    ConnectorConfig config;
    protected void init () {
        config = new ConnectorConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setTraceMessage(true);
        try {
            connection = Connector.newConnection(config);
        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }

        initGood = true ;
    }

    // queries and displays the 5 newest contacts
    protected   void queryContacts() {

        System.out.println("Querying for the 5 newest Contacts...");

        try {

            // query for the 5 newest contacts
            QueryResult queryResults = connection.query("SELECT Id, FirstName, LastName, Account.Name " +
                    "FROM Contact WHERE AccountId != NULL ORDER BY CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0) {
                for (int i=0;i<queryResults.getRecords().length;i++) {
                    // cast the SObject to a strongly-typed Contact
                    Contact c = (Contact)queryResults.getRecords()[i];
                    System.out.println("Id: " + c.getId() + " - Name: "+c.getFirstName()+" "+
                            c.getLastName()+" - Account: "+c.getAccount().getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // create 5 test Accounts
    private void createAccounts() {

        System.out.println("Creating 5 new test Accounts...");
        Account[] records = new Account[5];

        try {

            // create 5 test accounts
            for (int i=0;i<5;i++) {
                Account a = new Account();
                a.setName("Test Account "+i);
                records[i] = a;
            }

            // create the records in Salesforce.com
            SaveResult[] saveResults = connection.create(records);

            // check the returned results for any errors
            for (int i=0; i< saveResults.length; i++) {
                if (saveResults[i].isSuccess()) {
                    System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
                } else {
                    com.sforce.soap.enterprise.Error[] errors = saveResults[i].getErrors();
                    for (int j=0; j< errors.length; j++) {
                        System.out.println("ERROR creating record: " + errors[j].getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // updates the 5 newly created Accounts
    private void updateAccounts() {

        System.out.println("Update the 5 new test Accounts...");
        Account[] records = new Account[5];

        try {

            QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
                    "CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0) {
                for (int i=0;i<queryResults.getRecords().length;i++) {
                    // cast the SObject to a strongly-typed Account
                    Account a = (Account)queryResults.getRecords()[i];
                    System.out.println("Updating Id: " + a.getId() + " - Name: "+a.getName());
                    // modify the name of the Account
                    a.setName(a.getName()+" -- UPDATED");
                    records[i] = a;
                }
            }

            // update the records in Salesforce.com
            SaveResult[] saveResults = connection.update(records);

            // check the returned results for any errors
            for (int i=0; i< saveResults.length; i++) {
                if (saveResults[i].isSuccess()) {
                    System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
                } else {
                    Error[] errors = saveResults[i].getErrors();
                    for (int j=0; j< errors.length; j++) {
                        System.out.println("ERROR updating record: " + errors[j].getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteAccounts() {

        System.out.println("Deleting the 5 new test Accounts...");
        String[] ids = new String[5];

        try {

            QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
                    "CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0) {
                for (int i=0;i<queryResults.getRecords().length;i++) {
                    // cast the SObject to a strongly-typed Account
                    Account a = (Account)queryResults.getRecords()[i];
                    // add the Account Id to the array to be deleted
                    ids[i] = a.getId();
                    System.out.println("Deleting Id: " + a.getId() + " - Name: "+a.getName());
                }
            }

            // delete the records in Salesforce.com by passing an array of Ids
            DeleteResult[] deleteResults = connection.delete(ids);

            // check the results for any errors
            for (int i=0; i< deleteResults.length; i++) {
                if (deleteResults[i].isSuccess()) {
                    System.out.println(i+". Successfully deleted record - Id: " + deleteResults[i].getId());
                } else {
                    Error[] errors = deleteResults[i].getErrors();
                    for (int j=0; j< errors.length; j++) {
                        System.out.println("ERROR deleting record: " + errors[j].getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

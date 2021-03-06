package com.liquidhub.integration.contact;

import com.liquidhub.integration.MessageProcessorImpl;
import com.liquidhub.integration.UserIdLookUpService;
import com.liquidhub.integration.beans.User;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("ContactAddSync")
public class ContactAddSync extends MessageProcessorImpl {

    @Autowired
    @Qualifier("UserIdLookUpService")
    UserIdLookUpService userIdLookUpService;

    @Override
    public boolean processMessage(User  user) {

//        boolean alwayadd = true ;

       if (! isContactPresent(user)) {
           // add the contact to Sales Force
            Contact contact = new Contact();
            contact.setFirstName(user.getFirstName());
            contact.setLastName(user.getLastName());
//            contact.setAccountId(user.getEid());
            contact.setEmail(user.getEmail());

            try {

                // create the records in Salesforce.com
                SaveResult[] saveResults = connection.create(new Contact[]{contact});

                // check the returned results for any errors
                for (int i = 0; i < saveResults.length; i++) {
                    if (saveResults[i].isSuccess()) {
                        logger.info(i + ". Successfully created record - Id: " + saveResults[i].getId());
                        userIdLookUpService.addUserAppID(SALES_FORCE_APP_NAME, saveResults[i].getId(), user.getEid());
                    } else {
                        com.sforce.soap.enterprise.Error[] errors = saveResults[i].getErrors();
                        for (int j = 0; j < errors.length; j++) {
                            logger.info("ERROR creating record: " + errors[j].getMessage());
                            processingError(user.toString(), errors[j].getMessage(), null );
                        }
                    }
                }

            } catch (Exception e) {
                processingError(user.toString(), e.getMessage(), e);
                logger.error(e, e);
            }

        } else {
            logger.warn(user.toString() + " --- Contact is already present in SalesForce");
            processingError(user.toString(), "Contact is already present in SalesForce", null);

        }
        return false;
    }

    protected boolean isContactPresent(User user) {

        // check SF if contact is already present using the email or AccoutnID
        try {

            String userId = userIdLookUpService.getUserAppId(SALES_FORCE_APP_NAME, user.getEid());

            // query for the 5 newest contacts
            QueryResult queryResults = connection.query("SELECT Id, FirstName, LastName, Account.Name " +
                    "FROM Contact WHERE Id= '" + userId +
                    "' ORDER BY CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0)  return true ;
        } catch (Exception e) {
            logger.warn(e, e);
        }
        // if present return true else false
        return false ;
    }
}



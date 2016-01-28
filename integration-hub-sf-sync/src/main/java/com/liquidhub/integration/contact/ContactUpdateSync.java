package com.liquidhub.integration.contact;

import com.liquidhub.integration.MessageProcessorImpl;
import com.liquidhub.integration.beans.User;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.SObject;

import org.springframework.stereotype.Component;

@Component("ContactUpdateSync")
public class ContactUpdateSync extends MessageProcessorImpl {

	
    @Override
    public boolean processMessage(User  user) {

        if (isConctactPresent(user)) {
            // add the contact to Sales Force
            Contact contact = new Contact();
            contact.setId(user.getEid());
            contact.setFirstName(user.getFirstNameUpdated());
            contact.setLastName(user.getLastNameUpdated());
            
            contact.setEmail(user.getEmailUpdated());
           // contact.setAccountId(user.getAccid());
           

            try {

                // create the records in Salesforce.com
                SaveResult[] saveResults = connection.update(new Contact[]{contact});

                // check the returned results for any errors
                for (int i = 0; i < saveResults.length; i++) {
                    if (saveResults[i].isSuccess()) {
                        logger.info(i + ". Successfully created record - Id: " + saveResults[i].getId());
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

    protected boolean isConctactPresent(User user) {

        // check SF if contact is already present using the email or AccoutnID
        try {
        	//SELECT Id,AccountId,Email,FirstName,LastName FROM Contact where AccountId = '0012800000F6Mi7AAF' and FirstName='vinay33' and LastName='domala' and Email='rose@edge.com'
            // query for the 5 newest contacts
            QueryResult queryResults = connection.query("SELECT Id,Email,FirstName,LastName FROM Contact where FirstName = '"+user.getFirstName() +"' and LastName='"+user.getLastName() +"' and Email ='"+user.getEmail() +"'");
            if (queryResults.getSize() > 0){
            	SObject[] sObjects=queryResults.getRecords();
            	
            	for(int i=0;i<=sObjects.length-1;i++){
            	
            		Contact contact=	(Contact)sObjects[i];
            		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX;   "+contact.getId());            		
            		user.setEid(contact.getId());
            	}
            	
            	return true ;
            }
            	
        } catch (Exception e) {
            logger.warn(e, e);
        }
        // if present return true else false
        return false ;
    }

	/*@Override
	public boolean processMessage(User user) {
		// TODO Auto-generated method stub
		return false;
	}*/
}



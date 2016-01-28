package com.liquidhub.integration.beans;

import java.util.HashMap;
import java.util.Map;
//Here user is nothing but a contact in Salesforce.
//Each contact will be under some Account. An Account can have several contacts.
public class User {

    String firstName, lastName,  eid, email , telNumber, accid, AccountName,  firstNameUpdated, lastNameUpdated, emailUpdated;

	public String getFirstNameUpdated() {
		return firstNameUpdated;
	}

	public void setFirstNameUpdated(String firstNameUpdated) {
		this.firstNameUpdated = firstNameUpdated;
	}

	public String getLastNameUpdated() {
		return lastNameUpdated;
	}

	public void setLastNameUpdated(String lastNameUpdated) {
		this.lastNameUpdated = lastNameUpdated;
	}

	public String getEmailUpdated() {
		return emailUpdated;
	}

	public void setEmailUpdated(String emailUpdated) {
		this.emailUpdated = emailUpdated;
	}

	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

   
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    Map<String, Address> addressMap = new HashMap<String, Address>();

    Map<String, Telephone> telephoneMap = new HashMap<String, Telephone>();

    public Map<String, Telephone> getTelephoneMap() {
        return telephoneMap;
    }

    public void setTelephoneMap(Map<String, Telephone> telephoneMap) {
        this.telephoneMap = telephoneMap;
    }

    public Map<String, Address> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<String, Address> addressMap) {
        this.addressMap = addressMap;
    }

    public void addAddress(String type, Address address) {
        this.addressMap.put(type, address);
    }

    public void addTelephone(String type, Telephone telephone) {
        this.telephoneMap.put(type, telephone);
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eid='" + eid + '\'' +
                ", email='" + email + '\'' +
                ", telNumber='" + telNumber + '\'' +
                '}';
    }
}

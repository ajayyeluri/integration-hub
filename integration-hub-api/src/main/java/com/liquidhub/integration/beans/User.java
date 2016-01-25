package com.liquidhub.integration.beans;

import java.util.HashMap;
import java.util.Map;

public class User {

    String firstName, lastName,  eid, email , telNumber ;

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

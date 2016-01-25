package com.liquidhub.integration.beans;

public class Telephone {

    String type ="H";
    String telNumber = "888-888-8888";
    String countryCode = "1" ;

    @Override
    public String toString() {
        return "Telephone{" +
                "type='" + type + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}

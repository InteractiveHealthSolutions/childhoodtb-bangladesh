package com.ihsinformatics.childhoodtb_mobile.model;

/**
 * Created by Shujaat on 5/2/2017.
 */
public class Report {
    private String numberOfContact;
    private String numberOfChildhood;
    private String numberOfAdult;
    private String numberOfScreened;
    private String numberOfSymptomatic;
    private String numberOfContactsEligibleForPti;

    public String getNumberOfChildhood() {
        return numberOfChildhood;
    }

    public void setNumberOfChildhood(String numberOfChildhood) {
        this.numberOfChildhood = numberOfChildhood;
    }

    public String getNumberOfAdult() {
        return numberOfAdult;
    }

    public void setNumberOfAdult(String numberOfAdult) {
        this.numberOfAdult = numberOfAdult;
    }

    public String getNumberOfContact() {
        return numberOfContact;
    }

    public void setNumberOfContact(String numberOfContact) {
        this.numberOfContact = numberOfContact;
    }

    public String getNumberOfScreened() {
        return numberOfScreened;
    }

    public void setNumberOfScreened(String numberOfScreened) {
        this.numberOfScreened = numberOfScreened;
    }

    public String getNumberOfSymptomatic() {
        return numberOfSymptomatic;
    }

    public void setNumberOfSymptomatic(String numberOfSymptomatic) {
        this.numberOfSymptomatic = numberOfSymptomatic;
    }

    public String getNumberOfContactsEligibleForPti() {
        return numberOfContactsEligibleForPti;
    }

    public void setNumberOfContactsEligibleForPti(String numberOfContactsEligibleForPti) {
        this.numberOfContactsEligibleForPti = numberOfContactsEligibleForPti;
    }
}

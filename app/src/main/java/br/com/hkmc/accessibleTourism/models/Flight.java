package br.com.hkmc.accessibleTourism.models;

import java.io.Serializable;

public class Flight implements Serializable{
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGoingTime() {
        return goingTime;
    }

    public void setGoingTime(String goingTime) {
        this.goingTime = goingTime;
    }

    public String getGoingBoardingTime() {
        return goingBoardingTime;
    }

    public void setGoingBoardingTime(String goingBoardingTime) {
        this.goingBoardingTime = goingBoardingTime;
    }

    public String getReturnsTime() {
        return returnsTime;
    }

    public void setReturnsTime(String returnsTime) {
        this.returnsTime = returnsTime;
    }

    public String getReturnsBoardingTime() {
        return returnsBoardingTime;
    }

    public void setReturnsBoardingTime(String returnsBoardingTime) {
        this.returnsBoardingTime = returnsBoardingTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGoingDate() {
        return goingDate;
    }

    public void setGoingDate(String goingDate) {
        this.goingDate = goingDate;
    }

    public String getReturnsDate() {
        return returnsDate;
    }

    public void setReturnsDate(String returnsDate) {
        this.returnsDate = returnsDate;
    }

    private String goingTime;
    private String goingBoardingTime;
    private String returnsTime;
    private String returnsBoardingTime;
    private String value;
    private String currency;
    private String origin;
    private String destination;
    private String goingDate;
    private String returnsDate;
}

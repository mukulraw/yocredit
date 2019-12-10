package com.mrtecks.yocredit.updatePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("father")
    @Expose
    private String father;
    @SerializedName("mother")
    @Expose
    private String mother;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("income")
    @Expose
    private String income;
    @SerializedName("reference1")
    @Expose
    private String reference1;
    @SerializedName("reference2")
    @Expose
    private String reference2;
    @SerializedName("aadhar1")
    @Expose
    private String aadhar1;
    @SerializedName("aadhar2")
    @Expose
    private String aadhar2;
    @SerializedName("passbook")
    @Expose
    private String passbook;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("tenover")
    @Expose
    private String tenover;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("pan")
    @Expose
    private String pan;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }

    public String getReference2() {
        return reference2;
    }

    public void setReference2(String reference2) {
        this.reference2 = reference2;
    }

    public String getAadhar1() {
        return aadhar1;
    }

    public void setAadhar1(String aadhar1) {
        this.aadhar1 = aadhar1;
    }

    public String getAadhar2() {
        return aadhar2;
    }

    public void setAadhar2(String aadhar2) {
        this.aadhar2 = aadhar2;
    }

    public String getPassbook() {
        return passbook;
    }

    public void setPassbook(String passbook) {
        this.passbook = passbook;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTenover() {
        return tenover;
    }

    public void setTenover(String tenover) {
        this.tenover = tenover;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}

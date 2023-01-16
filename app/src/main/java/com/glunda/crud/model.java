package com.glunda.crud;

public class model {
    String productName,gender,productsize,url;

    public model() {
    }

    public model(String productName, String gender, String productsize, String url) {
        this.productName = productName;
        this.gender = gender;
        this.productsize = productsize;
        this.url = url;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProductsize() {
        return productsize;
    }

    public void setProductsize(String productsize) {
        this.productsize = productsize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

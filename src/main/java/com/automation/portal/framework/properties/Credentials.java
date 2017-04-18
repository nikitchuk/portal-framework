package com.automation.portal.framework.properties;

public enum Credentials {

    EMAIL("denisa.sufliarska@embedit.cz"),
    PASSWORD("hcichina33");

    private String value;

    Credentials(String s){
        this.value = s;
    }

    public void set(String s){
        this.value = s;
    }

    public String get(){
        return this.value;
    }

}

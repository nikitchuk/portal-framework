package com.automation.portal.framework.properties;

public enum Credentials {

    EMAIL("a.a.nikitchuk@gmail.com"),
    PASSWORD("Sasha2388");

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

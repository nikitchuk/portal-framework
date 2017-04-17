package com.automation.portal.framework.properties;

public enum TestProps {

    BROWSER_TYPE(""),
    URL(""),
    OS("");

    private String value;

    TestProps(String s) {
        this.value = s;
    }

    public void set(String s) {
        this.value = s;
    }

    public String get() {
        return this.value;
    }

}

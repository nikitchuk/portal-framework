package com.automation.portal.framework.properties;


public enum TestData {


    SEARCH_REQUEST("Iphone Cover"),
    SEARCH_MODEL("iPhone 6"),
    SEARCH_CONSTRAINT_CAPTION_CONTAINER("For iPhone 6"),
    NUMBER_ITEM("3"); //max size of list item on page 50


    private String value;

    TestData(String s) {
        this.value = s;
    }

    public String get() {

        return this.value;
    }

    public void set(String value) {
        this.value = value;
    }

}

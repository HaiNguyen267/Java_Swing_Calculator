package model;

public enum Error {
    STARTS_WITH_OPERATOR("STARTS WITH OPERATOR!"),
    ENDS_WITH_OPERATOR("ENDS WITH OPERATOR!"),
    CONTAINS_ZERO_DIVISION("CONTAINS ZERO DIVISION!");

    private String msg;

     Error(String msg) {
        this.msg = msg;
    }
}

package com.topakkaya.reading.enums;

public enum ReturnType {
    SUCCESS(0, "Operation Successful"),
    FAIL(-1, "Operation Failed");

    int resultCode;
    String resultMessage;

    ReturnType(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }
}

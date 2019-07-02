package com.inflearn.springbootmvc.sample;

public class AppError {

    private String message;
    private String reason;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "AppError{" +
                "message='" + message + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

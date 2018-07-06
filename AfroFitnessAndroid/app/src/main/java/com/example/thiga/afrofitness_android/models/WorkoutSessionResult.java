package com.example.thiga.afrofitness_android.models;

public class WorkoutSessionResult {
    private boolean error;
    private String message;

    public WorkoutSessionResult() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

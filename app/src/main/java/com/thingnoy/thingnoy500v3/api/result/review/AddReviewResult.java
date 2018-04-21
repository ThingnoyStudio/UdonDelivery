package com.thingnoy.thingnoy500v3.api.result.review;

/**
 * Created by HBO on 1/3/2561.
 */

public class AddReviewResult {
    private Boolean status;
    private String data;

    public AddReviewResult() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

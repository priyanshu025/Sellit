package com.example.sellit.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class HighestPrice {
    @SerializedName("price")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

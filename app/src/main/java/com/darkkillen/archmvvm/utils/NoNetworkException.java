package com.darkkillen.archmvvm.utils;

import java.io.IOException;

public class NoNetworkException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }

}

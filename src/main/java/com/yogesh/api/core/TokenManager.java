package com.yogesh.api.core;

import com.yogesh.api.clients.AuthClient;

public final class TokenManager {

    private static String token;

    private TokenManager(){
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static String getToken(){
        if(token==null){
            token = AuthClient.generateToken();
        }
        return token;
    }

}

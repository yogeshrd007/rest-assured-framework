package com.yogesh.api.core;

import com.yogesh.api.clients.AuthClient;

public final class TokenManager {

    private static volatile String token;
    private static final Object LOCK = new Object();

    private TokenManager(){
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static String getToken(){
        if(token==null){
            synchronized (LOCK){
              if(token==null){
                  token = AuthClient.generateToken();
              }
            }

        }
        return token;
    }
    public static void clearToken() {
        synchronized (LOCK){
            token = null;
        }
    }

}

package com.yogesh.api.reporting;

import io.qameta.allure.Allure;

public class AllureAttachmentManager {

    private AllureAttachmentManager() {}

    private static void attach(String name, String content){
        if(content == null || content.isBlank()){
            return;
        }
        Allure.addAttachment(name, content);
    }

    public static void attachRequest(String request){
        attach("Request",request);
    }

    public static void attachResponse(String response){
        attach("Response",response);
    }
}

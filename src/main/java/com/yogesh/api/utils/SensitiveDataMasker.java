package com.yogesh.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class SensitiveDataMasker {

    private static final String MASK = "********";

    private static final Set<String> SENSITIVE_HEADERS = Set.of(
            "authorization",
            "cookie",
            "token",
            "password",
            "apikey"
    );

    private static final Set<String> SENSITIVE_FIELDS = Set.of(
            "password",
            "token",
            "authorization",
            "apikey",
            "secret"
    );

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();


    private SensitiveDataMasker() {

    }

    public static Headers maskHeaders(Headers headers) {

        List<Header> maskedHeaders = new ArrayList<>();

        for (Header header : headers) {
            if (SENSITIVE_HEADERS.contains(header.getName().toLowerCase())) {
                maskedHeaders.add(new Header(header.getName(), MASK));
            } else {
                maskedHeaders.add(header);
            }
        }
        return new Headers(maskedHeaders);
    }

    public static String maskJsonBody(String jsonBody){
        try {
            JsonNode rootNode = OBJECT_MAPPER.readTree(jsonBody);
            ObjectNode objectNode =(ObjectNode) rootNode;

            Iterator<String> fieldNames =objectNode.fieldNames();

            while(fieldNames.hasNext()){
                String fieldName = fieldNames.next();
                if (SENSITIVE_FIELDS.contains(fieldName.toLowerCase())){
                    objectNode.put(fieldName,MASK);
                }
            }
            
            return OBJECT_MAPPER.writeValueAsString(objectNode);
        } catch (Exception e) {
            return jsonBody;

        }

    }
}

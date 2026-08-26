package com.yogesh.api.filters;

import com.yogesh.api.reporting.AllureAttachmentManager;
import com.yogesh.api.utils.CorrelationIdGenerator;
import com.yogesh.api.utils.CorrelationIdManager;
import com.yogesh.api.utils.SensitiveDataMasker;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger =
            LogManager.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        String correlationId = CorrelationIdGenerator.generate();
        CorrelationIdManager.set(correlationId);

        try {
            logRequest(requestSpec);

            Response response = ctx.next(requestSpec, responseSpec);

            logResponse(response);

            return response;
        } finally {
            CorrelationIdManager.clear();
        }
    }

    private void logRequest(FilterableRequestSpecification requestSpec) {

        StringBuilder requestLog = new StringBuilder();

        requestLog.append(System.lineSeparator());
        requestLog.append("==================================================").append(System.lineSeparator());
        requestLog.append("HTTP REQUEST").append(System.lineSeparator());
        requestLog.append("Correlation ID : ")
                .append(CorrelationIdManager.get())
                .append(System.lineSeparator());
        requestLog.append("==================================================").append(System.lineSeparator());

        requestLog.append("Method  : ")
                .append(requestSpec.getMethod())
                .append(System.lineSeparator());

        requestLog.append("URI     : ")
                .append(requestSpec.getURI())
                .append(System.lineSeparator());

        requestLog.append(System.lineSeparator())
                .append("Headers")
                .append(System.lineSeparator())
                .append("--------------------------------------------------")
                .append(System.lineSeparator());

        Headers maskedHeaders = SensitiveDataMasker.maskHeaders(requestSpec.getHeaders());

                requestLog.append(maskedHeaders)
                .append(System.lineSeparator());

        if (requestSpec.getBody() != null) {
            requestLog.append(System.lineSeparator())
                    .append("Body")
                    .append(System.lineSeparator())
                    .append("--------------------------------------------------")
                    .append(System.lineSeparator());

            String maskedBody = SensitiveDataMasker.maskJsonBody(
                    String.valueOf((Object)requestSpec.getBody()));

                  requestLog.append(maskedBody)
                    .append(System.lineSeparator());
        }

        logger.info(requestLog.toString());
        AllureAttachmentManager.attachRequest(requestLog.toString());
    }

    private void logResponse(Response response) {

        StringBuilder responseLog = new StringBuilder();

        responseLog.append(System.lineSeparator());
        responseLog.append("==================================================").append(System.lineSeparator());
        responseLog.append("HTTP RESPONSE").append(System.lineSeparator());
        responseLog.append("Correlation ID : ")
                .append(CorrelationIdManager.get())
                .append(System.lineSeparator());
        responseLog.append("==================================================").append(System.lineSeparator());

        responseLog.append("Status Code  : ")
                .append(response.getStatusCode())
                .append(System.lineSeparator());

        responseLog.append("Response Time: ")
                .append(response.time())
                .append(" ms")
                .append(System.lineSeparator());

        responseLog.append(System.lineSeparator())
                .append("Headers")
                .append(System.lineSeparator())
                .append("--------------------------------------------------")
                .append(System.lineSeparator());

        Headers maskedHeaders = SensitiveDataMasker.maskHeaders(response.getHeaders());
        responseLog.append(maskedHeaders)
                .append(System.lineSeparator());

        responseLog.append(System.lineSeparator())
                .append("Body")
                .append(System.lineSeparator())
                .append("--------------------------------------------------")
                .append(System.lineSeparator());

        String maskedBody = SensitiveDataMasker.maskJsonBody(response.getBody().asPrettyString());
                responseLog.append(maskedBody)
                .append(System.lineSeparator());

        logger.info(responseLog.toString());
        AllureAttachmentManager.attachResponse(responseLog.toString());
    }
}
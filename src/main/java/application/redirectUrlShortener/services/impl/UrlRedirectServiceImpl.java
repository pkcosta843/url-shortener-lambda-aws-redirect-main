package application.redirectUrlShortener.services.impl;

import application.redirectUrlShortener.models.UrlData;
import application.redirectUrlShortener.services.S3StorageService;
import application.redirectUrlShortener.services.UrlRedirectService;
import application.redirectUrlShortener.validators.RequestValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class UrlRedirectServiceImpl implements UrlRedirectService {

    private final S3StorageService s3StorageService;
    private final ObjectMapper objectMapper;

    public UrlRedirectServiceImpl(S3StorageService s3StorageService, ObjectMapper objectMapper) {
        this.s3StorageService = s3StorageService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> handleRedirectRequest(Map<String, Object> input) {
        String rawPath = input.get("rawPath").toString();
        String shortUrlCode = RequestValidator.validateAndExtractShortUrlCode(rawPath);

        UrlData urlData = s3StorageService.getUrlData(shortUrlCode, objectMapper);

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;

        if (urlData.expirationTime() < currentTimeInSeconds) {
            return createErrorResponse();
        }

        return createRedirectResponse(urlData.originalUrl());
    }

    private Map<String, Object> createErrorResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 410);
        response.put("body", "URL has expired");

        return response;
    }

    private Map<String, Object> createRedirectResponse(String location) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", location);

        response.put("headers", headers);
        response.put("statusCode", 302);

        return response;
    }
}

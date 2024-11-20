package application.redirectUrlShortener.services;

import application.redirectUrlShortener.models.UrlData;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface S3StorageService {
    UrlData getUrlData(String shortUrlCode, ObjectMapper objectMapper);
}

package application.redirectUrlShortener.services.impl;

import application.redirectUrlShortener.models.UrlData;
import application.redirectUrlShortener.services.S3StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;

@Slf4j
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client = S3Client.builder().build();

    @Override
    public UrlData getUrlData(String shortUrlCode, ObjectMapper objectMapper) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("url-shortener-lambda-dev")
                .key(shortUrlCode + ".json")
                .build();

        try (InputStream s3ObjectStream = s3Client.getObject(request)) {
            return objectMapper.readValue(s3ObjectStream, UrlData.class);
        } catch (Exception e) {
            log.error("Error fetching or deserializing URL data: {}", e.getMessage());
            throw new RuntimeException("Error fetching or deserializing URL data: " + e.getMessage(), e);
        }
    }
}

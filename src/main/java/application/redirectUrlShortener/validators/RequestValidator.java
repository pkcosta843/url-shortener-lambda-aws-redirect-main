package application.redirectUrlShortener.validators;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestValidator {

    public static String validateAndExtractShortUrlCode(String rawPath) {
        String shortUrlCode = rawPath.replace("/", "");
        if (shortUrlCode.isEmpty()) {
            log.error("Invalid input: 'shortUrlCode' is required.");
            throw new IllegalArgumentException("Invalid input: 'shortUrlCode' is required.");
        }
        return shortUrlCode;
    }

}

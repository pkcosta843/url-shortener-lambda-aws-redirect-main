package application.redirectUrlShortener;

import application.redirectUrlShortener.services.UrlRedirectService;
import application.redirectUrlShortener.services.impl.S3StorageServiceImpl;
import application.redirectUrlShortener.services.impl.UrlRedirectServiceImpl;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Main implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final UrlRedirectService urlRedirectService = new UrlRedirectServiceImpl(
            new S3StorageServiceImpl(),
            new ObjectMapper()
    );

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        return urlRedirectService.handleRedirectRequest(input);
    }
}
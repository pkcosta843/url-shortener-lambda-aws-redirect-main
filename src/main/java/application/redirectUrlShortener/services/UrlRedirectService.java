package application.redirectUrlShortener.services;

import java.util.Map;


public interface UrlRedirectService {
    Map<String, Object> handleRedirectRequest(Map<String, Object> input);
}

package application.redirectUrlShortener.models;

public record UrlData(String originalUrl, long expirationTime) {
}

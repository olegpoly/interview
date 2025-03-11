package code.interview.dto;

public record SignInResponse(
    String accessToken, String idToken, String refreshToken, Integer expiresIn) {}

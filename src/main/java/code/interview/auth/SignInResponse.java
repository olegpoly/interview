package code.interview.auth;

public record SignInResponse(
    String accessToken, String idToken, String refreshToken, Integer expiresIn) {}

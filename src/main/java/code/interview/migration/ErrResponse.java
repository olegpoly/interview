package code.interview.migration;

import java.util.UUID;

public record ErrResponse(UUID userId, String email, String error) {}

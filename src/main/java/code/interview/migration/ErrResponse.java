package code.interview.migration;

import java.util.UUID;

public record ErrResponse(UUID id, String email, String error) {}

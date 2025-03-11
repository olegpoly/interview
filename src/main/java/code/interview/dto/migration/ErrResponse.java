package code.interview.dto.migration;

import java.util.UUID;

public record ErrResponse(UUID id, String email, String error) {}

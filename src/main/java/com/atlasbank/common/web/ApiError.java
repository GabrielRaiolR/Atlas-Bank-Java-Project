package com.atlasbank.common.web;

import java.time.OffsetDateTime;
import java.util.Map;

public record ApiError(
        OffsetDateTime timestamp,
        String code,
        String message,
        Map<String, String> fieldErrors
) {
}

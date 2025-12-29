package com.yusufziyrek.clean_architecture_training.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String message, String code, LocalDateTime timestamp) {
}

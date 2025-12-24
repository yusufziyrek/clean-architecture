package com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto;

import java.util.UUID;

public record OrderResponseDto(
 UUID orderId,
 Long productId,
 Integer quantity,
 Double totalPrice,
 String status // Domain modelinde olmayan, sadece sunum için eklenen alanlar olabilir
) {}

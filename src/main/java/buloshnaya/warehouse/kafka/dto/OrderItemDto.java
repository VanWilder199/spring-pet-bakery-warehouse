package buloshnaya.warehouse.kafka.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {}
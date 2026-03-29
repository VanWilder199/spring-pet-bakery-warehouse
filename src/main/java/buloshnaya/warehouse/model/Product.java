package buloshnaya.warehouse.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(
        @Null
        Long productId,
        @NotNull
        String productName,
        @NotNull
        String description,
        @NotNull
        BigDecimal price,
        @NotNull
        Integer quantity,
        @NotNull
        String category
) {
}

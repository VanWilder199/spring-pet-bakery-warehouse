package buloshnaya.warehouse.kafka.dto;

import java.util.UUID;

public record StockReservationResult (
        UUID orderId,
        boolean success,
        String reason
) {}

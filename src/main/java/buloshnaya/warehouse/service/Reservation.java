package buloshnaya.warehouse.service;

import buloshnaya.warehouse.kafka.dto.ReserveStockCommand;
import buloshnaya.warehouse.kafka.dto.StockReservationResult;

public interface Reservation {
    StockReservationResult reserve(ReserveStockCommand command);
}

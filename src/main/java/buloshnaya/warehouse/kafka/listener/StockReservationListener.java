package buloshnaya.warehouse.kafka.listener;

import buloshnaya.warehouse.kafka.dto.ReserveStockCommand;
import buloshnaya.warehouse.kafka.dto.StockReservationResult;
import buloshnaya.warehouse.service.impl.ReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockReservationListener {

    private final ReservationService reservationService;
    private final KafkaTemplate kafkaTemplate;

    public StockReservationListener(ReservationService reservationService, KafkaTemplate kafkaTemplate) {
        this.reservationService = reservationService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "${kafka.topics.reserve-stock}")
    public void handle(ReserveStockCommand command) {
        StockReservationResult result = reservationService.reserve(command);
        kafkaTemplate.send("${kafka.topics.stock-reservation-result}", result);
    }
}

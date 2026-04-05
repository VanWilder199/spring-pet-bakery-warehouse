package buloshnaya.warehouse.service.impl;

import buloshnaya.warehouse.entity.ReservationEntity;
import buloshnaya.warehouse.kafka.dto.OrderItemDto;
import buloshnaya.warehouse.kafka.dto.ReserveStockCommand;
import buloshnaya.warehouse.kafka.dto.StockReservationResult;
import buloshnaya.warehouse.repository.ReservationRepositoy;
import buloshnaya.warehouse.repository.WarehouseRepository;
import buloshnaya.warehouse.service.Reservation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService implements Reservation {

    private final ReservationRepositoy reservationRepositoy;
    private final WarehouseRepository warehouseRepository;

    public ReservationService(ReservationRepositoy reservationRepositoy, WarehouseRepository warehouseRepository) {
        this.reservationRepositoy = reservationRepositoy;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public StockReservationResult reserve(ReserveStockCommand command) {
        List<OrderItemDto> items = command.items();

         for (OrderItemDto item : items) {
            var stock = warehouseRepository.findByProductIdAndStoreId(item.productId(), command.storeId());

            if (stock.isEmpty() || stock.get().getQuantity() < item.quantity()) {
                return new StockReservationResult(command.orderId(), false, "OUT_OF_STOCK");
            }
        }


        List<ReservationEntity> reservations = items.stream()
                .map(item -> {
                    ReservationEntity reservation = new ReservationEntity();
                    reservation.setOrderId(command.orderId());
                    reservation.setProductId(item.productId());
                    reservation.setQuantity(item.quantity());
                    reservation.setReservedUntil(LocalDateTime.now().plusMinutes(60));
                    return reservation;
                })
                .toList();

         try {
             reservationRepositoy.saveAll(reservations);
         } catch (DataIntegrityViolationException e) {
             return new StockReservationResult(command.orderId(), false, null);
         }

        return new StockReservationResult(command.orderId(), true, null);
    }
}

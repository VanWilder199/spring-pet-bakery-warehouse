package buloshnaya.warehouse.scheduler;

import buloshnaya.warehouse.repository.ReservationRepositoy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationScheduler {

    private final ReservationRepositoy reservationRepositoy;

    public ReservationScheduler(ReservationRepositoy reservationRepositoy) {
        this.reservationRepositoy = reservationRepositoy;
    }

    @Scheduled(fixedRate = 60_000)
    public void cleanExpired() {
        reservationRepositoy.deleteAllByReservedUntilBefore(LocalDateTime.now());
    }
}
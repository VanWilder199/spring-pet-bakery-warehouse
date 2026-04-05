package buloshnaya.warehouse.repository;

import buloshnaya.warehouse.entity.ReservationEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepositoy extends JpaRepository<ReservationEntity, Long> {

    @Transactional
    void deleteAllByReservedUntilBefore(LocalDateTime now);

}

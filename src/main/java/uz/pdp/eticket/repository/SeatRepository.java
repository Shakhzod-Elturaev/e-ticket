package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> getSeatsByCarriageId(Long carriageId);
}

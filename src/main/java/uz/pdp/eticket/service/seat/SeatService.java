package uz.pdp.eticket.service.seat;

import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.SeatBookingDto;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;
import uz.pdp.eticket.service.BaseService;

import java.util.List;

@Service
public interface SeatService extends BaseService<SeatEntity, SeatEntity> {
    void setSeatsToCarriage(Integer seats, CarriageEntity carriageEntity);

    List<SeatEntity> getSeatsByCarriageId(Long carriage_id);

    SeatEntity bookTheSeat(SeatBookingDto dto);
}

package uz.pdp.eticket.service.seat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.SeatBookingDto;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;
import uz.pdp.eticket.domain.exceptions.DataNotFoundException;
import uz.pdp.eticket.repository.SeatRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService{

    private final SeatRepository seatRepository;

    @Override
    public SeatEntity create(SeatEntity seatEntity) {
        return seatRepository.save(seatEntity);
    }

    @Override
    public SeatEntity getById(Long id) {
        return seatRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("seat is not found"));
    }

    @Override
    public void removeById(Long id) {
        seatRepository.deleteById(id);
    }

    @Override
    public List<SeatEntity> getAll() {
        return seatRepository.findAll();
    }

    @Override
    public void setSeatsToCarriage(Integer seats, CarriageEntity carriageEntity) {
        List<SeatEntity> seatEntityList = getSeatsByCarriageId(carriageEntity.getId());
        if(seatEntityList.isEmpty()){
            for (Integer i = 1; i <= seats; i++) {
                create(new SeatEntity(i, false, null, null,
                        null, null, carriageEntity));
            }
        }

    }

    @Override
    public List<SeatEntity> getSeatsByCarriageId(Long carriage_id) {
        return seatRepository.getSeatsByCarriageId(carriage_id);
    }

    @Override
    public SeatEntity bookTheSeat(SeatBookingDto dto) {
        List<SeatEntity> seats = seatRepository.getSeatsByCarriageId(dto.getCarriageId());
        for (SeatEntity s : seats) {
            if(Objects.equals(s.getSeatNumber(), dto.getSeatNumber())){
                s.setIsBusy(true);
                s.setUserFirstName(dto.getFirstName());
                s.setUserMiddleName(dto.getMiddleName());
                s.setUserLastName(dto.getLastName());
                s.setDocumentNumber(dto.getDocumentNumber());
                seatRepository.save(s);
                return s;
            }
        }
        return null;
    }
}

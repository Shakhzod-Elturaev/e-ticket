package uz.pdp.eticket.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageType;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;

import java.time.LocalDate;

@Data
@Builder
public final class TicketBookingResponse {
    private String departure;
    private String arrival;
    private LocalDate departureDate;
    private String trainName;
    private CarriageType carriageType;
    private String carriageName;
    private SeatEntity seat;
}

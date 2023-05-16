package uz.pdp.eticket.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;

import java.util.List;

@Data
@Builder
public final class TicketsRetrievalResponse {
    private TicketEntity ticket;
    private String trainName;
    private List<CarriageEntity> carriages;
}

package uz.pdp.eticket.domain.dto.request;

import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;

import java.util.List;

@Data
@Builder
public final class TrainTicketRequest {
    private List<TrainEntity> trains;
    private List<String> stations;
}

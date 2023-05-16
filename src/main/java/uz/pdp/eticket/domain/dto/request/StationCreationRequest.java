package uz.pdp.eticket.domain.dto.request;

import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.station.StationEntity;

@Data
@Builder
public final class StationCreationRequest {
    private String name;
    private String afterThisStation;
    private String beforeThisStation;
}

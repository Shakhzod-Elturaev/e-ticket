package uz.pdp.eticket.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CarriageCreationRequest {
    private String name;
    private Byte type;
    private Boolean isActive;
    private Integer seats;
}

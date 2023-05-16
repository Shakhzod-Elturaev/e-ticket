package uz.pdp.eticket.domain.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TrainCreationRequest {
    private String name;
    private Boolean isActive;
}

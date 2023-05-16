package uz.pdp.eticket.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageType;

import java.util.Set;

@Data
@Builder
public final class CarriageDto {
    private String name;
    private CarriageType type;
    private Boolean isActive;
    private Set<SeatDto> seats;
}

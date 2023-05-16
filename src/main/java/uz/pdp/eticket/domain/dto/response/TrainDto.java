package uz.pdp.eticket.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;

import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public final class TrainDto {
    private String name;
    private Set<CarriageDto> carriages;
}

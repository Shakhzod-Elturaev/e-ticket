package uz.pdp.eticket.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class SeatDto {
    private Integer seatNumber;
    private Boolean isBusy;
}

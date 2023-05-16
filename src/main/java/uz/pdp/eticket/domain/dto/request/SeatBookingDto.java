package uz.pdp.eticket.domain.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;

@AllArgsConstructor
@Data
public class SeatBookingDto {
    @Positive
    private Integer seatNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String documentNumber;
    private Long carriageId;
}

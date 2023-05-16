package uz.pdp.eticket.domain.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public final class TicketBookingRequest {
    private String departure;
    private String arrival;

    @DateTimeFormat(style = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    private Integer seatNumber;
    private Long carriageId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String documentNumber;
}

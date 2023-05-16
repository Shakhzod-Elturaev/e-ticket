package uz.pdp.eticket.domain.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
@Builder
public final class TicketCreationRequest {
    private String departure_place;
    private String arrival_place;

    @Positive
    private Map<Integer, BigDecimal> prices;

    @DateTimeFormat(style = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate departureDate;

    @DateTimeFormat(style = "hh:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime departureTime;

    private String trainName;
}

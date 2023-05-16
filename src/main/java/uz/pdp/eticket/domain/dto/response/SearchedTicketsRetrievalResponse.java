package uz.pdp.eticket.domain.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Builder
public final class SearchedTicketsRetrievalResponse {
    @NonNull private Long id;
    private String departure;
    private String arrival;
    private LocalDate departureDate;
    private LocalTime departureTime;

    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private TrainDto train;
}

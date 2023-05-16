package uz.pdp.eticket.domain.entity.station;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import uz.pdp.eticket.domain.entity.BaseEntity;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "stations")
public class StationEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    private Integer stationOrder;

    @Column(nullable = false)
    @JsonIgnore
    private LocalTime timeDifference;

    @JsonIgnore
    @OneToOne(mappedBy = "departure")
    private TicketEntity departureTicket;

    @JsonIgnore
    @OneToOne(mappedBy = "arrival")
    private TicketEntity arrivalTicket;
}

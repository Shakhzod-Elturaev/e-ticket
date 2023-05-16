package uz.pdp.eticket.domain.entity.ticket;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.domain.entity.BaseEntity;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tickets")
public class TicketEntity extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "departure_id", referencedColumnName = "id")
    private StationEntity departure;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "arrival_id", referencedColumnName = "id")
    private StationEntity arrival;

    @Column(nullable = false)
    private LocalDate departureDate;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Positive
    private BigDecimal sedentary_price;

    @Positive
    private BigDecimal composite_price;

    @Positive
    private BigDecimal compartment_price;

    @Positive
    private BigDecimal lux_price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private TrainEntity train;

    @Column(nullable = false)
    private LocalDate arrivalDate;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "tickets")
    private Set<UserEntity> customers = new HashSet<>();
}

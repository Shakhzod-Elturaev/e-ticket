package uz.pdp.eticket.domain.entity.train.carriage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.domain.entity.BaseEntity;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "carriages")
public class CarriageEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @Enumerated(value = EnumType.STRING)
    private CarriageType type;

    @Column(nullable = false)
    private Boolean isActive;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "carriage", orphanRemoval = true)
    @OrderBy(value = "seatNumber ASC")
    private Set<SeatEntity> seats = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    private TrainEntity train;
}

package uz.pdp.eticket.domain.entity.train;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.eticket.domain.entity.BaseEntity;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "trains")
public class TrainEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private Set<CarriageEntity> carriages = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private Set<TicketEntity> tickets = new HashSet<>();
}

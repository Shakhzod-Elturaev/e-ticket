package uz.pdp.eticket.domain.entity.train.seat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.eticket.domain.entity.BaseEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "seats")
public class SeatEntity extends BaseEntity {
    private Integer seatNumber;
    private Boolean isBusy;


    private String userFirstName;

    private String userLastName;

    private String userMiddleName;

    @JsonIgnore
    private String documentNumber;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "carriage_id", referencedColumnName = "id")
    private CarriageEntity carriage;
}

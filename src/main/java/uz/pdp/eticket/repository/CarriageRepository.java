package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;

import java.util.List;

public interface CarriageRepository extends JpaRepository<CarriageEntity, Long> {
    List<CarriageEntity> getCarriageEntitiesByTrain(TrainEntity train);
}

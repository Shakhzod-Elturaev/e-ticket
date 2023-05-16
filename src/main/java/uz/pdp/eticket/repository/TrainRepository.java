package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.domain.entity.train.TrainEntity;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
}

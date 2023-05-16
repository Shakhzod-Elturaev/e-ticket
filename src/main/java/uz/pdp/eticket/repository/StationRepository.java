package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.domain.entity.station.StationEntity;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    StationEntity findStationEntityByName(String name);
    StationEntity findStationEntityByStationOrder(Integer order);
}

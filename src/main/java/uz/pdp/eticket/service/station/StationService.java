package uz.pdp.eticket.service.station;

import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.StationCreationRequest;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.service.BaseService;

import java.time.LocalTime;
import java.util.List;

@Service
public interface StationService extends BaseService<StationEntity, StationCreationRequest> {
    boolean checkStationUnique(String name);

    LocalTime getDurationBetweenTwoStation(Integer begin, Integer end);

    StationEntity setTimeDifference(
            StationEntity currentStation,
            StationEntity beforeThis,
            StationEntity afterThis);

    void updateStationOrder(Integer newOrder);

    StationEntity getStationByItsOrder(Integer order);

    StationEntity getStationByName(String name);

    List<String> getAllStationsName();
}

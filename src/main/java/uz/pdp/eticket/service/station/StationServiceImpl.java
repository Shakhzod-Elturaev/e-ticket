package uz.pdp.eticket.service.station;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.StationCreationRequest;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.exceptions.DataNotFoundException;
import uz.pdp.eticket.domain.exceptions.DuplicateDataException;
import uz.pdp.eticket.domain.exceptions.EntityDestitutionException;
import uz.pdp.eticket.domain.exceptions.MismatchedDataException;
import uz.pdp.eticket.repository.StationRepository;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService{

    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    @Override
    public StationEntity create(StationCreationRequest request) {
        StationEntity stationEntity = modelMapper.map(request, StationEntity.class);
        StationEntity beforeThis = getStationByName(request.getBeforeThisStation());
        StationEntity afterThis = getStationByName(request.getAfterThisStation());
        if(beforeThis == null || afterThis == null)
            throw new EntityDestitutionException("Not enough info to register the station");
        else if(checkStationUnique(stationEntity.getName()))
            throw new DuplicateDataException("This stationEntity is already existent");
        else if(beforeThis.getStationOrder() - 1 != afterThis.getStationOrder())
            throw new MismatchedDataException("Stations did not matched");
        stationEntity = setTimeDifference(stationEntity, beforeThis, afterThis);
        return stationRepository.save(stationEntity);
    }

    @Override
    public StationEntity getById(Long id) {
        return stationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("station is not found"));
    }

    @Override
    public void removeById(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public List<StationEntity> getAll() {
        return stationRepository.findAll();
    }

    @Override
    public boolean checkStationUnique(String stationName) {
        for (StationEntity s : getAll()) {
            if(Objects.equals(s.getName(), stationName))
                return true;
        }
        return false;
    }

    @Override
    public LocalTime getDurationBetweenTwoStation(Integer begin, Integer end) {
        LocalTime fullDuration = LocalTime.of(0, 0);
        if(begin < end) {
            for (StationEntity s : getAll()) {
                if (s.getStationOrder() > begin && s.getStationOrder() <= end) {
                    fullDuration = fullDuration.plus(s.getTimeDifference().getHour(), ChronoUnit.HOURS);
                    fullDuration = fullDuration.plus(s.getTimeDifference().getMinute(), ChronoUnit.MINUTES);
                }
            }
        } else if(begin > end){
            for (StationEntity s : getAll()) {
                if (s.getStationOrder() > end && s.getStationOrder() <= begin) {
                    fullDuration = fullDuration.plus(s.getTimeDifference().getHour(), ChronoUnit.HOURS);
                    fullDuration = fullDuration.plus(s.getTimeDifference().getMinute(), ChronoUnit.MINUTES);
                }
            }
        }
        return fullDuration;
    }

    @Override
    public StationEntity setTimeDifference(StationEntity currentStation,
                                           StationEntity beforeThis,
                                           StationEntity afterThis) {
        Integer newOrder = afterThis.getStationOrder()+1;
        currentStation.setStationOrder(newOrder);
        updateStationOrder(newOrder);
        LocalTime time = generateRandomTime(beforeThis.getTimeDifference());
        beforeThis.setTimeDifference(afterThis.getTimeDifference()
                .minus(time.getHour(), ChronoUnit.HOURS)
                .minus(time.getMinute(), ChronoUnit.MINUTES)
                .minus(time.getSecond(), ChronoUnit.SECONDS));
        stationRepository.save(beforeThis);
        currentStation.setTimeDifference(time);
        return currentStation;
    }


    private LocalTime generateRandomTime(LocalTime bound){
        LocalTime time1 = LocalTime.of(0, 40, 0);
        LocalTime time2 = bound.minus(1, ChronoUnit.HOURS);
        int secondOfDayTime1 = time1.toSecondOfDay();
        int secondOfDayTime2 = time2.toSecondOfDay();
        Random random = new Random();
        int randomSecondOfDay = secondOfDayTime1 + random.nextInt(secondOfDayTime2-secondOfDayTime1);
        return LocalTime.ofSecondOfDay(randomSecondOfDay);
    }


    @Override
    public void updateStationOrder(Integer newOrder) {
        for (StationEntity s : getAll()) {
            if(s.getStationOrder() >= newOrder){
                s.setStationOrder(s.getStationOrder()+1);
                stationRepository.save(s);
            }
        }
    }

    @Override
    public StationEntity getStationByItsOrder(Integer order) {
        return stationRepository.findStationEntityByStationOrder(order);
    }

    @Override
    public StationEntity getStationByName(String name) {
        return stationRepository.findStationEntityByName(name);
    }


    @Override
    public List<String> getAllStationsName() {
        List<String> stationNames = new ArrayList<>();
        for (StationEntity s : getAll()) {
            stationNames.add(s.getName());
        }
        return stationNames;
    }
}

package uz.pdp.eticket.service.carriage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageType;
import uz.pdp.eticket.domain.exceptions.DataNotFoundException;
import uz.pdp.eticket.domain.exceptions.IllegalAccessException;
import uz.pdp.eticket.repository.CarriageRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CarriageServiceImpl implements CarriageService{

    private final CarriageRepository carriageRepository;

    @Override
    public CarriageEntity create(CarriageEntity carriageEntity) {
        return carriageRepository.save(carriageEntity);
    }

    @Override
    public CarriageEntity getById(Long id) {
        return carriageRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Carriage not found"));
    }

    @Override
    public void removeById(Long id) {
        carriageRepository.deleteById(id);
    }

    @Override
    public List<CarriageEntity> getAll() {
        return carriageRepository.findAll();
    }

    @Override
    public CarriageType identifyCarriageType(Byte typeByte) {
        CarriageType type;
        switch (typeByte){
            case 1 -> type = CarriageType.SEDENTARY;
            case 2 -> type = CarriageType.COMPOSITE;
            case 3 -> type = CarriageType.COMPARTMENT;
            case 4 -> type = CarriageType.LUX;
            default -> throw new IllegalAccessException("This type of carriage is not acceptable!");
        }
        return type;
    }

    @Override
    public List<CarriageEntity> getCarriagesByTrain(TrainEntity train) {
        return carriageRepository.getCarriageEntitiesByTrain(train);
    }
}

package uz.pdp.eticket.service.carriage;

import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageType;
import uz.pdp.eticket.service.BaseService;

import java.util.List;

@Service
public interface CarriageService extends BaseService<CarriageEntity, CarriageEntity> {
    CarriageType identifyCarriageType(Byte type);

    List<CarriageEntity> getCarriagesByTrain(TrainEntity train);
}

package uz.pdp.eticket.service.train;

import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.TrainCreationRequest;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.service.BaseService;

import java.util.List;

@Service
public interface TrainService extends BaseService<TrainEntity, TrainCreationRequest> {
    boolean checkTrainExists(String trainName);

    TrainEntity getByName(String name);
}

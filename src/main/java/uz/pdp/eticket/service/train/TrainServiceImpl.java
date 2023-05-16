package uz.pdp.eticket.service.train;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.TrainCreationRequest;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.exceptions.DataNotFoundException;
import uz.pdp.eticket.domain.exceptions.DuplicateDataException;
import uz.pdp.eticket.repository.TrainRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final ModelMapper modelMapper;

    @Override
    public TrainEntity create(TrainCreationRequest request) {
        TrainEntity trainEntity = modelMapper.map(request, TrainEntity.class);
        if(checkTrainExists(trainEntity.getName()))
            throw new DuplicateDataException("This trainEntity is already existent");
        return trainRepository.save(trainEntity);
    }

    @Override
    public TrainEntity getById(Long id) {
        return trainRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Not found"));
    }

    @Override
    public void removeById(Long id) {
        trainRepository.deleteById(id);
    }

    @Override
    public List<TrainEntity> getAll() {
        return trainRepository.findAll();
    }

    @Override
    public boolean checkTrainExists(String trainName) {
        for (TrainEntity t : getAll()) {
            if(Objects.equals(t.getName(), trainName))
                return true;
        }
        return false;
    }

    @Override
    public TrainEntity getByName(String name) {
        for (TrainEntity t : getAll()) {
            if(Objects.equals(t.getName(), name))
                return t;
        }
        return null;
    }
}

package uz.pdp.eticket.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.LoginRequest;
import uz.pdp.eticket.domain.dto.request.UserRegisterRequest;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.user.UserEntity;
import uz.pdp.eticket.domain.exceptions.DataNotFoundException;
import uz.pdp.eticket.domain.exceptions.DuplicateDataException;
import uz.pdp.eticket.domain.exceptions.IllegalAccessException;
import uz.pdp.eticket.repository.UserRepository;
import uz.pdp.eticket.service.ticket.TicketService;

import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TicketService ticketService;
    private final ModelMapper modelMapper;

    @Override
    public UserEntity create(UserRegisterRequest request) {
        UserEntity userEntity = modelMapper.map(request, UserEntity.class);
        if(checkUserExist(userEntity.getPhoneNumber()))
            throw new DuplicateDataException("This phone number has already been registered");
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Not found"));
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity login(LoginRequest dto) {
        Optional<UserEntity> user = userRepository.findByPhoneNumber(dto.getPhoneNumber());
        if(user.isEmpty())
            throw new DataNotFoundException("Not found");
        else if(user.get().getPassword().equals(dto.getPassword()))
            return user.get();
        throw new IllegalAccessException("Invalid password");
    }

    @Override
    public boolean checkUserExist(String phoneNumber) {
        for (UserEntity u : getAll()) {
            if(Objects.equals(u.getPhoneNumber(), phoneNumber))
                return true;
        }
        return false;
    }

    @Override
    public void setTicketToUser(Long ticketId, Long userId) {
        UserEntity user = getById(userId);
        Set<TicketEntity> tickets = user.getTickets();
        tickets.add(ticketService.getById(ticketId));
        user.setTickets(tickets);
        userRepository.save(user);
    }
}

package uz.pdp.eticket.service.user;

import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.LoginRequest;
import uz.pdp.eticket.domain.dto.request.UserRegisterRequest;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.user.UserEntity;
import uz.pdp.eticket.service.BaseService;

@Service
public interface UserService extends BaseService<UserEntity, UserRegisterRequest> {
    UserEntity login(LoginRequest dto);
    boolean checkUserExist(String phoneNumber);
    void setTicketToUser(Long ticketId, Long userId);
}

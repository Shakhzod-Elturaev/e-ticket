package uz.pdp.eticket.domain.dto.request;

import lombok.Builder;
import lombok.Data;
import uz.pdp.eticket.domain.entity.user.UserRoles;

import java.util.List;

@Data
@Builder
public final class UserRegisterRequest {
    private String phoneNumber;
    private String password;
    private List<UserRoles> roles;
}

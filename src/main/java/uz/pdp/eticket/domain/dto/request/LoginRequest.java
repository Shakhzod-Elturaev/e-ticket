package uz.pdp.eticket.domain.dto.request;

import lombok.*;

@Data
@Builder
public final class LoginRequest {
    private final String phoneNumber;
    private final String password;
}

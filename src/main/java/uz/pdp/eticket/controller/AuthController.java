package uz.pdp.eticket.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.domain.dto.request.LoginRequest;
import uz.pdp.eticket.domain.dto.request.UserRegisterRequest;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.entity.user.UserEntity;
import uz.pdp.eticket.repository.StationRepository;
import uz.pdp.eticket.service.user.UserService;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserRegisterRequest registerRequest){
        return userService.create(registerRequest);
    }

    @PostMapping("/login")
    public UserEntity login(
            @RequestBody LoginRequest dto
            ){
        return userService.login(dto);
    }
}

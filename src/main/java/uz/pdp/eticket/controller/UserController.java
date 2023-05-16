package uz.pdp.eticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.domain.dto.request.SeatBookingDto;
import uz.pdp.eticket.domain.dto.request.TicketBookingRequest;
import uz.pdp.eticket.domain.dto.request.TicketSearchingRequest;
import uz.pdp.eticket.domain.dto.response.SearchedTicketsRetrievalResponse;
import uz.pdp.eticket.domain.dto.response.TicketBookingResponse;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;
import uz.pdp.eticket.service.carriage.CarriageService;
import uz.pdp.eticket.service.seat.SeatService;
import uz.pdp.eticket.service.station.StationService;
import uz.pdp.eticket.service.ticket.TicketService;
import uz.pdp.eticket.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final StationService stationService;
    private final TicketService ticketService;
    private final CarriageService carriageService;
    private final SeatService seatService;
    private final UserService userService;

    @GetMapping("/user-homepage")
    public List<String> getStations(){
        return stationService.getAllStationsName();
    }


    @PostMapping("/tickets/searching")
    public List<SearchedTicketsRetrievalResponse> getTickets(@RequestBody TicketSearchingRequest request){
        return ticketService.getSearchedTickets(request);
    }


    @PostMapping("/tickets/order/{id}/{ticketId}")
    public TicketBookingResponse orderTicket(@PathVariable Long id,
                                             @PathVariable Long ticketId,
                                             @RequestBody TicketBookingRequest request){
        userService.setTicketToUser(ticketId, id);
        CarriageEntity carriage = carriageService.getById(request.getCarriageId());
        SeatEntity seat = seatService.bookTheSeat(new SeatBookingDto(
                request.getSeatNumber(), request.getFirstName(), request.getMiddleName(),
                request.getLastName(), request.getDocumentNumber(), carriage.getId()));
        return TicketBookingResponse.builder()
                .departure(request.getDeparture())
                .arrival(request.getArrival())
                .carriageName(carriage.getName())
                .carriageType(carriage.getType())
                .departureDate(request.getDate())
                .seat(seat)
                .trainName(ticketService.getById(ticketId).getTrain().getName())
                .build();
    }

}

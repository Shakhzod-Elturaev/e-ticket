package uz.pdp.eticket.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import uz.pdp.eticket.domain.dto.request.*;
import uz.pdp.eticket.domain.dto.response.TicketsRetrievalResponse;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.service.carriage.CarriageService;
import uz.pdp.eticket.service.seat.SeatService;
import uz.pdp.eticket.service.station.StationService;
import uz.pdp.eticket.service.ticket.TicketService;
import uz.pdp.eticket.service.train.TrainService;


import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TrainService trainService;
    private final StationService stationService;
    private final ModelMapper modelMapper;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final CarriageService carriageService;



    /**
     * Ticket related requests and responses
     */
    @GetMapping("tickets/create-ticket")
    public TrainTicketRequest requestTicketCreation(){
        return TrainTicketRequest.builder()
                .trains(trainService.getAll())
                .stations(stationService.getAllStationsName()).build();
    }

    @PostMapping("tickets/create-ticket")
    public TicketEntity createTicket(
            @RequestBody TicketCreationRequest request
            ){
        return ticketService.create(request);
    }

    @GetMapping("/tickets/get-all")
    public List<TicketEntity> getAllTickets(){
        return ticketService.getAll();
    }

    @GetMapping("/tickets/get-ticket/{id}")
    public TicketsRetrievalResponse getTicketInfo(@PathVariable Long id){
        TicketEntity ticket = ticketService.getById(id);
        TrainEntity train = trainService.getById(ticket.getTrain().getId());
        return TicketsRetrievalResponse.builder()
                .ticket(ticket)
                .trainName(train.getName())
                .carriages(carriageService.getCarriagesByTrain(train))
                .build();
    }





    /**
     * Train related requests and responses
     */
    @GetMapping("trains/get-all")
    public List<TrainEntity> getTrains(){
        return trainService.getAll();
    }


    @PostMapping("trains/register-carriage/{train_id}")
    public TrainEntity createCarriageToTrain(
            @PathVariable Long train_id,
            @RequestBody CarriageCreationRequest request
            ){
        CarriageEntity carriageEntity = modelMapper.map(request, CarriageEntity.class);
        carriageEntity.setTrain(trainService.getById(train_id));
        carriageEntity.setType(carriageService.identifyCarriageType(request.getType()));
        carriageService.create(carriageEntity);
        seatService.setSeatsToCarriage(request.getSeats(), carriageEntity);
        return trainService.getById(train_id);
    }


    @PostMapping("trains/register-train")
    public TrainEntity registerNewTrain(
            @RequestBody TrainCreationRequest trainRequest
            ){
        return trainService.create(trainRequest);
    }



    /**
     * Station related requests and responses
     */
    @PostMapping("/stations/register-station")
    public StationEntity registerNewStation(
            @RequestBody StationCreationRequest request
            ){
        return stationService.create(request);
    }

    @GetMapping("/stations/get-all")
    public List<StationEntity> getAllStations(){
        return stationService.getAll();
    }
}

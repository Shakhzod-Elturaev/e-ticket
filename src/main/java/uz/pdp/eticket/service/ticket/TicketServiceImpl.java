package uz.pdp.eticket.service.ticket;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.TicketCreationRequest;
import uz.pdp.eticket.domain.dto.request.TicketSearchingRequest;
import uz.pdp.eticket.domain.dto.response.CarriageDto;
import uz.pdp.eticket.domain.dto.response.SearchedTicketsRetrievalResponse;
import uz.pdp.eticket.domain.dto.response.SeatDto;
import uz.pdp.eticket.domain.dto.response.TrainDto;
import uz.pdp.eticket.domain.entity.station.StationEntity;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageEntity;
import uz.pdp.eticket.domain.entity.train.carriage.CarriageType;
import uz.pdp.eticket.domain.entity.train.TrainEntity;
import uz.pdp.eticket.domain.entity.train.seat.SeatEntity;
import uz.pdp.eticket.domain.exceptions.DataNotFoundException;
import uz.pdp.eticket.domain.exceptions.DuplicateDataException;
import uz.pdp.eticket.domain.exceptions.EntityDestitutionException;
import uz.pdp.eticket.repository.TicketRepository;
import uz.pdp.eticket.service.station.StationService;
import uz.pdp.eticket.service.train.TrainService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final StationService stationService;
    private final ModelMapper modelMapper;
    private final TrainService trainService;

    @Override
    public TicketEntity create(TicketCreationRequest request) {
        TicketEntity ticket = modelMapper.map(request, TicketEntity.class);
        TrainEntity train = trainService.getByName(request.getTrainName());
        StationEntity arrival = stationService.getStationByName(request.getArrival_place());
        StationEntity departure = stationService.getStationByName(request.getDeparture_place());
        if(arrival == null || departure == null || train == null || request.getPrices().isEmpty()){
            throw new EntityDestitutionException("There is no enough info in request");
        }
        ticket.setArrival(arrival);
        ticket.setDeparture(departure);
        LocalDateTime dateTime = setArrivalTime(ticket);
        ticket.setArrivalDate(dateTime.toLocalDate());
        ticket.setArrivalTime(dateTime.toLocalTime());
        ticket.setTrain(train);
        if (!checkTicketUnique(
                ticket.getDepartureDate(),
                ticket.getArrivalDate(),
                ticket.getTrain().getName())) {
            throw new DuplicateDataException("Given date and time does not match the schedule of this train");
        }
        return ticketRepository.save(setPriceToTicket(request.getPrices(), ticket));
    }

    private LocalDateTime setArrivalTime(TicketEntity ticket){
        LocalTime duration = stationService.getDurationBetweenTwoStation(
                ticket.getDeparture().getStationOrder(),
                ticket.getArrival().getStationOrder()
        );
        return LocalDateTime.of(ticket.getDepartureDate(),ticket.getDepartureTime())
                .plus(duration.getHour(), ChronoUnit.HOURS)
                .plus(duration.getMinute(), ChronoUnit.MINUTES);
    }

    @Override
    public TicketEntity getById(Long id) {
        return ticketRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Not found"));
    }

    @Override
    public void removeById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketEntity> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public boolean checkTicketUnique(LocalDate dDate, LocalDate aDate, String trainName) {
        for (TicketEntity t : getAll()) {
            if(Objects.equals(t.getTrain().getName(), trainName)){
                if (!(aDate.isBefore(t.getDepartureDate()) || dDate.isAfter(t.getArrivalDate())))
                    return false;
            }
        }
        return true;
    }

    @Override
    public TicketEntity setPriceToTicket(Map<Integer, BigDecimal> prices, TicketEntity ticket) {
        prices.forEach(
                (key, value) -> {
                    if(Objects.equals(key, CarriageType.SEDENTARY.getValue()))
                        ticket.setSedentary_price(value);
                    else if(Objects.equals(key, CarriageType.COMPOSITE.getValue()))
                        ticket.setComposite_price(value);
                    else if(Objects.equals(key, CarriageType.COMPARTMENT.getValue()))
                        ticket.setCompartment_price(value);
                    else if(Objects.equals(key, CarriageType.LUX.getValue()))
                        ticket.setLux_price(value);
                });
        return ticket;
    }

    @Override
    public List<SearchedTicketsRetrievalResponse> getSearchedTickets(TicketSearchingRequest request) {
        Integer begin = stationService.getStationByName(request.getDeparture()).getStationOrder();
        Integer end = stationService.getStationByName(request.getArrival()).getStationOrder();
        List<SearchedTicketsRetrievalResponse> sTickets = new ArrayList<>();
        for (TicketEntity t : getAll()) {
            Integer dO = t.getDeparture().getStationOrder();
            Integer aO = t.getArrival().getStationOrder();
            LocalDate departureDate = t.getDepartureDate();
            LocalTime departureTime = t.getDepartureTime();
            if(begin < end && aO > dO && dO <= begin && aO >= end){
                SearchedTicketsRetrievalResponse st = setForFirstCondition(begin, end, t, departureDate, departureTime);
                if(st.getDepartureDate().isEqual(request.getDate()))
                    sTickets.add(st);
            } else if(begin > end && aO < dO && dO >= begin && aO <= end){
                SearchedTicketsRetrievalResponse st = setForFirstCondition(begin, end, t, departureDate, departureTime);
                if(st.getDepartureDate().isEqual(request.getDate()))
                    sTickets.add(st);
            }
        }
        return sTickets;
    }

    private SearchedTicketsRetrievalResponse setForFirstCondition(
            Integer begin, Integer end, TicketEntity t, LocalDate departureDate, LocalTime departureTime){
        LocalTime dB = stationService.getDurationBetweenTwoStation(t.getDeparture().getStationOrder(), begin);
        LocalDateTime dDT = LocalDateTime.of(departureDate, departureTime)
                .plus(dB.getHour(), ChronoUnit.HOURS).plus(dB.getMinute(), ChronoUnit.MINUTES)
                .plus(dB.getSecond(), ChronoUnit.SECONDS);
        LocalTime dA = stationService.getDurationBetweenTwoStation(begin, end);
        LocalDateTime dAT = LocalDateTime.of(dDT.toLocalDate(), dDT.toLocalTime())
                .plus(dA.getHour(), ChronoUnit.HOURS).plus(dA.getMinute(), ChronoUnit.MINUTES)
                .plus(dA.getSecond(), ChronoUnit.SECONDS);
        return SearchedTicketsRetrievalResponse.builder().id(t.getId())
                .departure(stationService.getStationByItsOrder(begin).getName())
                .arrival(stationService.getStationByItsOrder(end).getName())
                .departureDate(dDT.toLocalDate()).departureTime(dDT.toLocalTime())
                .arrivalDate(dAT.toLocalDate()).arrivalTime(dAT.toLocalTime())
                .train(TrainDto.builder()
                        .name(t.getTrain().getName())
                        .carriages(converter(t.getTrain()))
                        .build())
                .build();
    }

    private Set<CarriageDto> converter(TrainEntity train){
        Set<CarriageDto> carriages = new HashSet<>();
        for (CarriageEntity c : train.getCarriages()) {
            Set<SeatDto> seats = new HashSet<>();
            for (SeatEntity s : c.getSeats()) {
                seats.add(SeatDto.builder().seatNumber(s.getSeatNumber()).isBusy(s.getIsBusy()).build());
            }
            CarriageDto map = CarriageDto.builder().name(c.getName()).isActive(c.getIsActive())
                    .type(c.getType())
                    .build();
            if(!seats.isEmpty())
                map.setSeats(seats);
            carriages.add(map);
        }
        return carriages;
    }

}

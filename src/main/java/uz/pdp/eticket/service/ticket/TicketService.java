package uz.pdp.eticket.service.ticket;

import org.springframework.stereotype.Service;
import uz.pdp.eticket.domain.dto.request.TicketCreationRequest;
import uz.pdp.eticket.domain.dto.request.TicketSearchingRequest;
import uz.pdp.eticket.domain.dto.response.SearchedTicketsRetrievalResponse;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;
import uz.pdp.eticket.service.BaseService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface TicketService extends BaseService<TicketEntity, TicketCreationRequest> {
    boolean checkTicketUnique(LocalDate dDate, LocalDate aDate, String trainName);
    TicketEntity setPriceToTicket(Map<Integer, BigDecimal> prices, TicketEntity ticket);

    List<SearchedTicketsRetrievalResponse> getSearchedTickets(TicketSearchingRequest request);
}

package uz.pdp.eticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.eticket.domain.entity.ticket.TicketEntity;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
}

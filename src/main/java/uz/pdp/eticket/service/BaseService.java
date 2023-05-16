package uz.pdp.eticket.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<E, CD> {
    E create(CD e);
    E getById(Long id);

    void removeById(Long id);
    List<E> getAll();
}

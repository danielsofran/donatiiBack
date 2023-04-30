package com.donatii.donatiiapi.service;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.repository.CauzaRepository;
import com.donatii.donatiiapi.repository.UserRepository;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CauzaService {
    private final CauzaRepository cauzaRepository;

    @Autowired
    CauzaService(CauzaRepository cauzaRepository) {
        this.cauzaRepository = cauzaRepository;
    }

    public void save(Cauza cauza) {
        cauzaRepository.save(cauza);
    }

    public Cauza findById(Long id) throws NotFoundException {
        Optional<Cauza> cauza = cauzaRepository.findById(id);
        if (cauza.isEmpty()) {
            throw new NotFoundException("Case not found!");
        }
        return cauza.get();
    }

    public void delete(Long id) throws NotFoundException {
        Optional<Cauza> cauza = cauzaRepository.findById(id);
        if(cauza.isEmpty())
            throw new NotFoundException("Case not found");
        cauzaRepository.deleteById(id);
    }
}
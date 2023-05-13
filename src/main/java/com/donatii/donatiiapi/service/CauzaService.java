package com.donatii.donatiiapi.service;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.Poza;
import com.donatii.donatiiapi.repository.CauzaRepository;
import com.donatii.donatiiapi.repository.PozaRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CauzaService {
    private final CauzaRepository cauzaRepository;
    private final PozaRepository pozaRepository;

    @Autowired
    CauzaService(CauzaRepository cauzaRepository, PozaRepository pozaRepository) {
        this.cauzaRepository = cauzaRepository;
        this.pozaRepository = pozaRepository;
    }

    public Cauza save(Cauza cauza) {
        cauza.setSustinatori(new HashSet<>());
        return cauzaRepository.save(cauza);
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

    public List<Cauza> findAll() {
        return cauzaRepository.findAll();
    }

    public void savePicture(String s, Long id) throws NotFoundException {
        Optional<Cauza> cauza = cauzaRepository.findById(id);
        if (cauza.isEmpty()) {
            throw new NotFoundException("Case not found!");
        }
        Poza poza = new Poza();
        poza.setUrl(s);
        cauza.get().getPoze().add(poza);
        cauzaRepository.save(cauza.get());
        pozaRepository.save(poza);
    }

    public void update(Long id, Cauza cauza) throws NotFoundException {
        if (!cauzaRepository.existsById(id)) {
            throw new NotFoundException("Case not found!");
        }
        cauza.setId(id);
        cauzaRepository.save(cauza);
    }
}
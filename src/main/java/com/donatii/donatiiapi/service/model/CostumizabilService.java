package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.repository.ICostumizabilRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.ICostumizabilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostumizabilService implements ICostumizabilService {

    private final ICostumizabilRepository costumizabilRepository;

    @Autowired
    CostumizabilService(ICostumizabilRepository costumizabilRepository) {
        this.costumizabilRepository = costumizabilRepository;
    }

    public Costumizabil getCostumizabil(Long costumizabil_id) throws NotFoundException {
        Optional<Costumizabil> costumizabilOptional = costumizabilRepository.findById(costumizabil_id);
        if(costumizabilOptional.isEmpty())
            throw new NotFoundException("Costumizabil inexistent!");
        return costumizabilOptional.get();
    }

    public Iterable<Costumizabil> getCostumizabile()
    {
        return costumizabilRepository.findAll();
    }

}

package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;

public interface ICostumizabilService {
    /**
     * Obtine un costumizabil dupa id
     * @param costumizabil_id Identificator costumizabil
     * @return Costumizabil
     */
    Costumizabil getCostumizabil(Long costumizabil_id) throws NotFoundException;

    /**
     * Obtine toate costumizabilele
     * @return Lista de costumizabile
     */
    Iterable<Costumizabil> getCostumizabile();
}

package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;

import java.util.List;

public interface ICauzaService {
    /**
     * Salveaza cauza
     * @param cauza Cauza
     * @return Cauza
     */
    Cauza save(Cauza cauza);

    /**
     * Gaseste o cauza dupa id
     * @param id Identificator Cauza
     * @return Cauza
     */
    Cauza findById(Long id) throws NotFoundException;

    /**
     * Sterge cauza
     * @param id Identificator Cauza
     */
    void delete(Long id) throws NotFoundException;

    /**
     * Gaseste toate cauzele
     * @return Lista de cauze
     */
    List<Cauza> findAll();

    /**
     * Salveaza o poza
     * @param s
     * @param id
     */
    void savePicture(String s, Long id) throws NotFoundException;

    /**
     * Modifica cauza
     * @param id Identificator Cauza
     * @param cauza Cauza
     */
    void update(Long id, Cauza cauza) throws NotFoundException;

    /**
     *
     * @param locatie
     * @param sumMin
     * @param sumMax
     * @param taguri
     * @param rezolvate
     * @param adaposturi
     * @return Lista de cauze
     */
    List<Cauza> filter(String locatie, Integer sumMin, Integer sumMax, List<TagAnimal> taguri, Boolean rezolvate, Boolean adaposturi);

    /**
     * Like
     * @param cauzaId Identificator Cauza
     * @param userId Identificator User
     */
    void like(Long cauzaId, Long userId) throws NotFoundException;
}

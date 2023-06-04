package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.*;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import com.donatii.donatiiapi.repository.IPozaRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.ICauzaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CauzaService implements ICauzaService {
    private final ICauzaRepository cauzaRepository;
    private final IPozaRepository pozaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CauzaService(ICauzaRepository cauzaRepository, IPozaRepository pozaRepository) {
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
    }

    public void update(Long id, Cauza cauza) throws NotFoundException {
        if (!cauzaRepository.existsById(id)) {
            throw new NotFoundException("Case not found!");
        }
        cauza.setId(id);
        cauzaRepository.save(cauza);
    }

    public List<Cauza> filter(String locatie, Integer sumMin, Integer sumMax, List<TagAnimal> taguri, Boolean rezolvate, Boolean adaposturi) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<? extends Cauza> query = criteriaBuilder.createQuery(Cauza.class);
        final Root<? extends Cauza> cauza = query.from(Cauza.class);
        final List<Predicate> predicates = new ArrayList<>();
        if (locatie != null && !locatie.isEmpty()) {
            predicates.add(criteriaBuilder.like(cauza.get("locatie"), "%" + locatie + "%"));
        }
        if (sumMin != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(cauza.get("sumaMinima"), sumMin));
        }
        if (sumMax != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(cauza.get("sumaMinima"), sumMax));
        }
        if (rezolvate != null && rezolvate) {
            Expression<Integer> sumaStransa = cauza.get("sumaStransa");
            Expression<Integer> sumaMinima = cauza.get("sumaMinima");
            predicates.add(criteriaBuilder.greaterThan(sumaMinima, sumaStransa));
        }
        query.where(predicates.toArray(new Predicate[0]));
        TypedQuery<? extends Cauza> typedQuery = entityManager.createQuery(query);
        List<? extends Cauza> resultList = typedQuery.getResultList();

        if (taguri != null && !taguri.isEmpty()) {
            resultList.removeIf(c -> {
                if(c instanceof CauzaAdapost) {
                    for (TagAnimal tag : taguri) {
                        if (((CauzaAdapost) c).getTaguri().contains(tag)){
                            return false;
                        }
                    }
                    return true;
                }
                else {
                    for (TagAnimal tag : taguri) {
                        if (((CauzaPersonala) c).getTagAnimal().getId().equals(tag.getId())){
                            return false;
                        }
                    }
                }
                return true;
            });
        }

        return (List<Cauza>) resultList;
    }

    public void like(Long cauzaId, Long userId) throws NotFoundException {
        Optional<Cauza> cauzaOptional = cauzaRepository.findById(cauzaId);
        if(cauzaOptional.isEmpty())
            throw new NotFoundException("User not found");
        Cauza cauza = cauzaOptional.get();
        if(cauza.getSustinatori().contains(userId)) {//unlike
            cauza.getSustinatori().removeIf(id -> id.equals(userId));
            cauzaRepository.save(cauza);
            System.out.println("unlike");
        }
        else {//apreciere
            cauza.getSustinatori().add(userId);
            cauzaRepository.save(cauza);
            System.out.println("like");
        }
    }

    @Override
    public void donate(Long cauzaId, Integer sum) throws NotFoundException {
        Optional<Cauza> cauzaOptional = cauzaRepository.findById(cauzaId);
        if(cauzaOptional.isEmpty())
            throw new NotFoundException("Cauza not found");
        Cauza cauza = cauzaOptional.get();
        cauza.setSumaStransa(cauza.getSumaStransa() + sum);
        cauzaRepository.save(cauza);
    }


}
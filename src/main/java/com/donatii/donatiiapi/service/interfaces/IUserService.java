package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.data.util.Pair;

import java.util.Set;

public interface IUserService {
    /**
     * Logheaza un user
     * @param email Email User
     * @param parola Parola User
     * @return User
     */
    User login(String email, String parola) throws NotFoundException;

    /**
     * Inregistreaza un user
     *
     * @param user User
     * @return
     */
    User register(User user) throws MyException;

    /**
     * Modifica un user
     * @param id Identificator User
     * @param user User
     */
    void update(Long id, User user) throws NotFoundException;

    /**
     * Sterge un user
     * @param id Identificator User
     */
    void delete(Long id) throws NotFoundException;

    /**
     * Gaseste un user dupa id
     * @param id Identificator User
     * @return User
     */
    User findById(Long id) throws NotFoundException;

    /**
     * Like
     * @param userId Identificator User
     * @param cauzaId Identificator Cauza
     */
    void like(Long userId, Long cauzaId) throws NotFoundException;

    /**
     * Salveaza un user
     *
     * @param user User
     * @return
     */
    User save(User user);

    /**
     * Cumpara un customizabil
     * @param userId Identificator User
     * @param costumizabil Costumizabil
     */
    void buy(Long userId, Costumizabil costumizabil) throws NotFoundException;

    /**
     * Echipeaza un customizabil
     * @param userId Identificator Costumizabil
     * @param costumizabil Costumizabil
     * @return Set Costumizabil
     */
    Set<Costumizabil> equip(Long userId, Costumizabil costumizabil) throws NotFoundException;

    /**
     * Dezechipeaza un customizabil
     * @param userId Identificator Costumizabil
     * @param costumizabil Costumizabil
     * @return Set Costumizabil
     */
    Set<Costumizabil> unequip(Long userId, Costumizabil costumizabil) throws NotFoundException;

    /**
     * Gaseste un user dupa o cauza
     * @param cauza Cauza
     */
    void deleteCauzaFromUser(Cauza cauza) throws NotFoundException;

    /**
     * Doneaza
     *
     * @param userId   Identificator User
     * @param sum      Suma
     * @param currency Moneda
     * @param cauza    Cauza
     * @return
     */
    Pair<Long, Integer> donate(Long userId, Integer sum, String currency, Cauza cauza) throws NotFoundException;

    /**
     * Updateaza resursele unui user
     * @param userId Identificator User
     * @param coins Monede
     * @param level Nivel
     */
    void updateResources(Long userId, Long coins, Integer level) throws NotFoundException;
}

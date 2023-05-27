package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.model.Tip;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import com.donatii.donatiiapi.utils.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Autowired
    UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String parola) throws NotFoundException {
        Optional<User> user = userRepository.findUserByEmailAndParola(email, parola);
        if(user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    public void register(User user) throws MyException {
        if(userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new MyException("Email already exists");
        userRepository.save(user);
    }

    public void update(Long id, User user) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setParola(user.getParola());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFullName(user.getFullName());
        userToUpdate.setGender(user.getGender());
        userToUpdate.setInterese(user.getInterese());
        userRepository.save(userToUpdate);
    }

    public void delete(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        userRepository.deleteById(id);
    }

    public User findById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        return userOptional.get();
    }

    public void like(Long userId, Long cauzaId) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        if(user.getSustineri().contains(cauzaId)) {//unlike
            user.getSustineri().removeIf(id -> id.equals(cauzaId));
            save(user);
            System.out.println("unlike");
        }
        else {//apreciere
            user.getSustineri().add(cauzaId);
            userRepository.save(user);
            System.out.println("like");
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void buy(Long userId, Costumizabil costumizabil) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        user.getCostumizabile().add(costumizabil);
        user.setCoins(user.getCoins() - costumizabil.getCostBani());
        save(user);
    }

    public Set<Costumizabil> equip(Long userId, Costumizabil costumizabil) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        for(Costumizabil costumizabil1 : user.getEchipate()) {
            if(costumizabil1.getTip().equals(costumizabil.getTip())) {
                user.getEchipate().remove(costumizabil1);
                break;
            }
        }
        user.getEchipate().add(costumizabil);
        save(user);
        return user.getEchipate();
    }

    public Set<Costumizabil> unequip(Long userId, Costumizabil costumizabil) throws NotFoundException {
        Ensure.NotNull(userId);
        Ensure.NotNull(costumizabil);

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        if(costumizabil.getTip().equals(Tip.Animal))
            throw new NotFoundException("You can't disequip the animal, please equip another animal");
        User user = userOptional.get();
        user.getEchipate().removeIf(costumizabil1 -> costumizabil1.getId().equals(costumizabil.getId()));
        save(user);
        return user.getEchipate();
    }

    public void deleteCauzaFromUser(Cauza cauza) throws NotFoundException {
        Ensure.NotNull(cauza);

        Optional<User> user = userRepository.findUserByCauzaId(cauza.getId());
        if(user.isEmpty())
        {
            throw new NotFoundException("User inexistent!");
        }
        user.get().getCauze().remove(cauza);
        userRepository.save(user.get());
    }
}

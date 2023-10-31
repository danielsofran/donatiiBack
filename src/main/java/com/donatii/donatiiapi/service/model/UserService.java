package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.*;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import com.donatii.donatiiapi.utils.Ensure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String email, String parola) throws NotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty() || !passwordEncoder.matches(parola, user.get().getParola())) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    public User register(User user) throws MyException {
        if(userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new MyException("Email already exists");
        return save(user);
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
        save(userToUpdate);
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
            save(user);
            System.out.println("like");
        }
    }

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getParola());
        user.setParola(encodedPassword);
        return userRepository.save(user);
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
        save(user.get());
    }

    @Override
    public Pair<Long, Integer> donate(Long userId, Integer sum, String currency, Cauza cauza) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        Donatie donatie = new Donatie(0L, sum, LocalDateTime.now(), currency, cauza.getId(), cauza.getTitlu());
        user.getDonatii().add(donatie);
        Long coins = 3L * sum;
        Integer level = sum / 5;
        user.setCoins(user.getCoins() + coins);
        user.setLevel(user.getLevel() + level);
        save(user);
        return Pair.of(coins, level);
    }

    @Override
    public void updateResources(Long userId, Long coins, Integer level) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NotFoundException("User not found");
        User user = userOptional.get();
        user.setCoins(user.getCoins() + coins);
        if(level != null)
            user.setLevel(user.getLevel() + level);
        save(user);
    }
}

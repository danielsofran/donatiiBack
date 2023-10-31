package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.CauzaAdapost;
import com.donatii.donatiiapi.model.Donatie;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import com.donatii.donatiiapi.repository.IPozaRepository;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.exceptions.MyException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.model.CauzaService;
import com.donatii.donatiiapi.service.model.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@DataJpaTest
public class UserServiceTests {
    @Autowired
    private IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICauzaRepository cauzaRepository;

    @Autowired
    private IPozaRepository pozaRepository;

    private CauzaService cauzaService;
    private UserService userService;

    @BeforeEach
    public void setUp()
    {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, passwordEncoder);
        cauzaService = new CauzaService(cauzaRepository, pozaRepository);
    }


    @Test
    public void loginThrowError() throws NotFoundException {
        String email = "test@yahoo.com";
        String parola = "test";
        Assert.assertThrows(NotFoundException.class,() -> userService.login(email, parola));

    }

    @Test
    public void loginSuccess() throws NotFoundException {
        String email = "test@yahoo.com";
        String parola = "test";

        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola(passwordEncoder.encode("test"));
        user.setUsername("test");
        userRepository.save(user);

        Assertions.assertThat(userService.login(email, parola)).isEqualTo(user);

    }
    @Test
    public void registerThrowError() throws MyException{
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola(passwordEncoder.encode("test"));
        user.setUsername("test");

        userRepository.save(user);

        Assert.assertThrows(MyException.class,() -> userService.register(user));
    }

    @Test
    public void registerSuccess() throws MyException{
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola(passwordEncoder.encode("test"));
        user.setUsername("test");

        Assertions.assertThat(userService.register(user)).isEqualTo(user);
    }

    @Test
    public void updateUserTestSuccess() throws NotFoundException {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        userService.save(user);

        user.setEmail("test1@yahoo.com");
        userService.update(user.getId(), user);

        Assertions.assertThat(userRepository.findById(user.getId()).get().getEmail()).isEqualTo("test1@yahoo.com");
    }

    @Test
    public void updateUserTestThrowError() throws NotFoundException {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        Assert.assertThrows(NotFoundException.class,() -> userService.update(user.getId(), user));
    }

    @Test
    public void deleteUserTestThrowError() throws NotFoundException {
        Assert.assertThrows(NotFoundException.class,() -> userService.delete(1L));
    }

    @Test
    public void deleteUserTestSuccess() throws NotFoundException {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        userService.save(user);
        userService.delete(user.getId());
        Assert.assertThrows(NotFoundException.class,() -> userService.findById(user.getId()));
    }

    @Test
    public void findByIdUserTestSuccess() throws NotFoundException {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        userService.save(user);
        Assertions.assertThat(userService.findById(user.getId())).isEqualTo(user);
    }

    @Test
    public void findByIdUserTestThrowError() throws NotFoundException {
        Assert.assertThrows(NotFoundException.class,() -> userService.findById(1L));
    }

    @Test
    public void likeTestThrowError() throws NotFoundException {
        Assert.assertThrows(NotFoundException.class,() -> userService.like(1L, 1L));
    }

    @Test
    public void likeTestSuccessContainsCauza() throws NotFoundException {

        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();

        cauzaService.save(cauza);
        Set<Long> cauze = Set.of(cauza.getId());

        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");
        user.setSustineri(cauze);
        userService.save(user);

        Assertions.assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);
    }

    @Test
    public void likeTestSuccessNotContainsCauza() throws NotFoundException {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        cauzaService.save(cauza);
        Set<Long> sustineri = new HashSet<>();
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");
        user.setSustineri(sustineri);

        userService.save(user);

        userService.like(user.getId(), cauza.getId());
        Assertions.assertThat(userRepository.findById(user.getId()).get().getSustineri().contains(cauza.getId())).isTrue();
    }

    @Test
    public void donateThrowError() throws NotFoundException {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        cauzaService.save(cauza);
        Assert.assertThrows(NotFoundException.class,() -> userService.donate(1L, 0, "test", cauza));
    }

    @Test
    public void donateSuccess() throws NotFoundException {
        String currency = "test";

        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        cauzaService.save(cauza);

        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        userService.save(user);
        Assertions.assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);
    }
}

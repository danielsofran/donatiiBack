package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.CauzaAdapost;
import com.donatii.donatiiapi.model.CauzaPersonala;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import com.donatii.donatiiapi.repository.IPozaRepository;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.ICauzaService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
public class CauzaServiceTests {
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
    public void findByIdCauzaTest() throws NotFoundException {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest1 = cauzaService.save(cauza);
        Cauza cauzaTest2 = cauzaService.findById(cauza.getId());
        Assertions.assertThat(cauzaTest1).isEqualTo(cauzaTest2);
    }

    @Test
    public void findAllCauzaTest()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest = cauzaService.save(cauza);
        List<Cauza> listaCauze = cauzaService.findAll();
        Assertions.assertThat(listaCauze.contains(cauzaTest)).isTrue();
    }

    @Test
    public void saveCauzaAdapostTest()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest = cauzaService.save(cauza);
        Assertions.assertThat(cauzaTest).isNotNull();
    }

    @Test
    public void saveCauzaPersonalaTest()
    {
        Cauza cauza = CauzaPersonala.builder()
                .numeAnimal("test")
                .rasaAnimal("test")
                .varstaAnimal(1)
                .build();
        Cauza cauzaTest = cauzaService.save(cauza);
        Assertions.assertThat(cauzaTest).isNotNull();
    }

    @Test
    public void deleteCauzaPersonalaTest() throws NotFoundException {
        Cauza cauza = CauzaPersonala.builder()
                .numeAnimal("test")
                .rasaAnimal("test")
                .varstaAnimal(1)
                .build();
        Cauza cauzaTest = cauzaService.save(cauza);
        cauzaService.delete(cauzaTest.getId());
        Assert.assertThrows(NotFoundException.class,() -> cauzaService.findById(cauza.getId()));
    }

    @Test
    public void deleteCauzaAdapostTest() throws NotFoundException {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest = cauzaService.save(cauza);
        cauzaService.delete(cauzaTest.getId());
        Assert.assertThrows(NotFoundException.class,() -> cauzaService.findById(cauza.getId()));
    }

    @Test
    public void updateCauzaPersonalaTest() throws NotFoundException {
        Cauza cauza = CauzaPersonala.builder()
                .numeAnimal("test")
                .rasaAnimal("test")
                .varstaAnimal(1)
                .build();
        cauzaService.save(cauza);
        cauza.setDescriere("test");
        cauzaService.save(cauza);
        Assertions.assertThat(cauzaService.findById(cauza.getId()).getDescriere()).isEqualTo("test");
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

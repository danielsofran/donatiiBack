package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.CauzaAdapost;
import com.donatii.donatiiapi.model.CauzaPersonala;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import com.donatii.donatiiapi.repository.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICauzaRepository cauzaRepository;

    @Test
    public void saveUserTest()
    {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        User userTest1 = userRepository.save(user);
        Assertions.assertThat(userTest1).isNotNull();
    }

    @Test
    public void updateUserTest()
    {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        User userTest1 = userRepository.save(user);

        userTest1.setEmail("test1@yahoo.com");
        userRepository.save(user);
        Assertions.assertThat(userRepository.findById(userTest1.getId()).get().getEmail()).isEqualTo("test1@yahoo.com");
        Assertions.assertThat(userTest1).isNotNull();
    }

    @Test
    public void findUserByEmailAndParolaTest()
    {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        User userTest1 = userRepository.save(user);
        User userTest2 = userRepository.findUserByEmailAndParola("test@yahoo.com", "test").get();
        Assertions.assertThat(userTest1).isEqualTo(userTest2);
    }

    @Test
    public void findUserByEmailTest()
    {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        User userTest1 = userRepository.save(user);
        User userTest2 = userRepository.findUserByEmail("test@yahoo.com").get();
        Assertions.assertThat(userTest1).isEqualTo(userTest2);
    }

    @Test
    public void findByIdUserTest()
    {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");

        User userTest1 = userRepository.save(user);
        User userTest2 = userRepository.findById(userTest1.getId()).get();
        Assertions.assertThat(userTest1).isEqualTo(userTest2);
    }
    @Test
    public void findUserByCauzaId()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();

        cauzaRepository.save(cauza);
        Set<Cauza> cauze = Set.of(cauza);

        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setParola("test");
        user.setUsername("test");
        user.setCauze(cauze);

        User userTest1 = userRepository.save(user);
        User userTest2 = userRepository.findUserByCauzaId(cauza.getId()).get();
        Assertions.assertThat(userTest1).isEqualTo(userTest2);
    }

}

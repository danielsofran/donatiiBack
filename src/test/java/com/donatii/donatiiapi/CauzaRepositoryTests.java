package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.CauzaAdapost;
import com.donatii.donatiiapi.model.CauzaPersonala;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
public class CauzaRepositoryTests {
    @Autowired
    private ICauzaRepository cauzaRepository;

    @Test
    public void findByIdCauzaTest()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest1 = cauzaRepository.save(cauza);
        Cauza cauzaTest2 = cauzaRepository.findById(cauza.getId()).get();
        Assertions.assertThat(cauzaTest1).isEqualTo(cauzaTest2);
    }

    @Test
    public void findAllCauzaTest()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest = cauzaRepository.save(cauza);
        List<Cauza> listaCauze = cauzaRepository.findAll();
        Assertions.assertThat(listaCauze.contains(cauzaTest)).isTrue();
    }

    @Test
    public void saveCauzaAdapostTest()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        Cauza cauzaTest = cauzaRepository.save(cauza);
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
        Cauza cauzaTest = cauzaRepository.save(cauza);
        Assertions.assertThat(cauzaTest).isNotNull();
    }

    @Test
    public void deleteCauzaPersonalaTest()
    {
        Cauza cauza = CauzaPersonala.builder()
                .numeAnimal("test")
                .rasaAnimal("test")
                .varstaAnimal(1)
                .build();
        cauzaRepository.save(cauza);
        cauzaRepository.delete(cauza);
        Assertions.assertThat(cauzaRepository.count()).isEqualTo(0);
    }

    @Test
    public void deleteCauzaAdapostTest()
    {
        Cauza cauza = CauzaAdapost.builder()
                .nume("test")
                .build();
        cauzaRepository.save(cauza);
        cauzaRepository.delete(cauza);
        Assertions.assertThat(cauzaRepository.count()).isEqualTo(0);
    }
    @Test
    public void updateCauzaPersonalaTest()
    {
        Cauza cauza = CauzaPersonala.builder()
                .numeAnimal("test")
                .rasaAnimal("test")
                .varstaAnimal(1)
                .build();
        cauzaRepository.save(cauza);
        cauza.setDescriere("test");
        cauzaRepository.save(cauza);
        Assertions.assertThat(cauzaRepository.findById(cauza.getId()).get().getDescriere()).isEqualTo("test");
    }

}

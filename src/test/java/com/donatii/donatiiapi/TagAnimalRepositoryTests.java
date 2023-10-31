package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import com.donatii.donatiiapi.repository.ITagAnimalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TagAnimalRepositoryTests {
    @Autowired
    private ITagAnimalRepository tagAnimalRepository;

    @Test
    public void findAllTagsTests()
    {
        Assertions.assertThat(tagAnimalRepository.findAll().size()).isEqualTo(11);
    }
}

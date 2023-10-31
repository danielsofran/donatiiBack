package com.donatii.donatiiapi;

import com.donatii.donatiiapi.repository.ITagAnimalRepository;
import com.donatii.donatiiapi.service.model.CauzaService;
import com.donatii.donatiiapi.service.model.TagAnimalService;
import com.donatii.donatiiapi.service.model.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataJpaTest
public class TagAnimalServiceTests {
    @Autowired
    private ITagAnimalRepository tagAnimalRepository;

    private TagAnimalService tagAnimalService;

    @BeforeEach
    public void setUp()
    {
        tagAnimalService = new TagAnimalService(tagAnimalRepository);
    }

    @Test
    public void getTagsTests()
    {
        Assertions.assertThat(tagAnimalService.getTags()).isNotNull();
    }
}

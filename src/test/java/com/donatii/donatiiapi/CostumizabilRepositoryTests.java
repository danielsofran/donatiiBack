package com.donatii.donatiiapi;

import com.donatii.donatiiapi.repository.ICostumizabilRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

@DataJpaTest
public class CostumizabilRepositoryTests {
    @Autowired
    private ICostumizabilRepository costumizabilRepository;

    @Test
    public void findByIdCostumizabilTest()
    {
        Assertions.assertThat(costumizabilRepository.findById(1234L)).isEmpty();
    }

    @Test
    public void findAllCostumizabileTest()
    {
        Assertions.assertThat(costumizabilRepository.findAll().size()).isNotNegative();
    }
}

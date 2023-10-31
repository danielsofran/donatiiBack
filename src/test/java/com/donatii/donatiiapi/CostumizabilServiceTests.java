package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.repository.ICostumizabilRepository;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.model.CauzaService;
import com.donatii.donatiiapi.service.model.CostumizabilService;
import com.donatii.donatiiapi.service.model.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataJpaTest
public class CostumizabilServiceTests {
    @Autowired
    private ICostumizabilRepository costumizabilRepository;
    private CostumizabilService costumizabilService;
    @BeforeEach
    public void setUp()
    {
        costumizabilService = new CostumizabilService(costumizabilRepository);
    }

    @Test
    public void getCostumizabilTestThrowError() throws NotFoundException {
        Assert.assertThrows(NotFoundException.class,() -> costumizabilService.getCostumizabil(1234L));
    }

    @Test
    public void getCostumizabilTestSuccess() throws NotFoundException {
        Costumizabil costumizabil = new Costumizabil();
        costumizabil.setUrl("test");
        costumizabil.setCostBani(10);
        costumizabilRepository.save(costumizabil);

        Assertions.assertThat(costumizabilService.getCostumizabil(costumizabil.getId())).isNotNull();
    }

    @Test
    public void getCostumizabileTest()
    {
        Assertions.assertThat(costumizabilService.getCostumizabile()).isNotNull();
    }
}

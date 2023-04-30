package com.donatii.donatiiapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("PERSONALA")
public class CauzaPersonala extends Cauza{
    @ManyToOne(fetch = FetchType.EAGER)
    private TagAnimal tagAnimal;
    private String numeAnimal;
    private Integer varstaAnimal;
    private String rasaAnimal;
    @Override
    public CauzaType type() {
        return CauzaType.PERSONALA;
    }
}

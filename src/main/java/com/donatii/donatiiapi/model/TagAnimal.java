package com.donatii.donatiiapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tags")
public class TagAnimal {
    @Id
    @SequenceGenerator(
            name = "tagAnimal_sequence",
            sequenceName = "tagAnimal_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tagAnimal_sequence"
    )
    private Long id;
    private String nume;


}

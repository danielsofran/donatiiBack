package com.donatii.donatiiapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Immutable;

@Data
@Entity
@Table(name = "costumizabile")
public class Costumizabil {
    @Id
    @SequenceGenerator(
            name = "costumizabil_sequence",
            sequenceName = "costumizabil_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "costumizabil_sequence"
    )
    private Long id;
    private Tip tip;
    private String url;
    private Integer costBani;
    private Integer levelMin;

    /*
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_costumizabil",
            joinColumns = @JoinColumn(name = "costumizabil_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> useri;
    */
}
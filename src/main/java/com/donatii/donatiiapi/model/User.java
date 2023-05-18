package com.donatii.donatiiapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;
    private String username;
    private String email;
    private String parola;
    private String fullName;
    private GenderType gender;
    private Long coins;
    private Integer level;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_tag_animal",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_animal_id")
    )
    private Set<TagAnimal> interese;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_costumizabil",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "costumizabil_id")
    )
    private Set<Costumizabil> costumizabile;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "costumizabile_echipate",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "costumizabil_id")
    )
    private Set<Costumizabil> echipate;

    /*
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sustineri",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cauza_id")
    )
    private Set<Cauza> sustineri;
     */
    @ElementCollection
    @CollectionTable(name="sustineri", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="cauza_id")
    private Set<Long> sustineri;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Donatie> donatii;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Cauza> cauze;
}

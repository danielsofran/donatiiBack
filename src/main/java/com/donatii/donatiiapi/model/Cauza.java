package com.donatii.donatiiapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorFormula;

import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Data
@Entity
@Table(name = "cauze")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("CASE WHEN varsta_animal IS NOT NULL THEN 'PERSONALA' ELSE 'ADAPOST' END")
public abstract class Cauza {
    @Id
    @SequenceGenerator(
            name = "cauza_sequence",
            sequenceName = "cauza_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cauza_sequence"
    )
    protected Long id;
    protected String descriere;
    protected String titlu;
    protected String locatie;
    protected Integer sumaMinima;
    protected Integer sumaStransa;
    protected String moneda;

    public abstract CauzaType type();

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name="sustineri", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="cauza_id")
    private Set<Long> sustinatori;

    @Transient
    private Integer nrSustinatori;

    public Integer getNrSustinatori() {
        return this.sustinatori.size();
    }

    /*
    @JsonIgnore
    @OneToMany(mappedBy = "cauza", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Donatie> donatii;
    */

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(name = "cauze_poze",
            joinColumns = @JoinColumn(name = "cauza_id"),
            inverseJoinColumns = @JoinColumn(name = "poze_id"))
    private Set<Poza> poze;
}

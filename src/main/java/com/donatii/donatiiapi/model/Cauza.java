package com.donatii.donatiiapi.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorFormula;

import java.util.HashSet;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CauzaAdapost.class, name = "adapost"),
        @JsonSubTypes.Type(value = CauzaPersonala.class, name = "personala")
})
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @CollectionTable(name="sustinatori", joinColumns=@JoinColumn(name="cauza_id"))
    @Column(name="user_id")
    private Set<Long> sustinatori = new HashSet<>();

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

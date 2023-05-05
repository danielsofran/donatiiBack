package com.donatii.donatiiapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "poze")
public class Poza {
    @Id
    @SequenceGenerator(
            name = "poza_sequence",
            sequenceName = "poza_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "poza_sequence"
    )
    private Long id;

    /*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cauza_id", nullable = false)
    private Cauza cauza;
     */

    private String url;
}

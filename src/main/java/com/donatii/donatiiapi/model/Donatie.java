package com.donatii.donatiiapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donatii")
public class Donatie {
    @Id
    @SequenceGenerator(
            name = "donatie_sequence",
            sequenceName = "donatie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "donatie_sequence"
    )
    private Long id;
    private Integer suma;
    private LocalDateTime data;
    private String moneda;

    private Long cauzaId;
    private String cauzaTitlu;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    */
}


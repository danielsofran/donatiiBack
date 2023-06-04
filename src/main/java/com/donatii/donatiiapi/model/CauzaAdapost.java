package com.donatii.donatiiapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ADAPOST")
public class CauzaAdapost extends Cauza{
    private String nume;
    @Override
    public CauzaType type() {
        return CauzaType.ADAPOST;
    }

    @ManyToMany
    @JoinTable(
            name = "cauza_adapost_tag_animal",
            joinColumns = @JoinColumn(name = "cauza_adapost_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_animal_id")
    )
    private Set<TagAnimal> taguri;
}

package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.Costumizabil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICostumizabilRepository extends JpaRepository<Costumizabil, Long> {
}

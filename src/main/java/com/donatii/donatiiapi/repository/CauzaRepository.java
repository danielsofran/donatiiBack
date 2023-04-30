package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.Cauza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CauzaRepository extends JpaRepository<Cauza, Long> {
}

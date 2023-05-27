package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.Cauza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICauzaRepository extends JpaRepository<Cauza, Long> {
}

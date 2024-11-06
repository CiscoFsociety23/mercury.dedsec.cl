package com.dedsec.mercury.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.mercury.models.Layouts;

@Repository
public interface LayoutsRepository extends JpaRepository<Layouts, Integer> {

    Optional<Layouts> findByLayoutName(String LayoutName);

}

package com.dedsec.mercury.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.mercury.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    
    Optional<Client> findByEmail(String email);

}
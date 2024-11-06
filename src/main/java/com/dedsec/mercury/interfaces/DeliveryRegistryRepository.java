package com.dedsec.mercury.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dedsec.mercury.models.DeliveryRegistry;

@Repository
public interface DeliveryRegistryRepository extends JpaRepository<DeliveryRegistry, Integer> {

}

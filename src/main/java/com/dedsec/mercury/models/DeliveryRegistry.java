package com.dedsec.mercury.models;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeliveryRegistry;

    @ManyToOne
    @JoinColumn(name = "idLayout", nullable = false)
    private Layouts layoutType;

    @Basic
    private String subject;
    private String reciever;
    private Date sendTime;

}

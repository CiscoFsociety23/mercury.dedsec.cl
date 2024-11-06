package com.dedsec.mercury.models;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Layouts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLayout;

    @Basic
    private String layoutName;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String layout;

}

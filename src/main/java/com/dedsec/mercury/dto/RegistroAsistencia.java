package com.dedsec.mercury.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroAsistencia {

    private String reciever;
    private String subject;
    private String tipoRegistro;
    private String nombre;
    private String rut;
    private String fechaHora;

}

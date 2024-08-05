package com.dedsec.mercury.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEmail {

    private String reciever;
    private String subject;
    private String message;

}

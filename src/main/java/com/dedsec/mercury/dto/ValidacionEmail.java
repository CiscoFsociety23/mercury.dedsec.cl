package com.dedsec.mercury.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidacionEmail {

    private String reciever;
    private String subject;
    private String validation_url;

}

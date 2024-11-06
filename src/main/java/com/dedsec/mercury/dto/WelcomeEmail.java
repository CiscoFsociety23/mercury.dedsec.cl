package com.dedsec.mercury.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WelcomeEmail {

    private String reciever;
    private String subject;
    private String userName;

}

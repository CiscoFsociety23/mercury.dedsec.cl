package com.dedsec.mercury.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRecord {

    private Integer id;
    private String reciever;
    private String subject;
    private String layoutName;
    private Date sendDate;

}

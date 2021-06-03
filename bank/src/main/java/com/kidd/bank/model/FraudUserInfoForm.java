package com.kidd.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudUserInfoForm {
    /**
     * amazon账号
     */
    private String kiddfiled1;

    /**
     * amazom密码
     */
    private String kiddfiled2;

}

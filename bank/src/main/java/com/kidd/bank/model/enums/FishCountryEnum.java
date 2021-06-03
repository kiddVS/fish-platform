package com.kidd.bank.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FishCountryEnum {
    JP("jp","https://www.amazon.co.jp/?Your_Account_Verified","Japan","version2",".co.jp"),
    AU("au","https://www.amazon.com.au/?Your_Account_Verified","Australia","version3",".com.au"),
    UK("gb","https://www.amazon.co.uk/?Your_Account_Verified","United Kingdom","version3",".co.uk"),
    US("us","https://www.huntington.com/?Your_Account_Verified","United States","version3",".com"),
    UNKONW("unkonw","https://www.amazon.com/?Your_Account_Verified","American","version3",".com");

    private  String countryCode;

    private String amazonUrl;

    private String countryName;

    private String version;

    private String suffix;

    public static FishCountryEnum getByCountryCode (String code){
        for (FishCountryEnum countryEnum :  FishCountryEnum.values()){
            if (countryEnum.getCountryCode().equalsIgnoreCase(code)){
                return countryEnum;
            }
        }
        return UNKONW;
    }
}

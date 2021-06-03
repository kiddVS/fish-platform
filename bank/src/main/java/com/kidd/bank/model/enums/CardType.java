package com.kidd.bank.model.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Getter
public enum CardType {
    UNKNOWN(null,"unknow","all.jpg"),
    VISA("^4[0-9]{12}(?:[0-9]{3}){0,2}$","visa","visa.png"),
    MASTERCARD("^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$","mastercard","mastercard.jpg"),
    AMERICAN_EXPRESS("^3[47][0-9]{13}$","ae","ae.png"),
    DINERS_CLUB("^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$","diners","all.jpg"),
    DISCOVER("^6(?:011|[45][0-9]{2})[0-9]{12}$","discover","all.jpg"),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$","jcb","jcb.jpg"),
    CHINA_UNION_PAY("^62[0-9]{14,17}$","china","all.jpg");
    private Pattern pattern;
    private String description;
    private String imageName;

    CardType() {
        this.pattern = null;
    }

    CardType(String pattern,String description,String imageName) {
        if(!StringUtils.isEmpty(pattern)){
            this.pattern = Pattern.compile(pattern);
        }
        else {
            this.pattern = null;
        }
        this.description = description;
        this.imageName = imageName;
    }

    public static CardType detect(String cardNumber) {

        for (CardType cardType : CardType.values()) {
            if (null == cardType.pattern) continue;
            if (cardType.pattern.matcher(cardNumber).matches()) return cardType;
        }

        return UNKNOWN;
    }

    public static CardType getBydes(String description) {

        for (CardType cardType : CardType.values()) {
            if (null == cardType.pattern) continue;
            if (cardType.getDescription().equalsIgnoreCase(description)) return cardType;
        }

        return UNKNOWN;
    }

}

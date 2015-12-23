package br.com.jonyfs.credit.card.api.model;

import java.util.regex.Pattern;

public enum CardType {

    UNKNOWN, VISA("^4[0-9]{12}(?:[0-9]{3})?$"), MASTERCARD("^5[1-5][0-9]{14}$"), AMERICAN_EXPRESS("^3[47][0-9]{13}$"), DINERS_CLUB("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"), DISCOVER(
                    "^6(?:011|5[0-9]{2})[0-9]{12}$"), JCB("^(?:2131|1800|35\\d{3})\\d{11}$");

    private Pattern pattern;

    CardType() {
        this.pattern = null;
    }

    CardType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public static CardType detect(String cardNumber) {

        for (CardType cardType : CardType.values()) {
            if (null == cardType.pattern)
                continue;
            if (cardType.pattern.matcher(cardNumber).matches())
                return cardType;
        }

        return UNKNOWN;
    }
}

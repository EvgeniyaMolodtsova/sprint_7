package org.example;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public String getRandom() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public Courier getDefault() {
        String login = getRandom();
        String password = getRandom();
        String firstName = getRandom();
        return new Courier(login, password, firstName);
    }

    public Courier getDefaultWithoutLogin() {
        String password = getRandom();
        String firstName = getRandom();
        return new Courier(null, password, firstName);
    }

    public Courier getDefaultWithoutPassword() {
        String login = getRandom();
        String firstName = getRandom();
        return new Courier(login, null, firstName);
    }
}

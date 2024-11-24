package ru.netology.web.data;


import lombok.Value;

import java.util.Random;

public class DataHelper {


    public static LoginInfo getLoginInfo() {
        return new LoginInfo("vasya", "qwerty123");
    }

    public static String getVerificationCode() {
        return "12345";
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static String getMaskedNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(15);
    }

    public static int generateValidAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }

    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000) + 1;
    }


    //Data классы:
    @Value
    public static class LoginInfo {
        String login;
        String password;
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String cardTestId;
    }

}











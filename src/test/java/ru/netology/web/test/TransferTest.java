package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.generateValidAmount;
import static ru.netology.web.data.DataHelper.getMaskedNumber;

public class TransferTest {
    DashboardPage dashboardPage;
    DataHelper.CardInfo firstCardinfo;
    DataHelper.CardInfo secondCardinfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var loginInfo = DataHelper.getLoginInfo();
        var verificationPage = loginPage.validLogin(loginInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardinfo = DataHelper.getFirstCard();
        secondCardinfo = DataHelper.getSecondCard();
        firstCardBalance = dashboardPage.getCardBalance(DataHelper.getMaskedNumber(firstCardinfo.getCardNumber()));
        secondCardBalance = dashboardPage.getCardBalance(DataHelper.getMaskedNumber(secondCardinfo.getCardNumber()));
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var amount = generateValidAmount(firstCardBalance);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCard(secondCardinfo);
        dashboardPage = transferPage.doValidTransfer(String.valueOf(amount), firstCardinfo);
        dashboardPage.reloadPage();
        var actualFirstCardBalance = dashboardPage.getCardBalance(getMaskedNumber(firstCardinfo.getCardNumber()));
        var actualSecondCardBalance = dashboardPage.getCardBalance(getMaskedNumber(secondCardinfo.getCardNumber()));
        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void shouldGetErrorIfBalanceLessAmount() {
        var amount = generateValidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCard(firstCardinfo);
        transferPage.doValidTransfer(String.valueOf(amount), secondCardinfo);
        transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
    }
}

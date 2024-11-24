package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement code = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify'].button");

    public VerificationPage() {
        code.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        code.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

}

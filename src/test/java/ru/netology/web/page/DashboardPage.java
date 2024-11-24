package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final String balanceBeginning = "баланс: ";
    private final String balanceEnding = " р.";
    private final SelenideElement title = $("[data-test-id='dashboard']");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        title.shouldBe(Condition.visible);
    }

    public int getCardBalance(String maskedNumberCard) {
        var text = cards.findBy(Condition.text(maskedNumberCard)).getText();
        return extractBalance(text);
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getCardTestId())).$("button").click();
        return new TransferPage();
    }

    public void reloadPage() {
        reloadButton.click();
        title.shouldBe(Condition.visible);
    }

    private int extractBalance(String text) {
        var beginning = text.indexOf(balanceBeginning);
        var ending = text.indexOf(balanceEnding);
        var value = text.substring(beginning + balanceBeginning.length(), ending);
        return Integer.parseInt(value);
    }
}

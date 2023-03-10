package page;

import com.codeborne.selenide.SelenideElement;
import lombok.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;


@NoArgsConstructor
@Data

public class PaymentPage {
    private static SelenideElement buttonContinue = $$(".button").find(exactText("Продолжить"));
    private SelenideElement successSendFormMessage = $$(".notification").first();
    private SelenideElement errorSendFormMessage = $$(".notification").last();
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $(byXpath("//span[text()='Владелец']/parent::span//input[@class='input__control']"));
    private SelenideElement codeField = $("[placeholder='999']");

    private SelenideElement invalidFormatError = $(withText("Неверный формат"));
    private SelenideElement errorInCardNumberField = $(byXpath("//input[@placeholder='0000 0000 0000 0000']/parent::span/parent::span//span[text()='Неверный формат']"));
    private SelenideElement errorInMonthField = $(byXpath("//input[@placeholder='08']/parent::span/parent::span//span[text()='Неверно указан срок действия карты']"));
    private SelenideElement errorInYearField = $(byXpath("//input[@placeholder='22']/parent::span/parent::span//span[text()='Неверно указан срок действия карты']"));
    private SelenideElement errorMessageYearField = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement errorInOwnerField = $(byXpath("//span[text()='Владелец']/parent::span//span[text()='Поле обязательно для заполнения']"));

    private SelenideElement errorInCodeField = $(byXpath("//input[@placeholder='999']/parent::span/parent::span//span[text()='Неверный формат']"));

    public void successMessageForm() {
        successSendFormMessage.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно " + "Операция одобрена Банком."));
    }

    public void errorMessageForm() {
        errorSendFormMessage.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void errorMessageAboutOutOfDateMonthOrNonexistentMonth() {
        errorInMonthField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверно указан срок действия карты"));
    }

    public void errorMessageAboutOutOfDateYear() {
        errorMessageYearField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Истёк срок действия карты"));
    }

    public void errorMessageInvalidFormat() {
        invalidFormatError.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверный формат"));
    }

    public void fillThePaymentForm(String cardNumber, String month, String year, String owner, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        buttonContinue.click();
    }

    public static void sendEmptyPaymentForm() {
        buttonContinue.click();
    }
}



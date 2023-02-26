package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import data.DataHelper;
import data.SQLHelper;
import page.PaymentPage;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.*;
import static page.PaymentPage.sendEmptyPaymentForm;

public class PaymentDebitCardTest {
    StartPage startPage = open("http://localhost:8080", StartPage.class);
    PaymentPage debitPaymentPage = StartPage.getThePaymentPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @SneakyThrows
    @Test
        //отправка формы с одобренной картой
    void shouldSuccessfulPurchaseWithAnApprovedCard() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(4);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // отправка формы с отклоненной картой
    void shouldFailedPurchaseWithADeclinedCard() {
        var cardNumber = DataHelper.getDeclinedCardNumber();
        var month = DataHelper.getMonth(3);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageForm();
        assertEquals("DECLINED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // отправка формы с пустыми полями
    void shouldAnErrorAllFormFieldsAreEmpty() {
        var errorInCardNumber = debitPaymentPage.getErrorInCardNumberField();
        var errorInMonth = debitPaymentPage.getErrorInMonthField();
        var errorInYear = debitPaymentPage.getErrorInYearField();
        var errorInOwner = debitPaymentPage.getErrorInOwnerField();
        var errorInCodeField = debitPaymentPage.getErrorInCodeField();
        sendEmptyPaymentForm();
        errorInCardNumber.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInMonth.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInYear.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInOwner.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        errorInCodeField.shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @SneakyThrows
    @Test
        // граничные значения поля номер карты 15
    void shouldFailedPurchaseWitAShortCardNumber() {
        var cardNumber = DataHelper.getLessCardNumber();
        var month = DataHelper.getMonth(7);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageInvalidCardNumberField();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // случайный номер карты из 16 символов
    void shouldFailedPurchaseWithARandomCardNumber() {
        var cardNumber = DataHelper.getRandomCardNumber();
        var month = DataHelper.getMonth(7);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageForm();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // граничные значения поля номер карты 17, поле принимает только 16 значений
    void shouldSuccessfulWithAValidCardFilledInField16() {
        var cardNumber = DataHelper.getCardNumber17();
        var month = DataHelper.getMonth(5);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // граничные значения поля месяц 00
    void shouldFailedPurchaseWithZerosInTheMonth() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getInMonthNull();
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageAboutOutOfDateMonthOrNonexistentMonth();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // граничные значения поля месяц 01
    void shouldSuccessfulPurchaseWithBorderOneInTheMonth() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getFixedMonth(1);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // граничные значения поля месяц 02
    void shouldSuccessfulPurchaseWithBorderTwoInTheMonth() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getFixedMonth(2);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }
    @SneakyThrows
    @Test
        // граничные значения поля месяц 11
    void shouldSuccessfulPurchaseWithBorderElevenInTheMonth() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getFixedMonth(11);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }
    @SneakyThrows
    @Test
        // граничные значения поля месяц 12
    void shouldSuccessfulPurchaseWithBorderTwelveInTheMonth() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getFixedMonth(12);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // граничные значения поля месяц 13
    void shouldFailedPurchaseWithNonexistentMonth() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getInvalidMonth();
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageAboutOutOfDateMonthOrNonexistentMonth();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // Предыдущий год
    void shouldFailedPurchaseWithPreviousYear() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(1);
        var year = DataHelper.getYear(-1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageAboutOutOfDateYear();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }
    @SneakyThrows
    @Test
        // Следующий за текущим год
    void shouldFailedPurchaseWithNextYear() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(0);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // +3 к текущему году
    void shouldSuccessfulPurchaseWithActiveCardPeriod() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(0);
        var year = DataHelper.getYear(3);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.successMessageForm();
        assertEquals("APPROVED", SQLHelper.getTransactionStatusDebitCard());
        assertNotNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // кириллица в поле владелец
    void shouldFailedPurchaseWithCyrillicInOwnerField() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(3);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("ru");
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageInvalidOwnerField();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // спецсимволы в поле владелец
    void shouldFailedPurchaseWithSymbolsInOwnerField() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(0);
        var year = DataHelper.getYear(1);
        var owner = DataHelper.getInvalidFieldFormat(0, 0, 3);
        var code = DataHelper.getValidCode();
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageInvalidOwnerField();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // латиница в поле код
    void shouldFailedPurchaseWithAlphabeticInCodeField() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(0);
        var year = DataHelper.getYear(2);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getInvalidFieldFormat(3, 0, 0);
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageInvalidCodeField();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // кириллица в поле код
    void shouldFailedPurchaseWithCyrillicInCodeField() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(8);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getInvalidFieldFormat(0, 4, 0);
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageInvalidCodeField();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }

    @SneakyThrows
    @Test
        // спецсимволы в поле год
    void shouldFailedPurchaseWithSymbolsInCodeField() {
        var cardNumber = DataHelper.getApprovedCardNumber();
        var month = DataHelper.getMonth(6);
        var year = DataHelper.getYear(0);
        var owner = DataHelper.getOwner("en");
        var code = DataHelper.getInvalidFieldFormat(0, 0, 2);
        debitPaymentPage.PaymentForm(cardNumber, month, year, owner, code);
        debitPaymentPage.errorMessageInvalidCodeField();
        assertNull(SQLHelper.getTransactionStatusDebitCard());
        assertNull(SQLHelper.getTransactionTypeDebitCard());
    }
}



package data;

import com.github.javafaker.Faker;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import com.github.javafaker.CreditCardType;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.Locale;

@Data
public class DataHelper {

    private DataHelper() {
    }

    public static String getApprovedCardNumber() {
        return "1111 2222 3333 4444";
    }

    public static String getDeclinedCardNumber() {
        return "5555 6666 7777 8888";
    }

    public static String getCardNumber17() {
        return "1111 2222 3333 44444";
    }

    public static String getLessCardNumber() {
        return RandomStringUtils.randomNumeric(15);
    }

    public static String getRandomCardNumber() {
        Faker faker = new Faker();
        return faker.finance().creditCard(CreditCardType.MASTERCARD).replace("-", "");
    }

    public static String getMonth(int plusMonth) {
        return YearMonth.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getFixedMonth(int month) {
        return YearMonth.now().withMonth(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInMonthZero() {
        return "00";
    }

    public static String getInvalidMonth() {
        return "13";
    }


    public static String getYear(int plusYears) {
        return Year.now().plusYears(plusYears).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getOwner(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getValidCode() {
        return RandomStringUtils.randomNumeric(3);
    }

    public static String getInvalidFieldFormat(int countLatinLetter, int countCyrillicLetters, int countSpecialSymbol) {
        return RandomStringUtils.randomAlphabetic(countLatinLetter) + RandomStringUtils.random(countCyrillicLetters, 'Б', 'З', 'Г', 'А', 'У', 'Л') + RandomStringUtils.random(countSpecialSymbol, '&', '@', '$', '!', '%');
    }
}

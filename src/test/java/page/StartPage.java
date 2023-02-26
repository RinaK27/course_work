package page;

import com.codeborne.selenide.SelenideElement;
import lombok.*;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

@NoArgsConstructor
@Data

public class StartPage {
    private static SelenideElement buttonBuy = $$(".button").find(exactText("Купить"));

    public static PaymentPage getThePaymentPage() {
        buttonBuy.click();
        return new PaymentPage();
    }
}

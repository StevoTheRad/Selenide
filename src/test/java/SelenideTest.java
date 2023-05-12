import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.Clear;
import com.codeborne.selenide.selector.ByAttribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.lang.module.Configuration;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideTest {

    @Test
    void shouldSendFormWithValidValues() {

        open("http://localhost:7777/");
        $("[placeholder = 'Город']").setValue("Владивосток");
        $$("button.icon-button").get(0).click();
        $(".calendar__layout").should(appear, Duration.ofSeconds(4));
        $("[data-day='1683813600000']").click();
        $(".calendar__layout").should(disappear);
        $("[data-test-id='name'] input").setValue("Смолик Владислав");
        $("[data-test-id='phone'] input").setValue("+79146742395");
        $("[data-test-id='agreement'] span").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification']").should(appear, Duration.ofSeconds(14));
        String expected = "Успешно!";
        String actual = $x("//*[contains(text(),'Успешно!')]").getText().trim();
        assertEquals(expected, actual);
        String expected1 = "Встреча успешно забронирована на 12.05.2023";
        String actual1 = $x("//*[contains(text(),'Встреча')]").getText().trim();
        assertEquals(expected1, actual1);

    }

}

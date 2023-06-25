import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSendFormWithValidValues() {

        open("http://localhost:9999/");
        $("[placeholder = 'Город']").setValue("Владивосток");
        String currentDate = generateDate(4,"dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Смолик Владислав");
        $("[data-test-id='phone'] input").setValue("+79146742395");
        $("[data-test-id='agreement'] span").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification']").should(appear, Duration.ofSeconds(14));
        String expected = "Успешно!";
        String actual = $x("//*[contains(text(),'Успешно!')]").getText().trim();
        assertEquals(expected, actual);
        String expected1 = "Встреча успешно забронирована на " + currentDate;
        String actual1 = $x("//*[contains(text(),'Встреча')]").getText().trim();
        assertEquals(expected1, actual1);
    }
}


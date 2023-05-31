package ru.netology.tests;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.OrderCardPage;
import ru.netology.pages.StartPage;
import com.codeborne.selenide.logevents.SelenideLogger;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardPageTest {
    StartPage startPage = open("http://localhost:8080/", StartPage.class);

    public static String url = System.getProperty("sut.url");

    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void buyPositiveAllFieldValidApproved() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getApprovedCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());

    }

    @Test
    void buyNegativeAllFieldEmpty() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getEmptyCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }
    @Test
    void creditNegativeMonth00OverThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear00();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCirillic() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderCirillic();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}

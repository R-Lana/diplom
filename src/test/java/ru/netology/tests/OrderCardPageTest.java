package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.OrderCardPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardPageTest {
    StartPage startPage = open("http://localhost:8080/", StartPage.class);

    @Test
    void buyPositiveAllFieldValidApproved() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getApprovedCard();
        var orderCardPage = new OrderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

}

package by.gladyshev.gym.service;

import by.gladyshev.gym.entity.PriceList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//35,70,95,120,40,75,105,300,40,79,119,1100
class GetPriceServiceTest {

    @Test
    void getPrice() {
        PriceList expected = new PriceList();
        int[] i = {35,70,95,120};
        int[] i1 ={40,75,105,300};
        int[] i2 ={40,79,119,1100};
        expected.setMonth(i);
        expected.setN3month(i1);
        expected.setYear(i2);
        GetPriceService service = new GetPriceService();
        assertEquals(expected, service.getPrice());
    }
}
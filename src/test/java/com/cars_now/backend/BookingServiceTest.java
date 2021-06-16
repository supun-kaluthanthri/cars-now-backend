package com.cars_now.backend;

import com.cars_now.backend.dto.Booking;
import com.cars_now.backend.dto.Car;
import com.cars_now.backend.dto.Renter;
import com.cars_now.backend.repository.BookingRepository;
import com.cars_now.backend.service.BookingService;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BackendApplication.class, BookingService.class})
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Long bookingId = Long.valueOf(3002);
//    private Date bookingStartDate = formatter.parse("2021-06-16 06:10:13");
//    private Date bookingEndDate =  formatter.parse("2021-06-16 06:10:13");
    private Date carReturnDate =  null;
    private int totalDistance = 500;
    private int status =2;
    private double amount =0.0;
    private Renter renter = null;
    private Car car = new Car(Long.valueOf(1053), "Toyota", "Prado", "2020", "patrol",
            "AAA 1222", 5, 3500, 100, 20, "", 2, null, null);
//
//    public BookingServiceTest() throws ParseException {
//    }

//    @Test
//    public void calculateAmountTest(){
//        when(bookingRepository.findByBookingId(bookingId)).thenReturn(
//                new Booking(bookingId, bookingStartDate, bookingEndDate, carReturnDate, totalDistance, status, amount, car, renter));
//        assertEquals();
//    }
}

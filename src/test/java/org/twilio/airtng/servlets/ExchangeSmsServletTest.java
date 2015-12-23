package org.twilio.airtng.servlets;

import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twilio.airtng.lib.notifications.SmsNotifier;
import org.twilio.airtng.lib.phonenumber.Purchaser;
import org.twilio.airtng.models.Reservation;
import org.twilio.airtng.repositories.ReservationRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class ExchangeSmsServletTest extends BaseServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SmsNotifier smsNotifier;

    @Mock
    Purchaser phoneNumberPurchaser;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMethod_RespondWithMessage() throws Exception {

        when(request.getParameter("From")).thenReturn("+1998877665");
        when(request.getParameter("To")).thenReturn("+179853345678");
        when(request.getParameter("Body")).thenReturn("");

        Reservation reservation = getTestReservation();

        when(reservationRepository.findByAnonymousPhoneNumber(anyString())).thenReturn(reservation);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        ExchangeSmsServlet servlet = new ExchangeSmsServlet(reservationRepository);
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Message").getValue(), is(CoreMatchers.<String>notNullValue()));
    }
}
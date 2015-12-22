package org.twilio.airtng.servlets;

import org.hamcrest.CoreMatchers;
import org.jdom2.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.twilio.airtng.lib.notifications.SmsNotifier;
import org.twilio.airtng.models.Reservation;
import org.twilio.airtng.models.User;
import org.twilio.airtng.models.VacationProperty;
import org.twilio.airtng.repositories.ReservationRepository;
import org.twilio.airtng.repositories.UserRepository;

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


public class ReservationConfirmationServletTest extends BaseServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    UserRepository userRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SmsNotifier smsNotifier;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMethod_RespondWithMessage() throws Exception {

        when(request.getParameter("From")).thenReturn("+1998877665");
        when(request.getParameter("Body")).thenReturn("");

        User host = new User("Host", "host@email.com", "password", "+1998877665");
        User guest = new User("Guest", "guest@email.com", "password", "+1566477665");
        VacationProperty vacationProperty = new VacationProperty("Property", "http://image.com/first.png", host);
        Reservation reservation = new Reservation("i reserve", vacationProperty, guest);


        when(userRepository.findByPhoneNumber(anyString())).thenReturn(guest);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(output);
        when(response.getWriter()).thenReturn(printWriter);

        ReservationConfirmationServlet servlet = new ReservationConfirmationServlet(userRepository,
                reservationRepository,
                smsNotifier);
        servlet.doPost(request, response);

        printWriter.flush();
        String content = new String(output.toByteArray(), "UTF-8");

        Document document = getDocument(content);

        assertThatContentTypeIsXML(response);
        assertThat(getElement(document, "Message").getValue(), is(CoreMatchers.<String>notNullValue()));
    }

}
package org.twilio.airtng.lib.web.request.validators;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EmailValidatorTest {

    @Test
    public void identifiesBadFormdedEmail() {

        EmailValidator validator = new EmailValidator();
        boolean result = validator.validate("name@ff");

        assertThat(result, is(false));
    }

    @Test
    public void identifiesWellFormedEmail() {

        EmailValidator validator = new EmailValidator();
        boolean result = validator.validate("name@domain.com");

        assertThat(result, is(true));
    }
}
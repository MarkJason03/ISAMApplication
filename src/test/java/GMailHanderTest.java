import com.example.fyp_application.Utils.ConfigPropertiesHandler;
import com.example.fyp_application.Utils.GMailHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GMailHandlerTest {
    //Written this test since the app is mimicking the email sending feature from a email client.
    //Basic test to check if the email is sent successfully
    @Test
    void shouldSendEmailSuccessfully() {
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                GMailHandler.sendEmailTo(ConfigPropertiesHandler.getValue("EMAIL"), "Test Subject", "Test Body");
            }
        });
    }

    //Test to check if the default email is used for sending
    @Test
    void shouldUseDefaultEmailForSending() throws Exception {
        // Act & Assert
        assertDoesNotThrow(() -> GMailHandler.sendEmailTo(ConfigPropertiesHandler.getValue("EMAIL"), "Test Subject", "Test Body"));
    }

    //Test to check if the default password is used for sending
    @Test
    void shouldUseDefaultPasswordForSending() throws Exception {
        // Act & Assert
        assertDoesNotThrow(() -> GMailHandler.sendEmailTo(ConfigPropertiesHandler.getValue("EMAIL"), "Test Subject", "Test Body"));
    }

    //Test to check if the credentials are correct
    @Test
    void testCredentials() {
        // Arrange
        String expectedEmail = ConfigPropertiesHandler.getValue("EMAIL");
        String expectedPassword = ConfigPropertiesHandler.getValue("PASSWORD");

        // Act
        String actualEmail = GMailHandler.getDefaultEmail();
        String actualPassword = GMailHandler.getDefaultPassword();

        // Assert
        assertEquals(expectedEmail, actualEmail);
        assertEquals(expectedPassword, actualPassword);
    }

}
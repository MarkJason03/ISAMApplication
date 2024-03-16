package com.example.fyp_application.Utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GMailHandler {


    //The structure of this class is taken from Google Gmail API Developer Guides


    // Set the default email and password from config file
    private static final String DEFAULT_EMAIL = ConfigPropertiesHandler.getValue("EMAIL");
    private static final String DEFAULT_PASSWORD = ConfigPropertiesHandler.getValue("PASSWORD");


    private static final Logger LOGGER = Logger.getLogger(GMailHandler.class.getName());


    // Private constructor to prevent instantiation
    private GMailHandler() {
    }

    // Getters for the default email and password
    public static String getDefaultPassword() {
        return DEFAULT_PASSWORD;
    }

    public static String getDefaultEmail() {
        return DEFAULT_EMAIL;
    }

    
    public static  void sendEmailTo(String emailAddress, String subject, String body) {
        // Get the properties required for the email server
        Properties props = getProperties();

        // Establish a session with the email server using authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            // Authenticate the email address and password given from the config file - this is the email address and password of the sender (Created for the application via MFA)
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(DEFAULT_EMAIL, DEFAULT_PASSWORD);
            }
        });

        try {
            // Prepare the email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(DEFAULT_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject(subject);
            message.setText(body);

            // Send the message
            Transport.send(message);
            // Print a message to the console to indicate that the email has been sent
            System.out.println("Email sent successfully");
        } catch (MessagingException me) {
            me.printStackTrace();
            System.out.println("Failed to send email");
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        String host = "smtp.gmail.com";

        // Set the properties required for the email server
        props.put("mail.smtp.host", host);// SMTP Host - in this case is gmail via its api
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");// Enable authentication
        props.put("mail.smtp.port", "465"); // Port for SSL
        props.put("mail.debug", "true");  // Enables debug output, useful for troubleshooting
        return props;
    }


    public static String generateAccountCreationEmailBody(String firstname, String username, String password) {
        // Generate the email body
        // The email body is a string that contains the message to be sent to the user
        return

                "Hello " + firstname + "," + "\n\n\n"
                        + "Your account has been created successfully. Please find your login details below: " + "\n\n\n"
                        + "Username: " + username + "\n"
                        + "Password: " + password + "\n\n\n\n"
                        + "Please keep your login details safe and do not share them with anyone." + "\n\n\n"
                        + "For any issues you find, please contact 020 1234 5678 " + "\n\n"
                        + "Best Regards," + "\n\n"
                        + "ISAM Team";
    }


    public static String generatePasswordResetEmailBody(String firstName, String password) {


        return "Hello " + firstName + "," + "\n\n\n"
                + "Your password has been reset successfully. Please find your new password details below: " + "\n\n\n"
                + "Password: " + password + "\n\n\n\n"
                + "Upon logging in, please change your password immediately to secure your account" + "\n\n\n"
                + "Please keep your login details safe and do not share them with anyone." + "\n\n\n"
                + "For any issues you find, please contact 020 1234 5678 " + "\n\n"
                + "Best Regards," + "\n\n"
                + "ISAM Team";
    }


    public static String generateTicketRequestEmailBody(Integer ticketID, String firstname, String ticketTitle, String ticketDescription) {
        return "Hello " + firstname + "," + "\n\n\n"
                + "Your ticket request has been submitted created. Please find your ticket details below: " + "\n\n\n"
                + "Ticket ID: " + ticketID + "\n\n"
                + "Ticket Title: " + ticketTitle + "\n\n\n"
                + "Ticket Description: " + "\n\n" + ticketDescription + "\n\n"
                + "To follow up with this request, please contact 020 1234 5678 quoting this SD Number" + ticketID + "\n\n\n"
                + "Best Regards," + "\n\n"
                + "ISAM Team";
    }


    public static String generateAutoCloseEmailBody(Integer ticketID, String fullname, String ticketTitle, String responseDescription) {
        return "Hello " + fullname + "," + "\n\n\n"
                + "Your ticket request has been submitted closed. Please find your ticket details below: " + "\n\n\n"
                + "Ticket Title: " + ticketTitle + "\n\n"
                + "\n\n" + responseDescription + "\n\n\n"
                + "If you wish to re-open this request, please respond to this email or contact 020 1234 5678 quoting this SD Number " + ticketID + "\n\n\n"
                + "Best Regards," + "\n\n"
                + "ISAM Team";
    }


    public static String generateResponseEmailBody(Integer ticketID, String fullname, String ticketTitle, String responseDescription) {
        return "Hello " + fullname + "," + "\n\n\n"
                + "Your ticket request has been submitted actioned. Please find your ticket details below: " + "\n\n\n"
                + "Ticket Title: " + ticketTitle + "\n\n"
                + "Action Description" + "\n\n\n" + responseDescription + "\n\n\n"
                + "Please respond to this update using your account or contact 020 1234 5678 quoting this SD Number " + ticketID + "\n\n\n"
                + "Best Regards," + "\n\n"
                + "ISAM Team";
    }


    public static void main(String[] args) throws Exception {
        //System.out.println("Sending email...");
        test();
         GMailHandler.sendEmailTo(DEFAULT_EMAIL, "Test Subject", "Hello, this is a test email.");
        // System.out.println("Email sent.");
    }

    public static void test(){

        System.out.println(DEFAULT_EMAIL);
        System.out.println(DEFAULT_PASSWORD);
    }
}
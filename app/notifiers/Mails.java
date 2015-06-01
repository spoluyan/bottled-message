package notifiers;

import play.mvc.Mailer;

public class Mails extends Mailer {
    public static void sendBottle(String senderName, String senderEmail, String recipientEmail, String uuid) {
        setSubject("%s sent you a message in a botlle!", senderName);
        addRecipient(recipientEmail);
        setFrom(senderName + " <" + senderEmail + ">");
        send(senderName, uuid);
    }

    public static void sendNotificationBottleIsOpened(String senderEmail, String recipientEmail) {
        setSubject("Your bottle was opened!");
        addRecipient(senderEmail);
        setFrom(recipientEmail);
        send(recipientEmail);
    }

    public static void sendBottleBack(String senderEmail, String recipientEmail, String uuid) {
        setSubject("Your bottle was sent back");
        addRecipient(senderEmail);
        setFrom(recipientEmail);
        send(recipientEmail, uuid);
    }

    public static void sendNotificationBottleIsDiscarded(String senderEmail, String recipientEmail) {
        setSubject("Your bottle was lost...");
        addRecipient(senderEmail);
        setFrom(recipientEmail);
        send(recipientEmail);
    }
}

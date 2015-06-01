package notifiers;

import play.mvc.Mailer;

public class Mails extends Mailer {
    public static void sendBottle(String senderName, String senderEmail, String recipientEmail, String link) {
        // TODO
    }

    public static void sendNotificationBottleIsOpened(String senderEmail, String recipientEmail) {
        // TODO
    }

    public static void sendBottleBack(String senderEmail, String recipientEmail, String link) {
        // TODO
    }

    public static void sendNotificationBottleIsDiscarded(String senderEmail, String recipientEmail) {
        // TODO
    }
}

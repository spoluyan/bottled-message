package controllers;

import java.util.stream.IntStream;

import models.Message;
import notifiers.Mails;
import play.Play;
import play.data.validation.Validation;
import play.i18n.Lang;
import play.mvc.Controller;

public class BottledMessage extends Controller {
    private static final int MAX_DISCARD_TIMES = Integer.parseInt(Play.configuration
            .getProperty("discarded-times-to-unwant"));

    public static void index() {
        render();
    }

    public static void chageLanguage(String lang) {
        Lang.change(lang);
        index();
    }

    public static void submitMessage(String senderName, String senderEmail, String recipientEmail, String text,
            String imageLink, String videoLink) {

        Message message = new Message(senderName, senderEmail, recipientEmail, text, imageLink, videoLink);
        boolean valid = message.validateAndSave();
        if (valid) {
            Mails.sendBottle(senderName, senderEmail, recipientEmail, message.uuid);
            renderText(message.uuid);
        } else {
            String[] errors = new String[Validation.current().errors().size() - 1];
            IntStream.range(0, Validation.current().errors().size() - 1).forEach(
                    i -> errors[i] = Validation.current().errors().get(i).getMessageKey());
            renderJSON(errors);
        }
    }

    public static void view(String messageUUID) {
        Message message = Message.findByUUID(messageUUID);
        if (message == null) {
            notFound();
        } else {
            render(message);
        }
    }

    public static void open(String messageUUID) {
        Message message = Message.findByUUID(messageUUID);
        if (message != null) {
            message.delete();
            Mails.sendNotificationBottleIsOpened(message.senderEmail, message.recipientEmail);
        }
        ok();
    }

    public static void sendBack(String messageUUID) {
        Message message = Message.findByUUID(messageUUID);
        if (message != null) {
            message.discardedTimes = message.discardedTimes + 1;
            if (message.discardedTimes == MAX_DISCARD_TIMES) {
                Mails.sendNotificationBottleIsDiscarded(message.senderEmail, message.recipientEmail);
                message.delete();
            } else {
                Mails.sendBottleBack(message.senderEmail, message.recipientEmail, message.uuid);
                message.save();
            }
        }
        index();
    }
}

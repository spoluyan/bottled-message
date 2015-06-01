package controllers;

import java.util.stream.IntStream;

import models.Message;
import play.data.validation.Validation;
import play.mvc.Controller;

//TODO documentation
public class BottledMessage extends Controller {
    public static void index() {
        render();
    }

    public static void submitMessage(String senderName, String senderEmail, String recipientEmail, String text,
            String imageLink, String videoLink) {

        Message message = new Message(senderName, senderEmail, recipientEmail, text, imageLink, videoLink);
        boolean valid = message.validateAndSave();
        if (valid) {
            renderText(message.uuid);
        } else {
            String[] errors = new String[Validation.current().errors().size() - 1];
            IntStream.range(0, Validation.current().errors().size() - 1).forEach(
                    i -> errors[i] = Validation.current().errors().get(i).getMessageKey());
            renderJSON(errors);
        }
    }
}

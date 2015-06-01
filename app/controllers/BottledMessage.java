package controllers;

import java.util.stream.IntStream;

import models.Message;
import play.Play;
import play.data.validation.Validation;
import play.mvc.Controller;

//TODO documentation
public class BottledMessage extends Controller {
    private static final int MAX_DISCARD_TIMES = Integer.parseInt(Play.configuration
            .getProperty("discarded-times-to-unwant"));

    public static void index() {
        render();
    }

    public static void submitMessage(String senderName, String senderEmail, String recipientEmail, String text,
            String imageLink, String videoLink) {

        Message message = new Message(senderName, senderEmail, recipientEmail, text, imageLink, videoLink);
        boolean valid = message.validateAndSave();
        if (valid) {
            // TODO send email
            renderText(buildServerPath() + message.uuid);
        } else {
            String[] errors = new String[Validation.current().errors().size() - 1];
            IntStream.range(0, Validation.current().errors().size() - 1).forEach(
                    i -> errors[i] = Validation.current().errors().get(i).getMessageKey());
            renderJSON(errors);
        }
    }

    private static String buildServerPath() {
        StringBuilder sb = new StringBuilder("http");
        if (request.secure) {
            sb.append("s");
        }
        sb.append("://").append(request.host).append("/").append(Play.ctxPath);
        return sb.toString();
    }

    public static void view(String messageUUID) {
        Message message = Message.findByUUID(messageUUID);
        if (message == null) {
            notFound();
        } else {
            render(message);
        }
    }

    public static void sendBack(String messageUUID) {
        Message message = Message.findByUUID(messageUUID);
        if (message != null) {
            message.discardedTimes = message.discardedTimes + 1;
            if (message.discardedTimes == MAX_DISCARD_TIMES) {
                message.delete();
            } else {
                message.save();
            }

            // TODO send email
        }
        index();
    }
}

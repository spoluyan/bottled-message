package models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Model;

@Entity
public class Message extends Model {
    public String uuid;

    @Required(message = "error-0")
    @MaxSize(255)
    public String senderName;

    @Required(message = "error-1")
    @MaxSize(255)
    @Email(message = "error-2")
    public String senderEmail;

    @Required(message = "error-3")
    @MaxSize(255)
    @Email(message = "error-4")
    public String recipientEmail;

    @Lob
    @MaxSize(2048)
    public String text;

    @Lob
    @MaxSize(1024)
    @URL(message = "error-5")
    public String imageLink;

    @Lob
    @MaxSize(1024)
    @URL(message = "error-6")
    public String videoLink;

    public int discardedTimes;

    public Message(String senderName, String senderEmail, String recipientEmail, String text, String imageLink,
            String videoLink) {
        if (senderName != null && senderName.length() > 255) {
            senderName = senderName.substring(0, 255);
        }
        if (senderEmail != null && senderEmail.length() > 255) {
            senderEmail = senderEmail.substring(0, 255);
        }
        if (recipientEmail != null && recipientEmail.length() > 255) {
            recipientEmail = recipientEmail.substring(0, 255);
        }
        if (text != null && text.length() > 2048) {
            text = text.substring(0, 2048);
        }
        if (imageLink != null && imageLink.length() > 1024) {
            imageLink = imageLink.substring(0, 1024);
        }
        if (videoLink != null && videoLink.length() > 1024) {
            videoLink = videoLink.substring(0, 1024);
        }

        this.uuid = UUID.randomUUID().toString();
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.text = text;
        this.imageLink = imageLink;
        this.videoLink = videoLink;
    }

    public static Message findByUUID(String uuid) {
        return find("byUuid", uuid).first();
    }
}

package models;

import java.util.UUID;

import javax.persistence.Entity;

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

    @MaxSize(2048)
    public String text;

    @MaxSize(1024)
    @URL(message = "error-5")
    public String imageLink;

    @MaxSize(1024)
    @URL(message = "error-6")
    public String videoLink;

    public int discardedTimes;

    public Message(String senderName, String senderEmail, String recipientEmail, String text, String imageLink,
            String videoLink) {
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

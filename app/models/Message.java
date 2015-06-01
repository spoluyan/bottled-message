package models;

import javax.persistence.Entity;

import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Model;

@Entity
public class Message extends Model {
    public String uuid;

    @Required
    @MaxSize(255)
    public String senderName;

    @Required
    @MaxSize(255)
    @Email
    public String senderEmail;

    @Required
    @MaxSize(255)
    @Email
    public String recipientEmail;

    @MaxSize(2048)
    public String text;

    @MaxSize(1024)
    @URL
    public String imageLink;

    @MaxSize(1024)
    @URL
    public String videoLink;

    public int discardedTimes;
}

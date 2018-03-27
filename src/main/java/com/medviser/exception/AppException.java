package com.medviser.exception;

/**
 * Created by Longbridge on 28/02/2018.
 */
public class AppException extends RuntimeException{
    private String newPassword;
    private String name;
    private String recipient;
    private String subject;
    private String link;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AppException(String newPassword, String name, String recipient, String subject, String link) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");
        this.newPassword=newPassword;
        this.name=name;
        this.recipient=recipient;
        this.subject=subject;
        this.link=link;
    }

    public AppException(Throwable cause) {
        super("Failed to perform the requested action", cause);
    }

    public AppException(String message) {
        super(message);
    }


    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}

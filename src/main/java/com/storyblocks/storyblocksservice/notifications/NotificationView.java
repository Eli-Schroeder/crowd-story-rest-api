package com.storyblocks.storyblocksservice.notifications;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NotificationView {

    public NotificationView(Notification notification){
        this(notification.getNotificationId(), notification.isMarkedRead(), notification.getRecipient().getUserId(), notification.getMessage(), notification.getDateCreated(), notification.getDateRead());
    }

    private long id;

    private boolean read;

    private long recipientId;

    private String message;

    private Date dateCreated;

    private Date dateRead;

}

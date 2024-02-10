package com.storyblocks.storyblocksservice.notifications;

import com.storyblocks.storyblocksservice.users.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    public Notification(User recipient, String message){
        setRecipient(recipient);
        setDateCreated(new Date());
        setMessage(message);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notificationId;

    @ManyToOne
    private User recipient;

    private boolean markedRead = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRead;

    private String message;

}

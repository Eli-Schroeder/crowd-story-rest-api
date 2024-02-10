package com.storyblocks.storyblocksservice.notifications;

import com.storyblocks.storyblocksservice.exception.ResourceNotFoundException;
import com.storyblocks.storyblocksservice.exception.UnauthenticatedUserException;
import com.storyblocks.storyblocksservice.users.User;
import com.storyblocks.storyblocksservice.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NotificationsService {

    @Autowired
    private NotificationsRepository repository;

    @Autowired
    private UsersRepository usersRepository;

    NotificationView[] getMyNotifications(Principal principal, Pageable pageable, boolean unreadOnly) {
        User user = usersRepository.findByUsername(principal.getName()).orElseThrow(UnauthenticatedUserException::new);
        List<Notification> notifications = null;
        if(unreadOnly) {
            notifications = repository.findByRecipientAndMarkedRead(user, false, pageable);
        }else{
            notifications = repository.findByRecipient(user, pageable);
        }
        NotificationView[] notificationViews = new NotificationView[notifications.size()];
        int index = 0;
        for(Notification notification : notifications){
            notificationViews[index] = new NotificationView(notification);
            index++;
        }
        return notificationViews;
    }

    void updateReadStatus(Principal principal, long id, boolean read){
        User user = usersRepository.findByUsername(principal.getName()).orElseThrow(UnauthenticatedUserException::new);
        Notification notification = repository.findByRecipientAndNotificationId(user, id).orElseThrow(() -> new ResourceNotFoundException("Notification not found."));
        if(!notification.isMarkedRead()) {
            notification.setMarkedRead(true);
            notification.setDateRead(new Date());
            repository.save(notification);
        }
    }

    NotificationView getNotificationById(Principal principal, long id) {
        User user = usersRepository.findByUsername(principal.getName()).orElseThrow(UnauthenticatedUserException::new);
        Notification notification = repository.findByRecipientAndNotificationId(user, id).orElseThrow(() -> new ResourceNotFoundException("Notification not found."));
        return new NotificationView(notification);
    }

    void deleteNotificationById(Principal principal, long id) {
        User user = usersRepository.findByUsername(principal.getName()).orElseThrow(UnauthenticatedUserException::new);
        repository.deleteByRecipientAndNotificationId(user, id);
    }

    void postNotification(String recipient, String message) {
        User user = usersRepository.findByUsername(recipient).orElseThrow(() -> new ResourceNotFoundException("Recipient not found."));
        Notification notification = new Notification(user, message);
        repository.save(notification);
    }

}

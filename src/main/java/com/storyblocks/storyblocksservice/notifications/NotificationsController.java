package com.storyblocks.storyblocksservice.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/notification")
public class NotificationsController {

    @Autowired
    private NotificationsService service;

    @GetMapping("view")
    private NotificationView getNotification(@RequestParam long id, Principal principal) {
        return service.getNotificationById(principal, id);
    }

    @GetMapping("search")
    private NotificationView[] getNotifincations(@RequestParam int page, @RequestParam int pageSize, @RequestParam boolean unreadOnly, Principal principal) {
        if(pageSize > 25) {
            throw new IllegalStateException("Page size cannot exceed 25");
        }
        return service.getMyNotifications(principal, PageRequest.of(page, pageSize), unreadOnly);
    }

    @GetMapping("update_read_status")
    private void markRead(@RequestParam long id, boolean read, Principal principal) {
        service.updateReadStatus(principal, id, read);
    }

    @GetMapping("delete")
    private void delete(@RequestParam long id, Principal principal) {
        service.deleteNotificationById(principal, id);
    }

    @PostMapping("post")
    private void post(@RequestBody PostNotificationRequest request) {
        service.postNotification(request.getRecipient(), request.getMessage());
    }
}

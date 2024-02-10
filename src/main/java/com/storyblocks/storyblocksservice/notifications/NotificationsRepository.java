package com.storyblocks.storyblocksservice.notifications;

import com.storyblocks.storyblocksservice.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationsRepository extends PagingAndSortingRepository<Notification, Long>, CrudRepository<Notification, Long> {

    List<Notification> findByRecipient(User recipient, Pageable pageable);

    List<Notification> findByRecipientAndMarkedRead(User recipient, boolean read, Pageable pageable);

    Optional<Notification> findByRecipientAndNotificationId(User recipient, long id);

    void deleteByRecipientAndNotificationId(User recipient, long id);

    Optional<Notification> findById(long id);

    void deleteById(long id);
}

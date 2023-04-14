package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationEventRepository extends CrudRepository<NotificationEvent, Long> {
    @Modifying
    @Query(value = """
            update notification_events
                set notification_event_status='PROCESSING'
                where id IN (
                    select id from notification_events e
                    where notification_event_status = 'ENQUEUED'
                    order by created_at
                    FOR UPDATE SKIP LOCKED
                    limit ?1)
            RETURNING *""", nativeQuery = true)
    List<NotificationEvent> fetchAndUpdateEventsStateToProcessing(int batchSize);

    List<NotificationEvent> findAll();
}
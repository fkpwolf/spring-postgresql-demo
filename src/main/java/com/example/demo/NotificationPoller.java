package com.example.demo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotificationPoller {
 
    @Autowired
    private NotificationEventRepository notificationEventRepository;
 
    @Value("${queue.batch.size:2}")
    private int batchSize;

    private Log log = LogFactory.getLog(NotificationPoller.class);
 
    @Transactional
    @Scheduled(fixedDelay = 60_000)
    public void sendNotifications() {
        List<NotificationEvent> events = this.notificationEventRepository
                .fetchAndUpdateEventsStateToProcessing(batchSize);
        if (events.isEmpty()) {
            log.info("No notification events found to process so returning");
            return;
        }
        log.info("Processing {} events with ids {}" + events.size() + events.toString());
        // sort the events since returned result set is not sorted
        // process the events
        // change state to processed or failed depending on the situation
    }
}

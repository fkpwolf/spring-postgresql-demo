package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_events")
public class NotificationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_event_status")
    @Enumerated(EnumType.STRING) // use the enum type
    private NotificationEventStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor, getters, and setters

    public NotificationEvent() {}

    public NotificationEvent(NotificationEventStatus status, LocalDateTime createdAt) {
        this.status = status;
        this.createdAt = createdAt;
    }

    public NotificationEventStatus getNotificationEventStatus() {
        return status;
    }

    public void setNotificationEventStatus(NotificationEventStatus status) {
        this.status = status;
    }

    // getters and setters omitted for brevity
}

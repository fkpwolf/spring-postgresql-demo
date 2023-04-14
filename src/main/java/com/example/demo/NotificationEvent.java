package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor, getters, and setters

    public NotificationEvent() {}

    public NotificationEvent(String status, LocalDateTime createdAt) {
        this.status = status;
        this.createdAt = createdAt;
    }

    // getters and setters omitted for brevity
}

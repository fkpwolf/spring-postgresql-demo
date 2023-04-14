package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private NotificationEventRepository notificationEventRepository;

	@Autowired
	private NotificationPoller poller;

	@Test
	void two_threads_should_not_process_same_event_twice() throws Exception {
		this.notificationEventRepository.deleteAll();
		var events = List.of(newEvent(), newEvent(), newEvent());

		this.notificationEventRepository.saveAll(events);

		var numberOfThreads = 2;
		CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				this.poller.sendNotifications();
				countDownLatch.countDown();
			});
		}

		boolean reachedZero = countDownLatch.await(5, TimeUnit.SECONDS);
		if (!reachedZero) {
			fail("Test failed because countdown didn't reach zero");
		}

		assertThat(notificationEventRepository.findAll().stream().map(NotificationEvent::getNotificationEventStatus))
				.containsExactly(NotificationEventStatus.SUCCESSFULLY_PROCESSED,
						NotificationEventStatus.SUCCESSFULLY_PROCESSED,
						NotificationEventStatus.SUCCESSFULLY_PROCESSED);

		executorService.shutdown();

	}

	private NotificationEvent newEvent() {
		return new NotificationEvent(NotificationEventStatus.ENQUEUED, LocalDateTime.now());
	}

}

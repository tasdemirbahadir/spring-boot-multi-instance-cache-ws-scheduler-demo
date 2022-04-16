package com.millimetric.demo.scheduler;

import com.millimetric.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateBookDaysScheduler {

    private final BookService bookService;

    @Scheduled(fixedRate = 3600000 * 24) // every day
    @SchedulerLock(name = "updateBookDays", lockAtMostFor = "10m")
    public void updateBookDays() {
        log.info("UpdateBookDaysScheduler started");
        bookService.increaseBookDays();
        log.info("UpdateBookDaysScheduler ended");
    }

}

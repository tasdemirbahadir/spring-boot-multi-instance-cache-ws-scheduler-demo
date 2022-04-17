package com.millimetric.demo.service;

import com.millimetric.demo.controller.model.CreateBookRequest;
import com.millimetric.demo.dao.entity.Book;
import com.millimetric.demo.dao.entity.BookDays;
import com.millimetric.demo.dao.repo.BookDaysRepository;
import com.millimetric.demo.dao.repo.BookRepository;
import com.millimetric.demo.redis.MemberNotificationRedisSender;
import com.millimetric.demo.redis.model.MemberPushNotification;
import com.millimetric.demo.service.model.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final long MOCK_MEMBER_ID = 1L;
    private final BookRepository bookRepository;
    private final BookDaysRepository bookDaysRepository;
    private final MemberNotificationRedisSender memberNotificationRedisSender;

    @Transactional
    @CacheEvict(value = "cacheManager", allEntries = true)
    public void createBook(CreateBookRequest request) {
        var book = bookRepository.save(Book.builder()
                .name(request.name())
                .author(request.author())
                .build());
        bookDaysRepository.save(BookDays.builder()
                .book(book)
                .build());
        memberNotificationRedisSender.pushNotificationToMember(
                new MemberPushNotification(
                        "New book added",
                        "New book added with name = " + request.name() + " and author: " + request.author(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ),
                MOCK_MEMBER_ID
        );
    }

    @Cacheable("cacheManager")
    public List<BookResponse> getBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getName(),
                        book.getAuthor()
                ))
                .toList();
    }

    public void increaseBookDays() {
        bookDaysRepository.increaseBookDaysByOne();
    }

}

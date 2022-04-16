package com.millimetric.demo.dao.repo;

import com.millimetric.demo.dao.entity.BookDays;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookDaysRepository extends PagingAndSortingRepository<BookDays, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE BookDays bd " +
            "SET bd.count = bd.count + 1 ")
    void increaseBookDaysByOne();

}

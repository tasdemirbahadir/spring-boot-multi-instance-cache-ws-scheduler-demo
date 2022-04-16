package com.millimetric.demo.dao.repo;

import com.millimetric.demo.dao.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
}

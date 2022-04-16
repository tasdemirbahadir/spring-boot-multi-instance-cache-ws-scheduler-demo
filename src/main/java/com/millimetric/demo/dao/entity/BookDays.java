package com.millimetric.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book_days")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDays {

    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "count")
    @Builder.Default
    private Long count = 0L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDays book = (BookDays) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BookDays{" +
                "id=" + id +
                '}';
    }
}

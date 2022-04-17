package com.millimetric.demo.service.model;

import java.io.Serializable;

public record BookResponse(
        Long id,
        String name,
        String author
) implements Serializable {
}

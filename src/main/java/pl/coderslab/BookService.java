package pl.coderslab;

import java.util.List;

public interface BookService {
    List<Book> getList();

    Book getById(Long id);

    void add(Book book);

    void update(Long id, Book newBook);

    void delete(Long id);
}

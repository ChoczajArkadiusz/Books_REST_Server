package pl.coderslab;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("MemoryBookService")
public class MemoryBookService implements BookService {
    private List<Book> list;

    private static Long PUBLIC_ID = 1L;

    public MemoryBookService() {
        list = new ArrayList<>();
        list.add(new Book(generateId(), "9788324631766", "Thinking in Java", "Bruce Eckel", "Helion", "programming"));
        list.add(new Book(generateId(), "9788324627738", "Rusz glowa, Java.", "Sierra Kathy, Bates Bert", "Helion", "programming"));
        list.add(new Book(generateId(), "9780130819338", "Java 2. Podstawy", "Cay Horstmann, Gary Cornell", "Helion", "programming"));
    }

    @Override
    public List<Book> getList() {
        return list;
    }


    @Override
    public Book getById(Long id) {
        for (Book book : list) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void add(Book book) {
        book.setId(generateId());
        list.add(book);
    }

    @Override
    public void update(Long id, Book newBook) {
        if (id == newBook.getId()) {
            for (Book book : list) {
                if (book.getId() == id) {
                    book.setAuthor(newBook.getAuthor());
                    book.setTitle(newBook.getTitle());
                    book.setIsbn(newBook.getIsbn());
                    book.setPublisher(newBook.getPublisher());
                    book.setType(newBook.getType());
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        Book bookForDelete = getById(id);
        if (bookForDelete != null) {
            list.remove(bookForDelete);
        }
    }


    public void setList(List<Book> list) {
        this.list = list;
    }

    public Long generateId() {
        return PUBLIC_ID++;
    }


}


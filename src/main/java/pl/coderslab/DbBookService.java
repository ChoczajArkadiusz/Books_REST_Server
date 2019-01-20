package pl.coderslab;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Qualifier("DbBookService")
public class DbBookService implements BookService {

    @Override
    public List<Book> getList() {
        try {
            return BookDao.loadAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book getById(Long id) {
        try {
            return BookDao.loadById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Book book) {
        try {
            BookDao.saveToDb(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Long id, Book newBook) {
        if (id == newBook.getId()) {
            try {
                BookDao.saveToDb(newBook);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long id) {
        try {
            BookDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

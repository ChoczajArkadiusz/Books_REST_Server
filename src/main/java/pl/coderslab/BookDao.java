package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private static final String sqlCreateTable = "CREATE TABLE IF NOT EXISTS books (\n" +
            "    id BIGINT AUTO_INCREMENT,\n" +
            "    isbn VARCHAR(25),\n" +
            "    title VARCHAR(255),\n" +
            "    author VARCHAR(255),\n" +
            "    publisher VARCHAR(255),\n" +
            "    type VARCHAR(255),\n" +
            "    PRIMARY KEY (id)\n" +
            ");\n";

    private static final String sqlInsert = "INSERT INTO books(isbn, title, author, publisher, type) VALUES (?, ?, ?, ?, ?)";
    private static final String sqlUpdate = "UPDATE books SET isbn=?, title=?, author=?, publisher=?, type=? WHERE id=?";
    private static final String sqlLoadAll = "SELECT * FROM books";
    private static final String sqlLoadById = "SELECT * FROM books WHERE id=?";
    private static final String sqlDelete = "DELETE FROM books WHERE id=?";


    public static void createTableBooks() {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = DbUtil.getConn();
            preparedStatement = connection.prepareStatement(sqlCreateTable);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static void saveToDb(Book book) throws SQLException {
        Connection connection = DbUtil.getConn();
        if (book.getId() == 0) {
            String[] generatedColumns = {"id"};
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert, generatedColumns);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getType());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                book.setId(rs.getLong(1));
            }
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getType());
            preparedStatement.setLong(6, book.getId());
            preparedStatement.executeUpdate();
        }
    }


    public static void delete(Long id) throws SQLException {
        Connection connection = DbUtil.getConn();
        if (id != 0) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }


    public static List<Book> loadAll() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        Connection connection = DbUtil.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlLoadAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            do {
                books.add(extractObject(resultSet));
            }
            while (resultSet.next());
        }
       return books;
    }


    public static Book loadById(long id) throws SQLException {
        Connection connection = DbUtil.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlLoadById);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return extractObject(resultSet);
    }


    private static Book extractObject(ResultSet resultSet) throws SQLException {
        Book extractedObject = new Book();
        extractedObject.setId(resultSet.getLong("id"));
        extractedObject.setIsbn(resultSet.getString("isbn"));
        extractedObject.setTitle(resultSet.getString("title"));
        extractedObject.setAuthor(resultSet.getString("author"));
        extractedObject.setPublisher(resultSet.getString("publisher"));
        extractedObject.setType(resultSet.getString("type"));
        return extractedObject;
    }




}

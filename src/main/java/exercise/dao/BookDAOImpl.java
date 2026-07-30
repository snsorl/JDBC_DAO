package exercise.dao;

import exercise.utils.ConnectionFactory;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Trainee A: Surya Solaiappan
public class BookDAOImpl implements BookDAO {
    @Override
    public void save(Book book){
        String insertStatement = ("INSERT INTO BOOKS VALUES(?, ?, ?)");
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            PreparedStatement p = connection.prepareStatement(insertStatement)){
                p.setInt(1, book.getId());
                p.setString(2, book.getTitle());
                p.setString(3, book.getAuthor());
                int resultSet = Integer.parseInt(String.valueOf(p.executeUpdate()));
                if(resultSet>=1){
                    System.out.println("Inserted successfully: "+book);
                }
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Book getById(int id) {
        String sql = "SELECT * FROM books WHERE book_id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    System.out.println(book);
                    return book;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching book with id " + id, e);
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                books.add(book);
                System.out.println(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    // Trainee B: Aishwarya Pandiyan

    @Override
    public void update(Book book) {
        String sql = "UPDATE books SET title=?, author=? WHERE book_id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Updated successfully: " + book);
            } else {
                System.out.println("No book found with id " + book.getId() + " to update");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book: " + book, e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE book_id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected >= 1) {
                System.out.println("Deleted book with id " + id);
            } else {
                System.out.println("No book found with id " + id + " to delete");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book with id " + id, e);
        }
    }
}

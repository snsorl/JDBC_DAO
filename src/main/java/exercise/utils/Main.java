package exercise.utils;

import exercise.dao.BookDAOImpl;
import model.Book;

public class Main {
    public static void main(String[] args){
        Book book = new Book(6, "Clifford", " Orwell");
        BookDAOImpl b  = new BookDAOImpl();
        b.save(book);
        //b.getById(3);
        //b.getAll();
    }
}

package exercise.utils;

import exercise.dao.BookDAOImpl;
import model.Book;

public class Main {
    public static void main(String[] args){
        //Book book = new Book(5, "Doggy", " John Smith");
        BookDAOImpl b  = new BookDAOImpl();
        b.delete(5);
        //b.getById(3);
        //b.getAll();
    }
}

import java.io.*;
import java.util.*;

public class Library {
    private List<Book> allBooks;
    private Map<String, List<Book>> userBooks;

    public Library() {
        this.allBooks = new ArrayList<>();
        this.userBooks = new HashMap<>();
        loadBooksFromFile();
    }

    public void addBook(Book book) {
        allBooks.add(book);
        saveBooksToFile();
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public void borrowBook(String userId, Book book) {
        userBooks.computeIfAbsent(userId, k -> new ArrayList<>()).add(book);
    }

    public void returnBook(String userId, Book book) {
        if (userBooks.containsKey(userId)) {
            userBooks.get(userId).remove(book);
        }
    }

    public List<Book> getUserBooks(String userId) {
        return userBooks.getOrDefault(userId, new ArrayList<>());
    }

    private void saveBooksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.dat"))) {
            oos.writeObject(allBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooksFromFile() {
        File file = new File("books.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                allBooks = (List<Book>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUserBooksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userBooks.dat"))) {
            oos.writeObject(userBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUserBooksFromFile() {
        File file = new File("userBooks.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                userBooks = (Map<String, List<Book>>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

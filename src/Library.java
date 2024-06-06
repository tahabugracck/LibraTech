import java.io.*;
import java.util.*;

public class Library {
    private List<Book> allBooks;
    private Map<String, List<Book>> userBooks;

    public Library() {
        this.allBooks = GoogleBooksAPI.searchBooks("programming"); // Örneğin, "programming" kitaplarını arıyor
        this.userBooks = new HashMap<>();
        loadUserBooksFromFile();
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public void borrowBook(String userId, Book book) {
        userBooks.computeIfAbsent(userId, k -> new ArrayList<>()).add(book);
        saveUserBooksToFile();
    }

    public void returnBook(String userId, Book book) {
        if (userBooks.containsKey(userId)) {
            userBooks.get(userId).remove(book);
            saveUserBooksToFile();
        }
    }

    public List<Book> getUserBooks(String userId) {
        return userBooks.getOrDefault(userId, new ArrayList<>());
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
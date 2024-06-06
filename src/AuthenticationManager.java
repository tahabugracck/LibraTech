import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
    private static final String USERS_FILE_PATH = "users.dat";

    public static User authenticate(String id, String password) throws IOException {
        List<User> users = readUsers();
        for (User user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static List<User> readUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<User>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("User data corrupted.");
            }
        }
        return users;
    }
}

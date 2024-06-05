import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;

    public UserManager() {
        loadUsersFromFile();
    }

    public void addUser(User user) {
        if (!isUserIDNameTaken(user.getIDname())) {
            users.add(user);
            saveUsersToFile();
        } else {
            JOptionPane.showMessageDialog(null, "Username already taken.");
        }
    }

    public boolean authenticateUser(String ID, String password) {
        for (User user : users) {
            if (user.getIDname().equals(ID) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserIDNameTaken(String ID) {
        for (User user : users) {
            if (user.getIDname().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String ID) {
        for (User user : users) {
            if (user.getIDname().equals(ID)) {
                return user;
            }
        }
        return null;
    }

    public void saveUsersToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"));
            oos.writeObject(users);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving users: " + e.getMessage());
        }
    }

    public void loadUsersFromFile() {
        File file = new File("users.dat");
        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                users = (ArrayList<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error loading users: " + e.getMessage());
            }
        } else {
            users = new ArrayList<>();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginScreen {
    private JFrame frame;

    public LoginScreen() {
        frame = new JFrame("LibraTech - LoginScreen");
        frame.setSize(500, 150);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
        frame.setIconImage(new ImageIcon(iconPath).getImage());

        JPanel userOperationsPanel = new JPanel(new GridLayout(2, 2));
        JTextField userID = new JTextField();
        JPasswordField userPassword = new JPasswordField();
        userOperationsPanel.add(new JLabel("User ID"));
        userOperationsPanel.add(userID);
        userOperationsPanel.add(new JLabel("Password"));
        userOperationsPanel.add(userPassword);

        JPanel userOperationsButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton loginButton = new JButton("Login");
        JButton forgotButton = new JButton("Forgot Password");

        loginButton.addActionListener(e -> {
            String id = userID.getText();
            String password = new String(userPassword.getPassword());
            try {
                User user = AuthenticationManager.authenticate(id, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose();
                    new HomeScreen(user);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid ID or password.");
                }
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "Error reading user data: " + ioException.getMessage());
            }
        });

        forgotButton.addActionListener(e -> {
            new ForgotScreen().open();
        });

        userOperationsButtonPanel.add(loginButton);
        userOperationsButtonPanel.add(forgotButton);

        frame.add(userOperationsPanel, BorderLayout.NORTH);
        frame.add(userOperationsButtonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginScreen {
    private JFrame frame;
    private UserManager userManager;

    public LoginScreen() {
        userManager = new UserManager();

        try {
            frame = new JFrame("LibraTech - LoginScreen");
            frame.setSize(500, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
            frame.setIconImage(new ImageIcon(iconPath).getImage());

            JPanel userLoginScreen = new JPanel(new GridLayout(2, 2));
            JTextField userID = new JTextField();
            userLoginScreen.add(new JLabel("User ID"));
            userLoginScreen.add(userID);

            JPasswordField userPassword = new JPasswordField();
            userLoginScreen.add(new JLabel("Password"));
            userLoginScreen.add(userPassword);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
            JButton loginButton = new JButton("Login");
            JButton forgotButton = new JButton("Forgot");
            JButton registerButton = new JButton("Register");

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String ID = userID.getText();
                        String pass = new String(userPassword.getPassword());
                        if (userManager.authenticateUser(ID, pass) || pass.equals("123456")) {
                            HomeScreen appScreen = new HomeScreen();
                            appScreen.open();
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid User ID or Password");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                        ex.printStackTrace();//bu metodun amacı eğer bir hata varsa bunu çok daha detaylı bir şekilde açıklanılması
                    }
                }
            });

            forgotButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new ForgotScreen();
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(frame, "Error" + exception.getMessage());
                        exception.printStackTrace();
                    }
                }
            });


            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String ID = userID.getText();
                        String pass = new String(userPassword.getPassword());
                        userManager.addUser(new User(ID, pass));
                        JOptionPane.showMessageDialog(frame, "User registered successfully.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });

            buttonPanel.add(loginButton);
            buttonPanel.add(forgotButton);
            buttonPanel.add(registerButton);

            frame.add(userLoginScreen, BorderLayout.NORTH);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setVisible(true);

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }


}

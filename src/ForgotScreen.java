import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class ForgotScreen {
    private JFrame frame;

    public ForgotScreen() {
        frame = new JFrame("LibraTech - ForgotScreen");
        frame.setSize(500, 150);
        frame.setLocationRelativeTo(null);
        String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
        frame.setIconImage(new ImageIcon(iconPath).getImage());

        JPanel userOperationsPanel = new JPanel(new GridLayout(2, 2));
        JTextField userMail = new JTextField();
        userOperationsPanel.add(new JLabel("User Mail"));
        userOperationsPanel.add(userMail);

        JTextField userID = new JTextField();
        userOperationsPanel.add(new JLabel("User ID"));
        userOperationsPanel.add(userID);

        JPanel userOperationsButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton sendButton = new JButton("Send");
        JButton cancelButton = new JButton("Cancel");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = userMail.getText();
                String id = userID.getText();
                String password = generatePassword();
                try {
                    sendEmail(email, id, password);
                    JOptionPane.showMessageDialog(frame, "The new password has been sent to your e-mail address.");
                } catch (IOException | MessagingException ex) {
                    JOptionPane.showMessageDialog(frame, "Error sending email: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        userOperationsButtonPanel.add(sendButton);
        userOperationsButtonPanel.add(cancelButton);

        frame.add(userOperationsPanel, BorderLayout.NORTH);
        frame.add(userOperationsButtonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void open() {
        frame.setVisible(true);
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void sendEmail(String to, String id, String password) throws IOException, MessagingException {
        Properties config = ConfigReader.readConfig();
        String from = config.getProperty("email.from");
        String host = "smtp.gmail.com";
        String username = config.getProperty("email.username");
        String emailPassword = config.getProperty("email.password");

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, emailPassword);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("New Password Request");
        message.setText("Your ID: " + id + "\nYour new password: " + password);

        Transport.send(message);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ForgotScreen {
    private JFrame frame;

    public ForgotScreen() {
        //ForgotScreen sınıfının arayüzünü tasarladık.
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

        //sendButtonun aksiyonlarını ayarladık.
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = userMail.getText();
                String id = userID.getText();
                String password = generatePassword(); // Şifreyi oluşturun
                try {
                    sendEmail(email, id, password); // E-posta gönderin
                    JOptionPane.showMessageDialog(frame, "The new password has been sent to your e-mail address."); // Bilgilendirme mesajı
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error sending email: " + ex.getMessage());
                }
            }
        });

        //cancelButtonunun aksiyonunu ayarladık.
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
        // Şifre oluşturma logic
        return "123456"; // Evrensel bir şifre. Her ID ile açılacak şifredir.
    }

    //sendButtonuna basıldığında mail göndermek için gerekli işlemler.
    private void sendEmail(String to, String id, String password) throws IOException {
        Properties config = ConfigReader.readConfig();
        String from = config.getProperty("email.from"); // Kendi e-posta adresiniz
        String host = "smtp.gmail.com"; // Gmail SMTP sunucusu
        String username = config.getProperty("email.username"); // E-posta adresiniz
        String emailPassword = config.getProperty("email.password"); // E-posta adresinizin şifresi (tercihen uygulama şifresi). Bu şifre "Daha Az Güvenli Uygulamalar" yazılarak bulundu.

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, emailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("New Password Request");
            // Gönderilen E-posta metninde kullanıcı ID'sini ve yeni şifreyi belirtiyoruz
            message.setText("Your ID: " + id + "\nYour new password: " + password);

            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


}



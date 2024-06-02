import javax.swing.*;
import java.awt.*;


public class HomeScreen {
    private JFrame frame;


    public HomeScreen() {
        // JFrame oluşturuluyor ve temel özellikleri ayarlanıyor
        frame = new JFrame("LibraTech - HomeScreen");
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3)); // Ekranı üç panele bölüyoruz
        String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
        frame.setIconImage(new ImageIcon(iconPath).getImage());

        JPanel leftPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> bookListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(bookListModel);

        frame.setVisible(true);
    }

    public void open() {
        frame.setVisible(true);
    }


}



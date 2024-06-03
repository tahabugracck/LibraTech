import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen {
    private JFrame frame;

    public HomeScreen() {
        // JFrame oluşturuluyor ve temel özellikleri ayarlanıyor
        frame = new JFrame("LibraTech - HomeScreen");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3)); // Ekranı üç panele bölüyoruz
        String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
        frame.setIconImage(new ImageIcon(iconPath).getImage());

        // Sol panel ve içeriği oluşturuluyor
        JPanel leftPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> bookListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(bookListModel);
        leftPanel.add(new JScrollPane(fileList), BorderLayout.CENTER);

        JPanel leftButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton addBookButon = new JButton("Add Book");
        JButton updateBookButton = new JButton("Update Book");
        leftButtonPanel.add(addBookButon);
        leftButtonPanel.add(updateBookButton);
        leftPanel.add(leftButtonPanel, BorderLayout.SOUTH); // Buton panelini sol panelin altına ekliyoruz

        addBookButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        updateBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Orta panel oluşturuluyor
        JPanel midPanel = new JPanel(new BorderLayout());
        JPanel midButtonPanel = new JPanel(new GridLayout(1,2));
        JButton barrowBookButton = new JButton("Barrow");
        JButton returnBookButton = new JButton("Return");
        midButtonPanel.add(barrowBookButton);
        midButtonPanel.add(returnBookButton);
        midPanel.add(midButtonPanel, BorderLayout.SOUTH);

        barrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        // Sağ panel oluşturuluyor
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel rightButtonPanel = new JPanel(new GridLayout(1,2));
        JButton editProfileButton = new JButton("Edit");
        JButton saveProfilButton = new JButton("Save");
        rightButtonPanel.add(editProfileButton);
        rightButtonPanel.add(saveProfilButton);
        rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);

        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        saveProfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // Paneller çerçeveye ekleniyor
        frame.add(leftPanel);
        frame.add(midPanel);
        frame.add(rightPanel);

        // Çerçeve görünür hale getiriliyor
        frame.setVisible(true);
    }

    public void open() {
        frame.setVisible(true);
    }


}
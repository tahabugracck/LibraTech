import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeScreen {
    private JFrame frame;
    private DefaultListModel<String> bookListModel;
    private JTextArea bookDetailsArea;
    private User authenticatedUser;

    public HomeScreen(User user) {
        this.authenticatedUser = user;

        // JFrame oluşturuluyor ve temel özellikleri ayarlanıyor
        frame = new JFrame("LibraTech - HomeScreen");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Ekranı BorderLayout olarak ayarlıyoruz
        String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
        frame.setIconImage(new ImageIcon(iconPath).getImage());

        // Üst panel ve arama kutusu oluşturuluyor
        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(30);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Sol panel ve içeriği oluşturuluyor
        JPanel leftPanel = new JPanel(new BorderLayout());
        bookListModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(bookListModel);
        leftPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        // Orta panel oluşturuluyor
        JPanel midPanel = new JPanel(new BorderLayout());
        bookDetailsArea = new JTextArea();
        bookDetailsArea.setEditable(false); // Metin alanı sadece okunabilir
        midPanel.add(new JScrollPane(bookDetailsArea), BorderLayout.CENTER);

        // Orta panelde butonlar oluşturuluyor
        JPanel midButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton borrowBookButton = new JButton("Borrow");
        JButton returnBookButton = new JButton("Return");
        midButtonPanel.add(borrowBookButton);
        midButtonPanel.add(returnBookButton);
        midPanel.add(midButtonPanel, BorderLayout.SOUTH);

        // Sağ panel oluşturuluyor
        JPanel rightPanel = new JPanel(new BorderLayout());
        JTextArea userProfileArea = new JTextArea(authenticatedUser.getIDname());
        userProfileArea.setEditable(false);
        rightPanel.add(new JScrollPane(userProfileArea), BorderLayout.CENTER);

        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton editProfileButton = new JButton("Edit");
        JButton saveProfileButton = new JButton("Save");
        rightButtonPanel.add(editProfileButton);
        rightButtonPanel.add(saveProfileButton);
        rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);

        // Kitap arama butonu için action listener ekleniyor
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                if (!query.trim().isEmpty()) {
                    List<Book> books = GoogleBooksAPI.searchBooks(query);
                    bookListModel.clear();
                    for (Book book : books) {
                        bookListModel.addElement(book.getTitle() + " by " + book.getAuthor());
                    }
                }
            }
        });

        // Sol paneldeki kitap listesinde seçim değişikliklerini dinlemek için bir listener ekleniyor
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = bookList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedBook = bookListModel.getElementAt(selectedIndex);
                        bookDetailsArea.setText("Book Details:\n" + selectedBook);
                    } else {
                        bookDetailsArea.setText("");
                    }
                }
            }
        });

        // Ödünç alma butonu için action listener ekleniyor
        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = bookList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String bookName = bookListModel.getElementAt(selectedIndex);
                    JOptionPane.showMessageDialog(frame, "You borrowed: " + bookName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a book to borrow.");
                }
            }
        });

        // İade etme butonu için action listener ekleniyor
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = bookList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String bookName = bookListModel.getElementAt(selectedIndex);
                    JOptionPane.showMessageDialog(frame, "You returned: " + bookName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a book to return.");
                }
            }
        });

        // Profil düzenleme butonu için action listener ekleniyor
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userProfileArea.setEditable(true);
            }
        });

        // Profil kaydetme butonu için action listener ekleniyor
        saveProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userProfileArea.setEditable(false);
                // You can add code here to save the updated profile information
            }
        });

        // Paneller çerçeveye ekleniyor
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(midPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);

        // Çerçeve görünür hale getiriliyor
        frame.setVisible(true);
    }

    public void open() {
        frame.setVisible(true);
    }
}

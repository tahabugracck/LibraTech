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
    private JTextField userIDField;
    private JPasswordField passwordField;
    private UserManager userManager;
    private User currentUser;
    private Library library;

    public HomeScreen(User currentUser, Library library) {
        this.currentUser = currentUser;
        this.library = library;
        this.userManager = new UserManager();

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
        bookListModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(bookListModel);
        leftPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        // Kitapları yükle
        for (Book book : library.getAllBooks()) {
            bookListModel.addElement(book.toString());
        }

        // Sol panelde butonlar oluşturuluyor
        JPanel leftButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton addBookButton = new JButton("Add Book");
        JButton updateBookButton = new JButton("Update Book");
        leftButtonPanel.add(addBookButton);
        leftButtonPanel.add(updateBookButton);
        leftPanel.add(leftButtonPanel, BorderLayout.SOUTH); // Buton panelini sol panelin altına ekliyoruz

        // Kitap ekleme butonu için action listener ekleniyor
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newBookTitle = JOptionPane.showInputDialog(frame, "Enter book title:");
                String newBookAuthor = JOptionPane.showInputDialog(frame, "Enter book author:");
                String newBookIsbn = JOptionPane.showInputDialog(frame, "Enter book ISBN:");
                if (newBookTitle != null && !newBookTitle.trim().isEmpty() &&
                        newBookAuthor != null && !newBookAuthor.trim().isEmpty() &&
                        newBookIsbn != null && !newBookIsbn.trim().isEmpty()) {
                    Book newBook = new Book(newBookTitle, newBookAuthor, newBookIsbn);
                    library.addBook(newBook);
                    bookListModel.addElement(newBook.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "All fields are required.");
                }
            }
        });

        // Sol panelde kitap listesi için dinleyici ekleniyor
        bookList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedBookTitle = bookList.getSelectedValue();
                    if (selectedBookTitle != null) {
                        for (Book book : library.getAllBooks()) {
                            if (book.toString().equals(selectedBookTitle)) {
                                bookDetailsArea.setText("Title: " + book.getTitle() +
                                        "\nAuthor: " + book.getAuthor() +
                                        "\nISBN: " + book.getIsbn());
                                break;
                            }
                        }
                    }
                }
            }
        });

        // Orta panel ve içeriği oluşturuluyor
        JPanel midPanel = new JPanel(new BorderLayout());
        bookDetailsArea = new JTextArea();
        bookDetailsArea.setEditable(false);
        midPanel.add(new JScrollPane(bookDetailsArea), BorderLayout.CENTER);

        // Orta panelde butonlar oluşturuluyor
        JPanel midButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        midButtonPanel.add(borrowButton);
        midButtonPanel.add(returnButton);
        midPanel.add(midButtonPanel, BorderLayout.SOUTH);

        // Sağ panel ve içeriği oluşturuluyor
        JPanel rightPanel = new JPanel(new GridLayout(3, 2));
        rightPanel.add(new JLabel("User ID"));
        userIDField = new JTextField(currentUser.getIDname());
        userIDField.setEditable(false);
        rightPanel.add(userIDField);

        rightPanel.add(new JLabel("Password"));
        passwordField = new JPasswordField(currentUser.getPassword());
        rightPanel.add(passwordField);

        JButton saveButton = new JButton("Save");
        rightPanel.add(saveButton);

        // Sağ panelde save butonu için action listener ekleniyor
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(passwordField.getPassword());
                currentUser = new User(currentUser.getIDname(), newPassword);
                userManager.saveUsersToFile();
                JOptionPane.showMessageDialog(frame, "Password updated successfully.");
            }
        });

        // Kitap ödünç alma butonu için action listener ekleniyor
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBookTitle = bookList.getSelectedValue();
                if (selectedBookTitle != null) {
                    for (Book book : library.getAllBooks()) {
                        if (book.toString().equals(selectedBookTitle)) {
                            library.borrowBook(currentUser.getIDname(), book);
                            JOptionPane.showMessageDialog(frame, "Book borrowed successfully.");
                            updateButtons();
                            break;
                        }
                    }
                }
            }
        });

        // Kitap iade etme butonu için action listener ekleniyor
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBookTitle = bookList.getSelectedValue();
                if (selectedBookTitle != null) {
                    for (Book book : library.getAllBooks()) {
                        if (book.toString().equals(selectedBookTitle)) {
                            library.returnBook(currentUser.getIDname(), book);
                            JOptionPane.showMessageDialog(frame, "Book returned successfully.");
                            updateButtons();
                            break;
                        }
                    }
                }
            }
        });

        // Butonları güncelleyen metod
        updateButtons();

        // Panelleri frame'e ekliyoruz
        frame.add(leftPanel);
        frame.add(midPanel);
        frame.add(rightPanel);

        // Frame'i görünür yapıyoruz
        frame.setVisible(true);
    }

    private void updateButtons() {
        List<Book> borrowedBooks = library.getUserBooks(currentUser.getIDname());
        for (Component c : frame.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                String buttonText = button.getText();
                if (buttonText.equals("Borrow Book")) {
                    button.setEnabled(true);
                    for (Book book : borrowedBooks) {
                        if (book.toString().equals(bookDetailsArea.getText())) {
                            button.setEnabled(false);
                            break;
                        }
                    }
                } else if (buttonText.equals("Return Book")) {
                    button.setEnabled(false);
                    for (Book book : borrowedBooks) {
                        if (book.toString().equals(bookDetailsArea.getText())) {
                            button.setEnabled(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void open() {
        frame.setVisible(true);
    }
}

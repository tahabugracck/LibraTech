import javax.swing.*;
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

        frame = new JFrame("LibraTech - HomeScreen");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        String iconPath = "C:\\Users\\tahab\\IdeaProjects\\LibraTech\\src\\icon\\LibraTech_icon.png";
        frame.setIconImage(new ImageIcon(iconPath).getImage());

        JPanel topPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(30);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new BorderLayout());
        bookListModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(bookListModel);
        leftPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        JPanel midPanel = new JPanel(new BorderLayout());
        bookDetailsArea = new JTextArea();
        bookDetailsArea.setEditable(false);
        midPanel.add(new JScrollPane(bookDetailsArea), BorderLayout.CENTER);

        JPanel midButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton borrowBookButton = new JButton("Borrow");
        JButton returnBookButton = new JButton("Return");
        midButtonPanel.add(borrowBookButton);
        midButtonPanel.add(returnBookButton);
        midPanel.add(midButtonPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        JTextArea userProfileArea = new JTextArea();
        userProfileArea.setEditable(false);
        rightPanel.add(new JScrollPane(userProfileArea), BorderLayout.CENTER);
        userProfileArea.setText(user.toString());

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(midPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                List<Book> books = GoogleBooksAPI.searchBooks(query);
                bookListModel.clear();
                for (Book book : books) {
                    bookListModel.addElement(book.getTitle());
                }
            }
        });

        bookList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedTitle = bookList.getSelectedValue();
                if (selectedTitle != null) {
                    for (Book book : GoogleBooksAPI.searchBooks(selectedTitle)) {
                        if (book.getTitle().equals(selectedTitle)) {
                            bookDetailsArea.setText("Title: " + book.getTitle() + "\nAuthor: " + book.getAuthor() + "\nISBN: " + book.getIsbn());
                            break;
                        }
                    }
                }
            }
        });

        borrowBookButton.addActionListener(e -> {
            String selectedTitle = bookList.getSelectedValue();
            if (selectedTitle != null) {
                JOptionPane.showMessageDialog(frame, "You have borrowed: " + selectedTitle);
            } else {
                JOptionPane.showMessageDialog(frame, "No book selected.");
            }
        });

        returnBookButton.addActionListener(e -> {
            String selectedTitle = bookList.getSelectedValue();
            if (selectedTitle != null) {
                JOptionPane.showMessageDialog(frame, "You have returned: " + selectedTitle);
            } else {
                JOptionPane.showMessageDialog(frame, "No book selected.");
            }
        });

        frame.setVisible(true);
    }
}

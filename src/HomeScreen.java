import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen {
    private JFrame frame;
    private DefaultListModel<String> bookListModel;
    private JTextArea bookDetailsArea;

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
        bookListModel = new DefaultListModel<>();
        JList<String> bookList = new JList<>(bookListModel);
        leftPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        // Başlangıçta 5 kitap ekleniyor
        for (int i = 1; i <= 5; i++) {
            bookListModel.addElement("Book" + i);
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
                String newBook = JOptionPane.showInputDialog(frame, "Enter book name:");
                if (newBook != null && !newBook.trim().isEmpty()) {
                    bookListModel.addElement(newBook);
                }
            }
        });

        // Kitap güncelleme butonu için action listener ekleniyor
        updateBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = bookList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String updatedBook = JOptionPane.showInputDialog(frame, "Update book name:", bookListModel.get(selectedIndex));
                    if (updatedBook != null && !updatedBook.trim().isEmpty()) {
                        bookListModel.set(selectedIndex, updatedBook);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a book to update.");
                }
            }
        });

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

        // Sağ panel oluşturuluyor
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2));
        JButton editProfileButton = new JButton("Edit");
        JButton saveProfileButton = new JButton("Save");
        rightButtonPanel.add(editProfileButton);
        rightButtonPanel.add(saveProfileButton);
        rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);

        // Profil düzenleme butonu için action listener ekleniyor
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Profil düzenleme işlemleri burada yapılacak
            }
        });

        // Profil kaydetme butonu için action listener ekleniyor
        saveProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Profil kaydetme işlemleri burada yapılacak
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

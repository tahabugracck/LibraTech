import javax.swing.*;

public class App{
    public static void main(String[] args) {
        try {
            new LoginScreen();
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null, "Error" + exception);
        }
    }
}
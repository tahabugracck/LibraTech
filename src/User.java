import java.io.Serializable;

public class User implements Serializable {
    private String IDname;
    private String password;

    public User(String IDname, String password) {
        this.IDname = IDname;
        this.password = password;
    }

    public String getIDname() {
        return IDname;
    }

    public String getPassword() {
        return password;
    }
}

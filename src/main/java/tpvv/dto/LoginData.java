package tpvv.dto;

public class LoginData {
    private String email;
    private String contrasenya;

    public String getEmail() {
        return email;
    }

    // Corrige para usar 'this.email = eMail;'
    public void setEmail(String eMail) {
        this.email = eMail;
    }

    // Corrige el getter, si quieres llamarlo getPassword().
    // Devuelve 'contrasenya'.
    public String getPassword() {
        return contrasenya;
    }

    // Corrige para usar 'this.contrasenya = password;'
    public void setPassword(String password) {
        this.contrasenya = password;
    }
}


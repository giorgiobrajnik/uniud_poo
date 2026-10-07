package it.universita.esse3.hashing;

// CONTROESEMPIO: l'email è mutabile e determina equals e hashCode
public class StudenteEmail {
    private final String nome;
    private String email;

    public StudenteEmail(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String nome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StudenteEmail)) {
            return false;
        }
        StudenteEmail that = (StudenteEmail) other;
        return java.util.Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(email);
    }
}

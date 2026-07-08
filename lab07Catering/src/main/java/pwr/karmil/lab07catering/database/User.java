package pwr.karmil.lab07catering.database;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "catering")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Schema(example = "jacek002", description = "Nazwa użytkownika")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Schema(example = "masło123", description = "Hasło użytkownika")
    @Column(name = "haslo", nullable = false)
    private String haslo;

    @Schema(example = "user", description = "Rola, może być albo zwykłym userem albo adminem")
    @Column(name = "role", length = 50)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
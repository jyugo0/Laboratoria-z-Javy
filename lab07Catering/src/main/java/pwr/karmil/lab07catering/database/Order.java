package pwr.karmil.lab07catering.database;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders", schema = "catering")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @Schema(example = "NIEOPŁACONE", description = "Stan zamówienia")
    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Schema(example = "46", description = "Totalny koszt zamówienia")
    @Column(name = "cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Schema(example = "2", description = "Id zamawiającego użytkownika")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(example = "21.05.2026 16:00:00", description = "Data na którą składanae jest zamówienie")
    @Column(name = "date")
    private Instant date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String userStr = (user != null) ? user.getUsername() : "Brak";
        return "ID: " + id + ", User: " + userStr + ", Stan: " + state + ", Koszt: " + cost + " zł, Data: " + date;
    }

}
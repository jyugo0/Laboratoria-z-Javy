package pwr.karmil.lab07catering.database;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
@Table(name = "menu", schema = "catering")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Id generowane przez baze")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Schema(example = "Sałatka lazur", description = "Nazwa pozycji w menu")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(example = "21", description = "Cena pozycji w menu w złotówkach")
    @Column(name = "price", nullable = false)
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - " + price + " zł";
    }

}
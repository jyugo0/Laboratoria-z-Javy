package pwr.karmil.lab07catering.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ElementsSelection {
    @Schema(example = "Frytki", description = "Nazwa pozycji z menu")
    private String menuName;

    @Schema(example = "2", description = "Ilość")
    private Integer quantity;

    public ElementsSelection() {}
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
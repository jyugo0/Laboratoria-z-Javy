package pwr.karmil.lab07catering.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

public class OrderRequest {
    @Schema(example = "user")
    private String username;

    @Schema(example = "2026-06-01T16:00:00Z")
    private Instant deliveryDate;

    private List<ElementsSelection> items;

    public OrderRequest() {}
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Instant getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Instant deliveryDate) { this.deliveryDate = deliveryDate; }
    public List<ElementsSelection> getItems() { return items; }
    public void setItems(List<ElementsSelection> items) { this.items = items; }
}
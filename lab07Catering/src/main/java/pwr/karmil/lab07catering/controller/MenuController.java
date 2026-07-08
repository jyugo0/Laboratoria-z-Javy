package pwr.karmil.lab07catering.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.karmil.lab07catering.database.Menu;
import pwr.karmil.lab07catering.database.MenuRepository;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Tag(name = "Karta Menu", description = "Zarządzanie pozycjami w ofercie kateringu")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Operation(summary = "Pobierz całe menu")
    @GetMapping
    public List<Menu> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @Operation(summary = "Pobierz pojednyńczą pozycje")
    @GetMapping("/{name}")
    public ResponseEntity<Menu> getMenuItemByName(@PathVariable String name) {
        return menuRepository.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Dodaj pozycje do menu")
    @PostMapping
    public Menu createMenuItem(@RequestBody Menu menu) {
        return menuRepository.save(menu);
    }

    @Operation(summary = "Zaaktualizuj cene produktu")
    @PutMapping("/{name}/update")
    public ResponseEntity<Menu> updateMenuItem(@PathVariable String name, @RequestParam Integer price) {
        return menuRepository.findByName(name).map(menu -> {
            menu.setPrice(price);
            Menu updatedMenu = menuRepository.save(menu);
            return ResponseEntity.ok(updatedMenu);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Usuń pozycje z manu")
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable String name) {
        return menuRepository.findByName(name).map(menu -> {
            menuRepository.delete(menu);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

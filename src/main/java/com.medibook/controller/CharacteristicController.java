package com.medibook.controller;

import com.medibook.entities.Characteristic;
import com.medibook.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/characteristics")
public class CharacteristicController {

    private final CharacteristicService characteristicService;

    @Autowired
    public CharacteristicController(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCharacteristics() {
        return new ResponseEntity<>(characteristicService.getAllCharacteristics(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCharacteristicById(@PathVariable Long id) {
        Characteristic characteristic = characteristicService.getCharacteristicById(id);
        if (characteristic == null) {
            return new ResponseEntity<>("Characteristic not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(characteristic, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Characteristic> getCharacteristicByName(@PathVariable String name) {
        Characteristic characteristic = characteristicService.getCharacteristicByName(name);
        if (characteristic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(characteristic, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCharacteristic(@RequestBody Characteristic characteristic) {
        Characteristic createdCharacteristic = characteristicService.createCharacteristic(characteristic);
        return new ResponseEntity<>(createdCharacteristic, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCharacteristic(@PathVariable Long id) {
        characteristicService.deleteCharacteristic(id);
        return new ResponseEntity<>("Caracteristica eliminada correctamente", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Characteristic> updateCharacteristic(
            @PathVariable Long id,
            @RequestBody Characteristic updatedCharacteristic
    ) {
        Characteristic updated = characteristicService.updateCharacteristic(id, updatedCharacteristic);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}

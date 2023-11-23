package com.medibook.controller;

import com.medibook.entities.Doctor;
import com.medibook.entities.Specialty;
import com.medibook.exceptions.ResourceNotFoundException;
import com.medibook.service.DoctorService;
import com.medibook.service.SpecialtyService;
import com.medibook.util.ValidatorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/specialities")
public class SpecialtyController {
    private SpecialtyService specialtyService;
    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addSpecialty(@RequestBody Specialty specialty) {

        specialtyService.addSpecialty(specialty);

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeSpecialty(@RequestBody Specialty specialty) throws ResourceNotFoundException {

        specialtyService.editSpecialty(specialty);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/listspecialities")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Specialty>> listSpe() throws  ResourceNotFoundException{

        List<Specialty> specialties = specialtyService.listSpecialities();

        return ResponseEntity.ok(specialties);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Specialty>> searchById(@PathVariable String id) throws  ResourceNotFoundException{
        Optional<Specialty> specialty = specialtyService.searchById(Long.parseLong(id));
        if(ValidatorClass.isNumeric(id)){

            return ResponseEntity.ok (specialty);

        } else {

            throw  new ResourceNotFoundException("El Id debe se numérico");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String id) throws ResourceNotFoundException {

        if(ValidatorClass.isNumeric(id)){

            specialtyService.deleteSpecialty(Long.parseLong(id));

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");

        } else {

            throw  new ResourceNotFoundException("El Id debe se numérico");
        }
    }

}

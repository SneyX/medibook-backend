package com.medibook.service;

import com.medibook.entities.Doctor;
import com.medibook.entities.Specialty;
import com.medibook.exceptions.ResourceNotFoundException;
import com.medibook.repository.DoctorRepository;
import com.medibook.repository.SpecialtyRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    private final static Logger logger = Logger.getLogger(SpecialtyService.class);

    //Método para agregar una especialidad
    public Specialty registerEspecialidad(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }


    public void addSpecialty(Specialty specialty) {

        Specialty specialty1 = registerEspecialidad(specialty);

        logger.info("Se ha agregado la especialidad con id: " + specialty1.getId());
    }

    public void editSpecialty(Specialty specialty) throws ResourceNotFoundException {

        String msg = "";

        Optional<Specialty> specialty1 = specialtyRepository.findById(specialty.getId());

        if (specialty1.isEmpty()) {

            msg = "No se puede modificar la especialidad porque el id no existe ";

            throw new ResourceNotFoundException(msg);
        } else {


            registerEspecialidad(specialty);
            logger.info("Se modifica el medico con id: " + specialty1.get().getId());
        }
    }

    //Método para visualizar todas las especialidades

    public List<Specialty> listSpecialities() throws ResourceNotFoundException {

        List<Specialty> specialties = specialtyRepository.findAll();

        logger.info("Se consulta todas las especialidades");
        return specialties;
    }

    //Método para eliminar especialidad por Id.

    public void deleteSpecialty(Long id) throws ResourceNotFoundException {

        if (specialtyRepository.findById(id).isEmpty())

            throw new ResourceNotFoundException("No existe especialidad con id: " + id);

        specialtyRepository.deleteById(id);

        logger.info("Se elimina la especialidad con Id: " + id);
    }


    //Método para buscar especialidad por Id

    public Optional<Specialty> searchById(Long id) throws  ResourceNotFoundException{

        Optional<Specialty> specialty =  specialtyRepository.findById(id);

        if(specialty.isPresent()){

            logger.info("Se consulta especialidad por Id: " + specialty.get().getId());}
        else{

            throw  new ResourceNotFoundException("No existe la especialidad con ese id: " + id);
        }
        return specialty;
    }
}

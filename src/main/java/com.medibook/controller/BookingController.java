package com.medibook.controller;

import com.medibook.entities.Booking;
import com.medibook.entities.Doctor;
import com.medibook.entities.Room;
import com.medibook.exceptions.ResourceNotFoundException;
import com.medibook.service.BookingService;
import com.medibook.service.DoctorService;
import com.medibook.service.RoomService;
import com.medibook.util.ValidatorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<Booking> registerBooking(Booking booking) throws ResourceNotFoundException {
        ResponseEntity<Booking> response;
        Room room = booking.getRoom();
        Doctor doctor = booking.getDoctor();
        try {
            if (roomService.searchById(room.getId()).isPresent() &&
                    doctorService.searchById(doctor.getId()).isPresent()) {
                response = ResponseEntity.ok(bookingService.registerBooking(booking));
                return response;
            } else if (roomService.searchById(room.getId()).isPresent()) {
                throw new ResourceNotFoundException("La Sala no está registrada");
            } else if (doctorService.searchById(doctor.getId()).isPresent()) {
                throw new ResourceNotFoundException("El Doctor no está registrado");
            } else {
                throw new ResourceNotFoundException("La sala y el doctor no están registrados");
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error al procesar la reserva");
        }
    }

    @PutMapping()
    public ResponseEntity<?> editBooking(@RequestBody Booking booking) throws ResourceNotFoundException {

        bookingService.editBooking(booking);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/listbookings")
    public ResponseEntity<List<Booking>> listBookings() throws  ResourceNotFoundException{

        List<Booking> bookings = bookingService.listBookings();

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Booking>> searchBookingById(@PathVariable String id) throws  ResourceNotFoundException{
        Optional<Booking> booking = bookingService.searchBookingById(Long.parseLong(id));
        if(ValidatorClass.isNumeric(id)){

            return ResponseEntity.ok (booking);

        } else {

            throw  new ResourceNotFoundException("El Id debe se numérico");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable String id) throws ResourceNotFoundException {

        if(ValidatorClass.isNumeric(id)){

            bookingService.deleteBooking(Long.parseLong(id));

            return ResponseEntity.status(HttpStatus.OK).body("Reserva eliminada");

        } else {

            throw  new ResourceNotFoundException("El Id debe se numérico");
        }
    }

}

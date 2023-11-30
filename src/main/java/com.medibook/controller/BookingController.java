package com.medibook.controller;

import com.medibook.entities.Booking;
import com.medibook.entities.Room;
import com.medibook.entities.UserEntity;
import com.medibook.exceptions.ResourceNotFoundException;
import com.medibook.repository.BookingRepository;
import com.medibook.service.BookingService;
import com.medibook.service.RoomService;
import com.medibook.service.UserEntityService;
import com.medibook.util.ValidatorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Booking> registerBooking(@RequestBody Booking booking) throws ResourceNotFoundException {
        Room room = booking.getRoom();
        UserEntity userEntity = booking.getUserEntity();
        List<Booking> bookings = bookingRepository.findAll();

        if (!roomService.searchById(room.getId()).isPresent()) {
            throw new ResourceNotFoundException("La Sala no está registrada");
        }

        if (!userEntityService.searchById(userEntity.getId()).isPresent()) {
            throw new ResourceNotFoundException("El usuario no está registrado");
        }

        try {
            List<Booking> bookingDate = bookings.stream().filter(it -> booking.getDate().equals(it.getDate())).collect(Collectors.toList());
            List<Booking> bookingShift = bookingDate.stream().filter(it -> booking.getShift().equals(it.getShift())).collect(Collectors.toList());
            if (bookingShift.isEmpty()){
                Booking registeredBooking = bookingService.registerBooking(booking);
                return ResponseEntity.ok(registeredBooking);
            } else{
                throw new ResourceNotFoundException("La Sala en esa fecha y turno se encuentra reservada");
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error al procesar la reserva");
        }
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> editBooking(@RequestBody Booking booking) throws ResourceNotFoundException {

        bookingService.editBooking(booking);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/listbookings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Booking>> listBookings() throws  ResourceNotFoundException{

        List<Booking> bookings = bookingService.listBookings();

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listUserbookings/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Booking>> listUserBookings(@PathVariable String id) throws  ResourceNotFoundException{

        List<Booking> bookings = bookingService.listBookingsUser((Long.parseLong(id)));

        return ResponseEntity.ok(bookings);
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Optional<Booking>> searchBookingById(@PathVariable String id) throws  ResourceNotFoundException{
        Optional<Booking> booking = bookingService.searchBookingById(Long.parseLong(id));
        if(ValidatorClass.isNumeric(id)){

            return ResponseEntity.ok (booking);

        } else {

            throw  new ResourceNotFoundException("El Id debe se numérico");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<String> deleteBooking(@PathVariable String id) throws ResourceNotFoundException {

        if(ValidatorClass.isNumeric(id)){

            bookingService.deleteBooking(Long.parseLong(id));

            return ResponseEntity.status(HttpStatus.OK).body("Reserva eliminada");

        } else {

            throw  new ResourceNotFoundException("El Id debe se numérico");
        }
    }

}

package com.medibook.service;

import com.medibook.entities.Booking;
import com.medibook.exceptions.ResourceNotFoundException;
import com.medibook.repository.BookingRepository;
import com.medibook.repository.RoomRepository;
import com.medibook.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    private final static Logger logger = Logger.getLogger(BookingService.class);

    //METODO PARA AGREGAR UNA RESERVA
    public Booking registerBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    //METODO PARA EDITAR/ACTUALIZAR RESERVA

    public void editBooking(Booking booking) throws ResourceNotFoundException {

        String msg = "";

        Optional<Booking> booking1 = bookingRepository.findById(booking.getId());

        if (booking1.isEmpty()) {

            msg = "No se puede modificar la reserva porque el id no existe ";

            throw new ResourceNotFoundException(msg);
        } else {


            registerBooking(booking);
            logger.info("Se modifica la reserva con id: " + booking1.get().getId());
        }
    }

    //METODO PARA VISUALIZAR TODAS LAS RESERVAS

    public List<Booking> listBookings() throws ResourceNotFoundException {

        List<Booking> bookings = bookingRepository.findAll();

        logger.info("Se consultan todas las reservas");
        return bookings;
    }

    public List<Booking> listBookingsUser(Long id) throws ResourceNotFoundException {
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> bookingUser = bookings.stream().filter(it -> id.equals(it.getUserEntity().getId())).collect(Collectors.toList());
        List<Booking> bookingStatus = bookingUser.stream().filter(it -> it.isStatus()).collect(Collectors.toList());

        logger.info("Se consultan todas las reservas del usuario con id: " + id);
        return bookingStatus;
    }

    //METODO PARA BUSCAR RESERVA X ID

    public Optional<Booking> searchBookingById(Long id) throws  ResourceNotFoundException{

        Optional<Booking> booking =  bookingRepository.findById(id);

        if(booking.isPresent()){

            logger.info("Se consulta la reserva por Id: " + booking.get().getId());}
        else{

            throw  new ResourceNotFoundException("No existe una reserva con ese id: " + id);
        }
        return booking;
    }

    //METODO PARA ELIMINAR RESERVA X ID

    public void deleteBooking(Long id) throws ResourceNotFoundException {

        if (bookingRepository.findById(id).isEmpty())

            throw new ResourceNotFoundException("No existe la reserva con id: " + id);

        bookingRepository.deleteById(id);

        logger.info("Se elimina la reserva con Id: " + id);
    }

}
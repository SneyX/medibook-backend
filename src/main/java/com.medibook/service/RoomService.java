package com.medibook.service;
import com.medibook.entities.*;
import com.medibook.exceptions.ResourceNotFoundException;
import com.medibook.repository.BookingRepository;
import com.medibook.repository.RoomRepository;
import com.medibook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.sqm.CastType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CharacteristicService characteristicService;

    @Autowired
    private BookingService bookingService;

    private final static Logger logger = Logger.getLogger(RoomService.class);

    //Método para agregar una sala
    public Room registerRoom(Room room) {
        return roomRepository.save(room);
    }

    public void editRoom(Room room) throws ResourceNotFoundException {

        String msg = "";

        Optional<Room> room1 = roomRepository.findById(room.getId());

        if (room1.isEmpty()) {

            msg = "No se puede modificar la sala porque el id no existe ";

            throw new ResourceNotFoundException(msg);
        } else {


            registerRoom(room);
            logger.info("Se modifica la sala con id: " + room1.get().getId());
        }
    }

    //Método para visualizar todas las salas

    public List<Room> listRooms() throws ResourceNotFoundException {

        List<Room> rooms = roomRepository.findAll();

        logger.info("Se consulta todas las sala");
        return rooms;
    }

    //Método para eliminar sala por Id.

    public void deleteRoom(Long id) throws ResourceNotFoundException {

        List<UserEntity> userEntities = userRepository.findAll();
        List<Booking>bookings = bookingRepository.findAll();

        for (UserEntity userEntity: userEntities) {
            List<Room> rooms = userEntity.getRoomsFavorite();
            List<Room> roomsAux = new ArrayList();
            for (Room room1 : rooms) {
                if(!(room1.getId().equals(id))){

                   roomsAux.add(room1);
                }
            }
            userEntity.setRoomsFavorite(roomsAux);
        }


        for (Booking booking : bookings) {
            Room room = booking.getRoom();
            Room roomaux;
                if(room.getId().equals(id)){
                    bookingService.deleteBooking(booking.getId());
                }
        }

        if (roomRepository.findById(id).isEmpty())

            throw new ResourceNotFoundException("No existe sala con id: " + id);

        roomRepository.deleteById(id);

        logger.info("Se elimina la sala con Id: " + id);
    }


    //Método para buscar sala por nombre

    public Optional<Room> searchByName(String name) throws ResourceNotFoundException {
        Optional<Room> room = roomRepository.findByName(name);


        if (room.isPresent()) {

            logger.info("Se consulta sala por nombre: " + room.get().getName());

        } else{

            throw new ResourceNotFoundException("No existe el nombre de la sala que esta buscando");
        }
        return room;

    }


    //Método para buscar sala por Id

    public Optional<Room> searchById(Long id) throws  ResourceNotFoundException{

        Optional<Room> room =  roomRepository.findId(id);

        if(room.isPresent()){

            logger.info("Se consulta sala por Id: " + room.get().getId());}
        else{

            throw  new ResourceNotFoundException("No existe la sala con ese id: " + id);
        }
        return room;
    }

    @Transactional
    public void saveImageRoom(Room room){
        roomRepository.save(room);
    }

}

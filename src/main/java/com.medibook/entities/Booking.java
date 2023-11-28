package com.medibook.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Date; //día
    private String shift; //turno

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false) //relación con user
    private UserEntity userEntity;

    public Booking() {
    }

    public Booking(String date, String shift, Room room, UserEntity userEntity) {
        Date = date;
        this.shift = shift;
        this.room = room;
        this.userEntity = userEntity;
    }

    public Booking(Long id, String date, String shift, Room room, UserEntity userEntity) {
        this.id = id;
        Date = date;
        this.shift = shift;
        this.room = room;
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
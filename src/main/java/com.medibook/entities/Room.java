package com.medibook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;


    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private Set<Image> images;

    @ManyToOne
    @JoinColumn(name = "typeroom_id",nullable = false)
    private Typeroom typeroom;

    @ManyToMany
    @JoinTable(
        name = "room_characteristic",
        joinColumns = @JoinColumn(name = "room_id", nullable = true),
        inverseJoinColumns = @JoinColumn(name = "characteristic_id", nullable = true)
    )
    private Set<Characteristic> characteristics = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roomsFavorite")
    private Set<UserEntity> userEntities;

    public Room() {
    }

    public Room(Long id, String name, String description, Set<Booking> bookings, Set<Image> images, Typeroom typeroom, Set<Characteristic> characteristics,Set<UserEntity> userEntities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bookings = bookings;
        this.images = images;
        this.typeroom = typeroom;
        this.characteristics = characteristics;
        this.userEntities=userEntities;
    }

    public Room(String name, String description, Set<Booking> bookings, Set<Image> images, Typeroom typeroom, Set<Characteristic> characteristics,Set<UserEntity> userEntities) {
        this.name = name;
        this.description = description;
        this.bookings = bookings;
        this.images = images;
        this.typeroom = typeroom;
        this.characteristics = characteristics;
        this.userEntities=userEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Typeroom getTyperoom() {
        return typeroom;
    }

    public void setTyperoom(Typeroom typeroom) {
        this.typeroom = typeroom;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        return Objects.equals(id, other.id);
    }
}

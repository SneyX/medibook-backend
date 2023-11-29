package com.medibook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;



import java.util.*;


@Builder
@Entity
@Table(name = "user")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String lastname;

    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;



    @ManyToMany
    @JoinTable(
            name = "user_room",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = true)
    )
    private List<Room> roomsFavorite = new ArrayList<>();

   /* @ManyToMany
    @JoinTable(
            name = "review_room_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = true),
            inverseJoinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id",nullable = true)
    )
    private Set<Review> reviews = new HashSet<>();*/

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private Set<Booking> bookings;


    public UserEntity() {
    }


    public UserEntity(String name, String lastname, String username, String password, Role role,List<Room> roomsFavorite/*,Set<Review> reviews*/, Set<Booking> bookings) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.roomsFavorite = roomsFavorite;
        this.bookings = bookings;
        /*this.reviews = reviews;*/
    }


    public UserEntity(Long id, String name, String lastname, String username, String password, Role role,List<Room> roomsFavorite/*,Set<Review> reviews*/, Set<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.roomsFavorite = roomsFavorite;
        /*this.reviews = reviews;*/
        this.bookings = bookings;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Room> getRooms() {
        return roomsFavorite;
    }

    public void setRooms(List<Room> rooms) {
        this.roomsFavorite = rooms;
    }

    public List<Room> getRoomsFavorite() {
        return roomsFavorite;
    }

    public void setRoomsFavorite(List<Room> roomsFavorite) {
        this.roomsFavorite = roomsFavorite;
    }

   /* public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }*/

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserEntity other = (UserEntity) obj;
        return Objects.equals(username, other.username);
    }




}

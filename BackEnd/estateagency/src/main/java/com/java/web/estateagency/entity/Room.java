package com.java.web.estateagency.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;

    @Column(name="name",unique = true)
    private String name;

    private Float price;

    private String address;

    private String description;

    private String state;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String img;

    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

     @JsonBackReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Order> orders;

    @JsonBackReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<History> histories;

    @JsonBackReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @JsonBackReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @JsonBackReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Request> requests;
}

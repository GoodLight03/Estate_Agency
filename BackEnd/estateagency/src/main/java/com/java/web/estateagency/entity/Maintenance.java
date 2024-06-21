package com.java.web.estateagency.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "maintenance")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date date;

    private Long price;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private Room room;
}

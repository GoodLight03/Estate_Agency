package com.java.web.estateagency.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_user_id", referencedColumnName = "user_id")
    private User firstUserName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_user_id", referencedColumnName = "user_id")
    private User secondUserName;

   
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messageList;
}

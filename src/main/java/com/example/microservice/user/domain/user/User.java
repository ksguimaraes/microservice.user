package com.example.microservice.user.domain.user;

import java.util.Date;

import com.example.microservice.user.domain.address.Address;
import com.example.microservice.user.domain.user.status.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    @Id @Column(nullable = false) @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "birth_date")
    private Date birthDate;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, name = "created_at")
    private Date createdAt;

    @Column(nullable = false, name = "updated_at")
    private Date updatedAt;

    @Column(nullable = false, name = "deleted_at")
    private Date deletedAt;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @OneToOne
    @JoinColumn(name = "updated_by_user_id")
    private User updatedByUser;

    @OneToOne
    @JoinColumn(name = "deleted_by_user_id")
    private User deletedByUser;
    
}

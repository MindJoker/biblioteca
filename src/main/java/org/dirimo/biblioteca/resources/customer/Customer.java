package org.dirimo.biblioteca.resources.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.common.BaseEntity;
import org.dirimo.biblioteca.resources.reservation.Reservation;

import java.util.List;


@Entity
@Table(name = "Customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="FIRST_NAME",nullable = false)
    private String firstName;

    @Column(name="LAST_NAME",nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Reservation> reservation;



}

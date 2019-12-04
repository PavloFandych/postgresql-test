package org.total.postgre.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "employees")
@ToString
@NoArgsConstructor
@Setter
public class Employee {

    private long id;

    private String firstName;

    private String lastName;

    private String emailId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long getId() {
        return id;
    }

    @Column(name = "firstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }

    @Column(name = "emailAddress", nullable = false)
    public String getEmailId() {
        return emailId;
    }
}
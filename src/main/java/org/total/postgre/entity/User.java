package org.total.postgre.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "userId", columnNames = "userId"),
        @UniqueConstraint(name = "userName", columnNames = "userName") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class User implements Serializable {

    private long userId;

    private String userName;

    private String userPassword;

    private Set<Role> roles;

    private City city;

    private String userEmail;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "userId", nullable = false)
    public long getUserId() {
        return userId;
    }

    @Column(name = "userName", nullable = false)
    public String getUserName() {
        return userName;
    }

    @Column(name = "password", nullable = false)
    public String getUserPassword() {
        return userPassword;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "userId", nullable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "roleId", nullable = false) })
    public Set<Role> getRoles() {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        return roles;
    }

    @ManyToOne
    @JoinColumn(name = "cityId")
    public City getCity() {
        return city;
    }

    @Column(name = "userEmail", nullable = true)
    public String getUserEmail() {
        return userEmail;
    }
}
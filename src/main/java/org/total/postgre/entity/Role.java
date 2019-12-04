package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.total.postgre.entity.enums.RoleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(name = "roleId", columnNames = "roleId"),
        @UniqueConstraint(name = "roleType", columnNames = "roleType") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class Role implements Serializable {

    private long roleId;

    private RoleType roleType;

    private Set<User> users;

    private Set<Capability> capabilities;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "roleId", nullable = false)
    @JsonIgnore
    public long getRoleId() {
        return roleId;
    }

    @Column(name = "roleType", nullable = false)
    @Enumerated(EnumType.STRING)
    public RoleType getRoleType() {
        return roleType;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "roleId", nullable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "userId", nullable = false) })
    @JsonIgnore
    public Set<User> getUsers() {
        if (this.users == null) {
            this.users = new HashSet<>();
        }
        return users;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "role_capability", joinColumns = { @JoinColumn(name = "roleId", nullable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "capabilityId", nullable = false) })
    @JsonIgnore
    public Set<Capability> getCapabilities() {
        if (capabilities == null) {
            capabilities = new HashSet<>();
        }
        return capabilities;
    }
}
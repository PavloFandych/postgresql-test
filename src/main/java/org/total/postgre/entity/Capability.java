package org.total.postgre.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.total.postgre.entity.enums.CapabilityType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "capabilities", uniqueConstraints = { @UniqueConstraint(name = "capabilityId", columnNames = "capabilityId"),
        @UniqueConstraint(name = "capabilityType", columnNames = "capabilityType") })
@NoArgsConstructor
@Setter
@ToString
public final class Capability implements Serializable {

    private long capabilityId;

    private CapabilityType capabilityType;

    private Set<Role> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "capabilityId", nullable = false)
    public long getCapabilityId() {
        return capabilityId;
    }

    @Column(name = "capabilityType", nullable = false)
    @Enumerated(EnumType.STRING)
    public CapabilityType getCapabilityType() {
        return capabilityType;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "role_capability", joinColumns = {
            @JoinColumn(name = "capabilityId", nullable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "roleId", nullable = false) })
    public Set<Role> getRoles() {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        return roles;
    }
}
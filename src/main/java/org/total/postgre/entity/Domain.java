package org.total.postgre.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "domains", uniqueConstraints = { @UniqueConstraint(name = "domainName", columnNames = "domainName") })
@NoArgsConstructor
@Setter
public class Domain {

    private long domainId;

    private String domainName;

    private Domain parent;

    private List<Domain> children;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    public long getDomainId() {
        return domainId;
    }

    @Column(name = "domainName")
    public String getDomainName() {
        return domainName;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parentDomainId")
    public Domain getParent() {
        return parent;
    }

    @OneToMany(mappedBy = "parent")
    public List<Domain> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Domain domain = (Domain) o;
        return domainName.equals(domain.domainName) && Objects.equals(parent, domain.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainName, parent);
    }

    @Override
    public String toString() {
        return "Domain{" + "domainId=" + domainId + ", domainName='" + domainName + '\'' + '}';
    }
}
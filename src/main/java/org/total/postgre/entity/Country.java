package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.total.postgre.entity.enums.CountryCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "countries", uniqueConstraints = { @UniqueConstraint(name = "countryId", columnNames = "countryId"),
        @UniqueConstraint(name = "countryCode", columnNames = "countryCode") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class Country implements Serializable {

    private long countryId;

    private String countryName;

    private CountryCode countryCode;

    private Set<City> cities;

    private Set<Tournament> tournaments;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "countryId", nullable = false)
    public long getCountryId() {
        return countryId;
    }

    @Column(name = "countryName", nullable = false)
    public String getCountryName() {
        return countryName;
    }

    @Column(name = "countryCode", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    public CountryCode getCountryCode() {
        return countryCode;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @JsonIgnore
    public Set<City> getCities() {
        return cities;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @JsonIgnore
    public Set<Tournament> getTournaments() {
        return tournaments;
    }
}
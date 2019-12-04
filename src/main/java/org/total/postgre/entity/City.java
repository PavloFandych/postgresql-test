package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.total.postgre.entity.enums.CityCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "cities", uniqueConstraints = { @UniqueConstraint(name = "cityId", columnNames = "cityId"),
        @UniqueConstraint(name = "cityCode", columnNames = "cityCode"), })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class City implements Serializable {

    private long cityId;

    private String cityName;

    private CityCode cityCode;

    private Country country;

    private Set<Team> teams;

    private Set<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cityId", nullable = false)
    @JsonIgnore
    public long getCityId() {
        return cityId;
    }

    @Column(name = "cityName", nullable = false)
    public String getCityName() {
        return cityName;
    }

    @Column(name = "cityCode", nullable = false, length = 4)
    @Enumerated(EnumType.STRING)
    public CityCode getCityCode() {
        return cityCode;
    }

    @ManyToOne
    @JoinColumn(name = "countryId")
    @JsonIgnore
    public Country getCountry() {
        return country;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    @JsonIgnore
    public Set<Team> getTeams() {
        if (this.teams == null) {
            this.teams = new HashSet<>();
        }
        return teams;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }
}
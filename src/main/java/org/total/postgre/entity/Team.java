package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Pavlo.Fandych
 */

@Entity
@Table(name = "teams", uniqueConstraints = { @UniqueConstraint(name = "teamId", columnNames = "teamId"),
        @UniqueConstraint(name = "teamCode", columnNames = "teamCode") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class Team implements Serializable {

    private long teamId;

    private String teamName;

    private String teamCode;

    private City city;

    private Set<Result> resultsAsHost;

    private Set<Result> resultsAsGuest;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "teamId", nullable = false)
    @JsonIgnore
    public long getTeamId() {
        return teamId;
    }

    @Column(name = "teamName", nullable = false)
    public String getTeamName() {
        return teamName;
    }

    @Column(name = "teamCode", nullable = false, length = 6)
    public String getTeamCode() {
        return teamCode;
    }

    @ManyToOne
    @JoinColumn(name = "cityId", nullable = false)
    @JsonIgnore
    public City getCity() {
        return city;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hostTeam")
    @JsonIgnore
    public Set<Result> getResultsAsHost() {
        return resultsAsHost;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guestTeam")
    @JsonIgnore
    public Set<Result> getResultsAsGuest() {
        return resultsAsGuest;
    }
}
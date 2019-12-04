package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.total.postgre.entity.enums.TournamentCode;
import org.total.postgre.entity.enums.TournamentType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author kostya
 */

@Entity
@Table(name = "tournaments", uniqueConstraints = { @UniqueConstraint(name = "tournamentId", columnNames = "tournamentId"),
        @UniqueConstraint(name = "tournamentCode", columnNames = "tournamentCode") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class Tournament implements Serializable {

    private long tournamentId;

    private TournamentType tournamentType;

    private TournamentCode tournamentCode;

    private String tournamentName;

    private Country country;

    private Set<Result> results;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tournamentId", nullable = false)
    @JsonIgnore
    public long getTournamentId() {
        return tournamentId;
    }

    @Column(name = "tournamentType", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    public TournamentType getTournamentType() {
        return tournamentType;
    }

    @Column(name = "tournamentCode", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    public TournamentCode getTournamentCode() {
        return tournamentCode;
    }

    @Column(name = "tournamentName", nullable = false)
    public String getTournamentName() {
        return tournamentName;
    }

    @ManyToOne
    @JoinColumn(name = "countryId")
    @JsonIgnore
    public Country getCountry() {
        return country;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
    @JsonIgnore
    public Set<Result> getResults() {
        return results;
    }
}
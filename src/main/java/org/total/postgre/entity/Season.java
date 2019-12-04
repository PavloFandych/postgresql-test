package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.total.postgre.entity.enums.SeasonCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author kostya
 */

@Entity
@Table(name = "seasons", uniqueConstraints = { @UniqueConstraint(name = "seasonId", columnNames = "seasonId"),
        @UniqueConstraint(name = "seasonCode", columnNames = "seasonCode") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class Season implements Serializable {

    private long seasonId;

    private String seasonName;

    private SeasonCode seasonCode;

    private Set<Result> results;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "seasonId", nullable = false)
    @JsonIgnore
    public long getSeasonId() {
        return seasonId;
    }

    @Column(name = "seasonCode", nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    public SeasonCode getSeasonCode() {
        return seasonCode;
    }

    @Column(name = "seasonName", nullable = false)
    public String getSeasonName() {
        return seasonName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "season")
    @JsonIgnore
    public Set<Result> getResults() {
        return results;
    }
}


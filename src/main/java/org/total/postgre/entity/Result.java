package org.total.postgre.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author kostya
 */

@Entity
@Table(name = "results", uniqueConstraints = { @UniqueConstraint(name = "resultId", columnNames = "resultId"),
        @UniqueConstraint(name = "resultCode", columnNames = "resultCode") })
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
public final class Result implements Serializable {

    private long resultId;

    private String resultCode;

    private Tournament tournament;

    private Season season;

    private byte matchDay;

    private Team hostTeam;

    private Team guestTeam;

    private byte goalsByHost;

    private byte goalsByGuest;

    private Calendar date;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "resultId", nullable = false)
    @JsonIgnore
    public long getResultId() {
        return resultId;
    }

    @Column(name = "resultCode", nullable = false, length = 24)
    public String getResultCode() {
        return resultCode;
    }

    @ManyToOne
    @JoinColumn(name = "tournamentId", nullable = false)
    @JsonIgnore
    public Tournament getTournament() {
        return tournament;
    }

    @ManyToOne
    @JoinColumn(name = "seasonId", nullable = false)
    @JsonIgnore
    public Season getSeason() {
        return season;
    }

    @Column(name = "matchDay", nullable = false)
    @JsonIgnore
    public byte getMatchDay() {
        return matchDay;
    }

    @ManyToOne
    @JoinColumn(name = "hostTeamId", nullable = false)
    public Team getHostTeam() {
        return hostTeam;
    }

    @ManyToOne
    @JoinColumn(name = "guestTeamId", nullable = false)
    public Team getGuestTeam() {
        return guestTeam;
    }

    @Column(name = "goalsByHost")
    public byte getGoalsByHost() {
        return goalsByHost;
    }

    @Column(name = "goalsByGuest")
    public byte getGoalsByGuest() {
        return goalsByGuest;
    }

    @Column(name = "date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    public Calendar getDate() {
        return date;
    }

    public String calcScore() {
        return goalsByHost + ":" + goalsByGuest;
    }
}
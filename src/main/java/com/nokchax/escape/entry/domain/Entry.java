package com.nokchax.escape.entry.domain;

import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.point.domain.Point;
import com.nokchax.escape.problem.dto.SolvedProblemSummaryDto;
import com.nokchax.escape.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EntryId.class)
public class Entry implements Comparable<Entry> {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int score;
    private int hard;
    private int medium;
    private int easy;

    public Entry(SolvedProblemSummaryDto solvedProblemSummaryDto) {
        this.mission = new Mission(solvedProblemSummaryDto.getMissionId());
        this.user = new User(solvedProblemSummaryDto.getUserId());
        this.score = solvedProblemSummaryDto.evaluateScore();
        this.hard = solvedProblemSummaryDto.getHardCount();
        this.medium = solvedProblemSummaryDto.getMediumCount();
        this.easy = solvedProblemSummaryDto.getEasyCount();
    }

    public Entry(User user, Mission mission) {
        this.user = user;
        this.mission = mission;
    }

    public boolean isMissionComplete(int goalScore) {
        return this.score >= goalScore;
    }

    public EntryDto toDto() {
        return EntryDto.builder()
                .missionId(mission.getId())
                .userId(user.getId())
                .score(score)
                .hard(hard)
                .medium(medium)
                .easy(easy)
                .build();
    }

    public Point imposeFine() {
        return Point.builder()
                .user(user)
                .point(score - mission.getGoalScore())
                .dateTime(LocalDateTime.now())
                .description(Point.Description.FINES)
                .build();
    }

    @Override
    public String toString() {
        return "Entry{" +
                "mission=" + mission.getId() +
                ", user=" + user.getId() +
                ", score=" + score +
                ", hard=" + hard +
                ", medium=" + medium +
                ", easy=" + easy +
                '}';
    }

    @Override
    public int compareTo(Entry anotherEntry) {
        if (this.score != anotherEntry.score) {
            return Integer.compare(anotherEntry.score, this.score);
        } else if (this.hard != anotherEntry.hard) {
            return Integer.compare(anotherEntry.hard, this.hard);
        } else if (this.medium != anotherEntry.medium) {
            return Integer.compare(anotherEntry.medium, this.medium);
        } else if (this.easy != anotherEntry.easy) {
            return Integer.compare(anotherEntry.easy, this.easy);
        } else return 0;
    }
}

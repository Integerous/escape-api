package com.nokchax.escape.problem.domain;

import com.nokchax.escape.mission.domain.Mission;
import com.nokchax.escape.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "user_problem")
@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(SolvedProblemId.class)
public class SolvedProblem {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Id
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private LocalDateTime solvedTime;
    private LocalDateTime updatedTime;

    // mission info, and it also should be a id????
}

package com.nokchax.escape.problem.repository;


import com.nokchax.escape.JpaTest;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class ProblemRepositoryTest extends JpaTest {
    public static final List<String> TITLES = Arrays.asList(
            "Combination Sum II", "Combination Sum", "Count and Say", "Sudoku Solver", "Valid Sudoku", "Search Insert Position",
            "Find First and Last Position of Element in Sorted Array", "Search in Rotated Sorted Array", "Longest Valid Parentheses",
            "Next Permutation", "Substring with Concatenation of All Words", "Divide Two Integers", "Implement strStr()", "Remove Element",
            "Remove Duplicates from Sorted Array", "Reverse Nodes in k-Group", "Swap Nodes in Pairs", "Merge k Sorted Lists", "Generate Parentheses",
            "Merge Two Sorted Lists", "Valid Parentheses", "Remove Nth Node From End of List", "4Sum", "Letter Combinations of a Phone Number",
            "3Sum Closest", "3Sum", "Longest Common Prefix", "Roman to Integer", "Integer to Roman", "Container With Most Water", "Regular Expression Matching",
            "Palindrome Number", "String to Integer (atoi)", "Reverse Integer", "ZigZag Conversion", "Longest Palindromic Substring", "Median of Two Sorted Arrays",
            "Longest Substring Without Repeating Characters", "Add Two Numbers", "Two Sum"
    );

    @Autowired
    private ProblemRepository problemRepository;

    @ParameterizedTest
    @MethodSource
    @DisplayName("문제 번호를 넘겼을때 해당 문제를 푼 사용자를 제대로 리턴하는지")
    void findProblemSolveUserTest(Long problemNo, int expectedSize) {
        List<ProblemSolveUserDto> problemSolveUser = problemRepository.findProblemSolveUser(problemNo);

        assertThat(problemSolveUser).isNotNull();
        assertThat(problemSolveUser.size()).isEqualTo(expectedSize);

        log.info("Problem solve user dto");
        problemSolveUser.forEach(System.out::println);
    }

    private static Stream<Arguments> findProblemSolveUserTest() {
        return Stream.of(
                Arguments.of(1L, 14),
                Arguments.of(1281L, 1)
        );
    }

    /*
        푼 문제
        1, 2, 3, 4, 5, 6, 7, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 24, 25, 26, 27, 28, 31, 32, 35, 36, 39
        총 30 문제

        TITLES는 1~40번까지의 문제
    */
    @Test
    @DisplayName("사용자 아이디와 사용자가 푼 문제 리스트를 크롤하여 문제명만 넘겼을때, 이 사용자가 풀었지만 DB에는 저장되지 않은 문제들만 리턴하는지 테스트")
    void checkSolvedProblemCountTest() {
        log.info("nokchax14 번이 푼 문제는 30문제, TITLES는 총 40문제");
        beforeQuery();
        List<Problem> notSavedSolvedProblems = problemRepository.findSolvedButNotSavedYetProblems("nokchax14", TITLES);
        afterQuery();

        assertThat(notSavedSolvedProblems.size()).isEqualTo(10);

        showResult();
        notSavedSolvedProblems.forEach(System.out::println);
    }

    @MethodSource
    @ParameterizedTest
    @DisplayName("사용자 아이디나 푼 문제리스트에 문제가 있는경우 익셉션이 발생하는지 테스트한다.")
    void checkIllegalArgumentException(String userId, List<String> titles) {
        beforeQuery();
        List<Problem> solvedButNotSavedYetProblems = problemRepository.findSolvedButNotSavedYetProblems(userId, titles);
        afterQuery();

        assertThat(solvedButNotSavedYetProblems).isNotNull();
        assertThat(solvedButNotSavedYetProblems.size()).isZero();
    }

    private static Stream<Arguments> checkIllegalArgumentException() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, Collections.emptyList()),
                Arguments.of("", null),
                Arguments.of("", Collections.emptyList()),
                Arguments.of("okay", null),
                Arguments.of("okay", Collections.emptyList())
        );
    }
}
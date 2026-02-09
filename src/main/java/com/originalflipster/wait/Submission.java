package com.originalflipster.wait;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

final class Submission {
    private final String name;
    private final LocalDateTime submission;
    private final LocalDateTime deadline;
    private final Duration maxDuration;
    private final Duration estimatedDuration;
    private final String result;
    private Mode mode = Mode.MAX;

    Submission(String name, LocalDateTime submission, LocalDateTime deadline, Duration maxDuration, Duration estimatedDuration, String result) {
        this.name = name;
        this.submission = submission;
        this.deadline = deadline;
        this.maxDuration = maxDuration;
        this.estimatedDuration = estimatedDuration;
        this.result = result;
    }

    String getName() {
        return name;
    }

    long days() {
        return submission.until(LocalDateTime.now(), ChronoUnit.DAYS);
    }

    long hours() {
        return submission.until(LocalDateTime.now(), ChronoUnit.HOURS);
    }

    long secs() {
        return submission.until(LocalDateTime.now(), ChronoUnit.SECONDS);
    }

    BigDecimal progressDone() {
        if (deadline != null) {
            return BigDecimal.valueOf(((double) secs()) / ((double) activeDuration().toSeconds() + submission.until(deadline, ChronoUnit.SECONDS))).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.valueOf(((double) secs()) / ((double) activeDuration().toSeconds())).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    private LocalDateTime start() {
        if (deadline != null) {
            return deadline;
        }
        return submission;
    }

    private LocalDateTime end() {
        return start().plus(activeDuration());
    }

    long daysLeft() {
        return LocalDateTime.now().until(end(), ChronoUnit.DAYS);
    }

    long hoursLeft() {
        return LocalDateTime.now().until(end(), ChronoUnit.HOURS);
    }

    boolean done() {
        return result != null;
    }

    String getResult() {
        return result;
    }

    private boolean hasEstimate() {
        return estimatedDuration != null;
    }

    void setMode(Mode mode) {
        this.mode = mode;
    }

    private Duration activeDuration() {
        return mode == Mode.ESTIMATE && hasEstimate() ? estimatedDuration : maxDuration;
    }
}

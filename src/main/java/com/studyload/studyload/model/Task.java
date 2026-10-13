package com.studyload.studyload.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String type;

    @NotNull
    private String deadline;

    @NotNull
    private Integer estimatedHours;

    @NotBlank
    private String difficulty;

    public Task() {
    }

    // =========================
    // BASIC GETTERS AND SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // =========================
    // SMART PRIORITY SCORE
    // =========================

    @Transient
    public int getPriorityScore() {

        int score = 0;

        // -------------------------
        // 1. DIFFICULTY - 40 POINTS
        // -------------------------

        if ("Hard".equalsIgnoreCase(difficulty)) {
            score += 40;
        } else if ("Medium".equalsIgnoreCase(difficulty)) {
            score += 25;
        } else {
            score += 10;
        }

        // -------------------------
        // 2. ESTIMATED HOURS - 30 POINTS
        // -------------------------

        if (estimatedHours != null) {

            if (estimatedHours >= 8) {
                score += 30;
            } else if (estimatedHours >= 6) {
                score += 25;
            } else if (estimatedHours >= 4) {
                score += 20;
            } else if (estimatedHours >= 2) {
                score += 10;
            } else {
                score += 5;
            }
        }

        // -------------------------
        // 3. DEADLINE - 30 POINTS
        // -------------------------

        try {

            LocalDate deadlineDate =
                    LocalDate.parse(deadline);

            LocalDate today =
                    LocalDate.now();

            long daysRemaining =
                    ChronoUnit.DAYS.between(today, deadlineDate);

            if (daysRemaining < 0) {
                // Overdue
                score += 30;

            } else if (daysRemaining == 0) {
                // Due today
                score += 30;

            } else if (daysRemaining <= 2) {
                // Due within 2 days
                score += 25;

            } else if (daysRemaining <= 5) {
                // Due within 5 days
                score += 20;

            } else if (daysRemaining <= 10) {
                // Due within 10 days
                score += 10;

            } else {
                score += 5;
            }

        } catch (Exception e) {
            // If deadline is invalid
            score += 0;
        }

        return Math.min(score, 100);
    }

    // =========================
    // PRIORITY LEVEL
    // =========================

    @Transient
    public String getPriorityLevel() {

        int score = getPriorityScore();

        if (score >= 70) {
            return "HIGH";
        } else if (score >= 40) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }

    // =========================
    // DEADLINE STATUS
    // =========================

    @Transient
    public String getDeadlineStatus() {

        try {

            LocalDate deadlineDate =
                    LocalDate.parse(deadline);

            LocalDate today =
                    LocalDate.now();

            long daysRemaining =
                    ChronoUnit.DAYS.between(today, deadlineDate);

            if (daysRemaining < 0) {
                return "OVERDUE";

            } else if (daysRemaining == 0) {
                return "DUE TODAY";

            } else if (daysRemaining <= 3) {
                return "DUE SOON";

            } else {
                return "UPCOMING";
            }

        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
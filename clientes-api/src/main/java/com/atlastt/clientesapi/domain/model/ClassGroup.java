package com.atlastt.clientesapi.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ClassGroup {

    private final UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int workloadHours;
    private String teacherName;
    private final List<String> monitorNames;
    private final List<LocalDate> classDates;

    private ClassGroup(UUID id, String name, LocalDate startDate, LocalDate endDate,
                       int workloadHours, String teacherName,
                       List<String> monitorNames, List<LocalDate> classDates) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workloadHours = workloadHours;
        this.teacherName = teacherName;
        this.monitorNames = new ArrayList<>(monitorNames);
        this.classDates = new ArrayList<>(classDates);
    }

    public static ClassGroup create(String name, LocalDate startDate, LocalDate endDate,
                                    int workloadHours, String teacherName, List<String> monitorNames) {
        validateName(name);
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        validateDateRange(startDate, endDate);
        validateWorkload(workloadHours);
        validateTeacherName(teacherName);

        List<String> safeMonitorNames = monitorNames == null ? List.of() : monitorNames;

        return new ClassGroup(UUID.randomUUID(), name, startDate, endDate, workloadHours,
                teacherName, safeMonitorNames, List.of());
    }

    public static ClassGroup restore(UUID id, String name, LocalDate startDate, LocalDate endDate,
                                     int workloadHours, String teacherName,
                                     List<String> monitorNames, List<LocalDate> classDates) {
        Objects.requireNonNull(id, "id must not be null");
        validateName(name);
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");

        return new ClassGroup(id, name, startDate, endDate, workloadHours, teacherName,
                monitorNames == null ? List.of() : monitorNames,
                classDates == null ? List.of() : classDates);
    }

    public void registerClassDate(LocalDate date) {
        Objects.requireNonNull(date, "date must not be null");
        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            throw new IllegalArgumentException("Class date must be within the class group's date range");
        }
        if (!classDates.contains(date)) {
            classDates.add(date);
        }
    }

    public void addMonitor(String monitorName) {
        if (monitorName == null || monitorName.isBlank()) {
            throw new IllegalArgumentException("Monitor name must not be blank");
        }
        if (!monitorNames.contains(monitorName)) {
            monitorNames.add(monitorName);
        }
    }

    public void removeMonitor(String monitorName) {
        monitorNames.remove(monitorName);
    }

    public void updateTeacher(String newTeacherName) {
        validateTeacherName(newTeacherName);
        this.teacherName = newTeacherName;
    }

    public void updateName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void updateDateRange(LocalDate newStartDate, LocalDate newEndDate) {
        Objects.requireNonNull(newStartDate, "startDate must not be null");
        Objects.requireNonNull(newEndDate, "endDate must not be null");
        validateDateRange(newStartDate, newEndDate);
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("ClassGroup name must not be blank");
        }
    }

    private static void validateTeacherName(String teacherName) {
        if (teacherName == null || teacherName.isBlank()) {
            throw new IllegalArgumentException("Teacher name must not be blank");
        }
    }

    private static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }
    }

    private static void validateWorkload(int workloadHours) {
        if (workloadHours <= 0) {
            throw new IllegalArgumentException("Workload hours must be positive");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getWorkloadHours() {
        return workloadHours;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public List<String> getMonitorNames() {
        return Collections.unmodifiableList(monitorNames);
    }

    public List<LocalDate> getClassDates() {
        return Collections.unmodifiableList(classDates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassGroup that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
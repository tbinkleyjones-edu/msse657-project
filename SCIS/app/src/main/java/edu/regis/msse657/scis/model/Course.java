/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.model;

import java.io.Serializable;

/**
 * A simple domain object used to represent a course in a program.
 */
public class Course implements Serializable {

    static final long serialVersionUID = -1L;

    private final long id;
    private final String name;
    private final long programId;

    public Course(long id, String name, long programId) {
        this.id = id;
        this.name = name;
        this.programId = programId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getProgramId() {
        return programId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (programId != course.programId) return false;
        return !(name != null ? !name.equals(course.name) : course.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (programId ^ (programId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", programId=" + programId +
                '}';
    }
}

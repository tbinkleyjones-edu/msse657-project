package edu.regis.msse655.scis.model;

import java.io.Serializable;

/**
 * Created by Tim on 10/31/15.
 */
public class Program implements Serializable{
    static final long serialVersionUID = -1L;

    private final int id;
    private final String name;

    public Program(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Program program = (Program) o;

        if (id != program.id) return false;
        return !(name != null ? !name.equals(program.name) : program.name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

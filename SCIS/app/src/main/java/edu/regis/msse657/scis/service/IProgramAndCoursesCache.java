/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Program;


/**
 * A data repository that provides a cached of Program and Course objects.
 */
public interface IProgramAndCoursesCache {

    /**
     * Retrieves every Program object in the cache.
     * @return a list of all Program objects in the cache.
     */
    List<Program> retrieveAllPrograms();

    /**
     * Add the list of programs to the internal cache, removing any existing programs.
     * @param programs
     * @return
     */
    void cachePrograms(List<Program> programs);

    /**
     * Retrieves every Course object in the cache related to the specified program.
     * @param programId
     * @return a list of all Program objects in the cache.
     */
    List<Course> retrieveCoursesForProgram(long programId);

    /**
     * Add the list of courses to the internal cache, removing any existing courses.
     * @param courses
     * @return
     */
    void cacheCourses(List<Course> courses);

    /**
     * Remove all programs and courses stored in the cache.
     */
    void clear();
}

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
public interface IProgramAndCourseCache {

    /**
     * Retrieves every Program object in the cache.
     * @return a list of all Program objects in the cache.
     */
    List<Program> retrieveAllPrograms();

    /**
     * Add the program to the internal cache.
     * @param program
     * @return
     */
    void cacheProgram(Program program);

    /**
     * Retrieves every Course object in the cache related to the specified program.
     * @param programId
     * @return a list of all Program objects in the cache.
     */
    List<Course> retrieveCoursesForProgram(long programId);

    /**
     * Add the course to the internal cache.
     * @param course
     * @return
     */
    void cacheCourse(Course course);

    /**
     * Remove all programs and courses stored in the cache.
     * @return The number of rows affected.
     */
    int clear();
}

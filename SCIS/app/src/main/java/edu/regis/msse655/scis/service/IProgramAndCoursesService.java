package edu.regis.msse655.scis.service;

import java.util.List;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

/**
 * A service used to retrieve Program and Course objects asynchronously .
 */
public interface IProgramAndCoursesService {

    void getProgramsAsync(ProgramCallback callback);

    void getCoursesAsync(int programId, CourseCallback callback);

}

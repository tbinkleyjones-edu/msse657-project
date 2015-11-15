/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.Arrays;
import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Program;

/**
 * A test case to verify ProgramsAndCoursesCacheSioImpl.
 */
public class ProgramsAndCoursesCacheSQLiteImplTest extends ApplicationTestCase<Application> {

    // use an in memory database for each run to avoid deleting production data, and to avoid side effects between test runs.
    private static class ProgramAndCourseCacheInMemorySQLiteImpl extends ProgramAndCourseCacheSQLiteImpl {
        protected ProgramAndCourseCacheInMemorySQLiteImpl(Context context) {
            super(context, null);
        }
    }

    public ProgramsAndCoursesCacheSQLiteImplTest() {
        super(Application.class);
    }

    /**
     * Verify the service stores and retrieves program and course objects.
     *
     * @throws Exception
     */
    @MediumTest
    public void testServiceOperations() throws Exception {

        ProgramAndCourseCacheSQLiteImpl cacheInstance = new ProgramAndCourseCacheInMemorySQLiteImpl(getContext());
        assertEquals(0, cacheInstance.retrieveAllPrograms().size());  // expect to be empty initially
        assertEquals(0, cacheInstance.retrieveCoursesForProgram(1).size());  // expect to be empty initially

        List<Program> testPrograms = Arrays.asList(new Program(1, "one"), new Program(2, "two"));
        List<Course> testCourses = Arrays.asList(new Course(1, "one", 1), new Course(2, "two", 2));

        cacheInstance.cachePrograms(testPrograms);
        cacheInstance.cacheCourses(testCourses);

        assertEquals(2, cacheInstance.retrieveAllPrograms().size());
        assertEquals(1, cacheInstance.retrieveCoursesForProgram(1).size());

        // cache a different list
        testPrograms = Arrays.asList(new Program(2, "two"), new Program(3, "three"), new Program(4, "four"));
        cacheInstance.cachePrograms(testPrograms);
        assertEquals(3, cacheInstance.retrieveAllPrograms().size());
    }

}

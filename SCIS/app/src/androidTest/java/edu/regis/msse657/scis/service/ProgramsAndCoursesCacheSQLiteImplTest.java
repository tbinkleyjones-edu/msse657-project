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

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Courses;
import edu.regis.msse657.scis.model.Program;
import edu.regis.msse657.scis.model.Programs;

/**
 * A test case to verify ProgramsAndCoursesCacheSioImpl.
 */
public class ProgramsAndCoursesCacheSQLiteImplTest extends ApplicationTestCase<Application> {

    public static final String[] SQL_COLUMNS_PROGRAM = ProgramAndCourseCacheContentProviderImpl.SQL_COLUMNS_PROGRAM;
    public static final String[] SQL_COLUMNS_COURSE = ProgramAndCourseCacheContentProviderImpl.SQL_COLUMNS_COURSE;

    // use an in memory database for each run to avoid deleting production data, and to avoid side effects between test runs.
    private static class ProgramAndCourseServiceInMemorySQLiteImpl extends ProgramAndCourseServiceSQLiteImpl {
        protected ProgramAndCourseServiceInMemorySQLiteImpl(Context context) {
            super(context, null);
        }
    }

    public ProgramsAndCoursesCacheSQLiteImplTest() {
        super(Application.class);
    }

    /**
     * Verify the cache stores and retrieves program and course objects.
     *
     * @throws Exception
     */
    @MediumTest
    public void testServiceOperations() throws Exception {

        IProgramAndCoursesService serviceInstance = new ProgramAndCourseServiceInMemorySQLiteImpl(getContext());
        assertEquals(0, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());  // expect to be empty initially
        assertEquals(0, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());  // expect to be empty initially

        serviceInstance.insertProgram(Programs.toContentValues(new Program(1, "one")));
        serviceInstance.insertProgram(Programs.toContentValues(new Program(2, "two")));

        serviceInstance.insertCourse(Courses.toContentValues(new Course(1, "one", 2)));
        serviceInstance.insertCourse(Courses.toContentValues(new Course(2, "two", 2)));

        assertEquals(2, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());
        assertEquals(2, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());

        // delete objects
        serviceInstance.deleteProgram(1);
        serviceInstance.deleteCourse(1);

        assertEquals(1, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());
        assertEquals(1, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());

        // clear the cache
        serviceInstance.clear();
        assertEquals(0, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());  // expect to be empty
        assertEquals(0, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());  // expect to be empty
    }

}

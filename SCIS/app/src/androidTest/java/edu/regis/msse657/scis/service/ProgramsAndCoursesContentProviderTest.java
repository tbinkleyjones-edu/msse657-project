/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import edu.regis.msse657.scis.service.ProgramAndCourseContract.ProgramTable;
import edu.regis.msse657.scis.service.ProgramAndCourseContract.CourseTable;

/**
 * A test case to verify ProgramsAndCoursesCacheSioImpl.
 */
public class ProgramsAndCoursesContentProviderTest extends ApplicationTestCase<Application> {

    public static final String[] SQL_COLUMNS_PROGRAM = ProgramAndCourseCacheContentProviderImpl.SQL_COLUMNS_PROGRAM;
    public static final String[] SQL_COLUMNS_COURSE = ProgramAndCourseCacheContentProviderImpl.SQL_COLUMNS_COURSE;

    public ProgramsAndCoursesContentProviderTest() {
        super(Application.class);
    }

    /**
     * Verify the ContentProvder stores and retrieves program and course objects.
     *
     * @throws Exception
     */
    @MediumTest
    public void testProviderOperations() throws Exception {

        ProgramAndCourseContentProvider providerInstance = new ProgramAndCourseContentProvider();
        providerInstance.attachInfo(getContext(), null);

        // TODO: use a mock service and validate the outputs.

//        assertEquals(0, providerInstance.query(ProgramTable.TABLE_URI, SQL_COLUMNS_PROGRAM, null, null, null).getCount());  // expect to be empty initially
//        assertEquals(0, providerInstance.query(CourseTable.TABLE_URI, SQL_COLUMNS_COURSE, null, null, null).getCount());  // expect to be empty initially
//
//        serviceInstance.insertProgram(Programs.toContentValues(new Program(1, "one")));
//        serviceInstance.insertProgram(Programs.toContentValues(new Program(2, "two")));
//
//        serviceInstance.insertCourse(Courses.toContentValues(new Course(1, "one", 2)));
//        serviceInstance.insertCourse(Courses.toContentValues(new Course(2, "two", 2)));
//
//        assertEquals(2, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());
//        assertEquals(2, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());
//
//        // delete objects
//        serviceInstance.deleteProgram(1);
//        serviceInstance.deleteCourse(1);
//
//        assertEquals(1, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());
//        assertEquals(1, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());
//
//        // clear the cache
//        serviceInstance.clear();
//        assertEquals(0, serviceInstance.queryPrograms(SQL_COLUMNS_PROGRAM, null, null, null).getCount());  // expect to be empty
//        assertEquals(0, serviceInstance.queryCourses(SQL_COLUMNS_COURSE, null, null, null).getCount());  // expect to be empty
    }

}

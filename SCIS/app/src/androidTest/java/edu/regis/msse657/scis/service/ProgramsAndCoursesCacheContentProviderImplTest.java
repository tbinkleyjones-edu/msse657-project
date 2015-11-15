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
import android.content.ContentResolver;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.mock.MockContentResolver;
import android.test.suitebuilder.annotation.MediumTest;

import java.util.Arrays;
import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Program;

/**
 * A test case to verify ProgramsAndCoursesCacheSioImpl.
 */
public class ProgramsAndCoursesCacheContentProviderImplTest extends ApplicationTestCase<Application> {

    public ProgramsAndCoursesCacheContentProviderImplTest() {
        super(Application.class);
    }

    /**
     * Verify the cache stores and retrieves program and course objects.
     *
     * @throws Exception
     */
    @MediumTest
    public void testCacheOperations() throws Exception {

        ContentResolver contentResolver = new MockContentResolver();
        IProgramAndCourseCache cacheInstance = new ProgramAndCourseCacheContentProviderImpl(contentResolver);

        assertEquals(0, cacheInstance.retrieveAllPrograms().size());  // expect to be empty initially
        assertEquals(0, cacheInstance.retrieveCoursesForProgram(1).size());  // expect to be empty initially

        List<Program> testPrograms = Arrays.asList(new Program(1, "one"), new Program(2, "two"));
        List<Course> testCourses = Arrays.asList(new Course(1, "one", 1), new Course(2, "two", 2));

        for (Program program : testPrograms) {
            cacheInstance.cacheProgram(program);
        }
        for (Course course : testCourses) {
            cacheInstance.cacheCourse(course);
        }

        assertEquals(2, cacheInstance.retrieveAllPrograms().size());
        assertEquals(1, cacheInstance.retrieveCoursesForProgram(1).size());

        // modify a program and cache it
        cacheInstance.cacheProgram(new Program(2, "two and a half"));

        List<Program> programs = cacheInstance.retrieveAllPrograms();
        assertEquals(2, programs.size());
        assertEquals("two and a half", programs.get(1).getName());

        // modify a course and cache it
        cacheInstance.cacheCourse(new Course(2, "two and a half", 2));
        List<Course> courses = cacheInstance.retrieveCoursesForProgram(2);
        assertEquals(1, courses.size());
        assertEquals("two and a half", courses.get(0).getName());

        // clear the cache
        cacheInstance.clear();
        assertEquals(0, cacheInstance.retrieveAllPrograms().size());  // expect to be empty initially
        assertEquals(0, cacheInstance.retrieveCoursesForProgram(1).size());  // expect to be empty initially
    }

}

package edu.regis.msse655.scis.service;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

/**
 * Created by Tim on 10/31/15.
 */
public class ProgramsAndCoursesServiceRestImplTest extends InstrumentationTestCase {

    /**
     * An integration test to validate communication with the live Programs REST end point.
     * @throws Throwable
     */
    @LargeTest
    public void testGetPrograms() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final List<Program> retrievedPrograms = new ArrayList<>();

        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ProgramsAndCoursesServiceRestImpl().getProgramsAsync(new IProgramAndCoursesService.ProgramCallback() {

                    @Override
                    public void execute(List<Program> programs) {
                        retrievedPrograms.addAll(programs);
                        signal.countDown();
                    }
                });
            }
        });

	    /* The testing thread will wait here until the UI thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);

        assertEquals(10, retrievedPrograms.size()); // Regis' web service always returns the same number of things.
    }

    /**
     * An integration test to validate communication with the live Courses REST end point.
     * @throws Throwable
     */
    @LargeTest
    public void testGetCourses() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final List<Course> retrievedCourses = new ArrayList<>();

        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                int programId = 4; // This is the MSCC program
                new ProgramsAndCoursesServiceRestImpl().getCoursesAsync(programId, new IProgramAndCoursesService.CourseCallback() {

                    @Override
                    public void execute(List<Course> courses) {
                        retrievedCourses.addAll(courses);
                        signal.countDown();
                    }
                });
            }
        });

	    /* The testing thread will wait here until the UI thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);

        assertEquals(1, retrievedCourses.size()); // Regis' web service always returns one course per program
        //assertTrue(retrievedCourses.get(0).getName().startsWith("MSCC"));
    }
}

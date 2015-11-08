package edu.regis.msse655.scis.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.test.InstrumentationTestCase;
import android.test.ServiceTestCase;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.regis.msse655.scis.model.Course;
import edu.regis.msse655.scis.model.Program;

/**
 * Created by Tim on 10/31/15.
 */
public class ProgramsAndCoursesIntentServiceTest extends ServiceTestCase<ProgramAndCoursesIntentService> {

    public ProgramsAndCoursesIntentServiceTest() {
        super(ProgramAndCoursesIntentService.class);
    }

    /**
     * A unit test to validate that the programs helper method constructs a proper Intent and calls startService()
     */
    @SmallTest
    public void testStartActionGetPrograms() {

        Context context = new ContextWrapper(getContext()) {
            @Override
            public ComponentName startService(Intent intent) {
                assertNotNull(intent);
                assertEquals("edu.regis.msse655.scis.service.action.PROGRAMS", intent.getAction());
                return null; // return value doesn't matter here.
            }
        };

        ProgramAndCoursesIntentService.startActionGetPrograms(context);
    }

    /**
     * A unit test to validate that the programs helper method constructs a proper Intent and calls startService()
     */
    @SmallTest
    public void testStartActionGetCourses() {

        Context context = new ContextWrapper(getContext()) {
            @Override
            public ComponentName startService(Intent intent) {
                assertNotNull(intent);
                assertEquals("edu.regis.msse655.scis.service.action.COURSES", intent.getAction());
                assertEquals(1, intent.getIntExtra("edu.regis.msse655.scis.service.extra.PROGRAMID", -1));
                return null; // return value doesn't matter here.
            }
        };

        ProgramAndCoursesIntentService.startActionGetCourses(context, 1);
    }

    /**
     * An integration test to validate communication with the live Programs REST end point.
     *
     * @throws Throwable
     */
    @LargeTest
    public void testGetPrograms() throws InterruptedException {

        final CountDownLatch signal = new CountDownLatch(1);

        final Intent[] broadcastIntents = {null};

        Context context = new ContextWrapper(getContext()) {
            @Override
            public void sendBroadcast(Intent intent) {
                broadcastIntents[0] = intent;
                signal.countDown();
            }
        };

        setContext(context);

        Intent intent = new Intent(getContext(), ProgramAndCoursesIntentService.class);
        intent.setAction("edu.regis.msse655.scis.service.action.PROGRAMS");

        startService(intent);

        /* The testing thread will wait here until the service thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);

        Intent broadcastIntent = broadcastIntents[0];
        assertNotNull(broadcastIntent);
        assertEquals("edu.regis.msse655.scis.service.action.PROGRAMS_RESULT", broadcastIntent.getAction());

        ArrayList<Program> programs = (ArrayList<Program>) broadcastIntent.getSerializableExtra("edu.regis.msse655.scis.service.extra.PROGRAMS");
        assertEquals(10, programs.size()); // Regis' web service always returns the same number of things.

    }

    /**
     * An integration test to validate communication with the live Courses REST end point.
     *
     * @throws Throwable
     */
    @LargeTest
    public void testGetCourses() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final Intent[] broadcastIntents = {null};

        Context context = new ContextWrapper(getContext()) {
            @Override
            public void sendBroadcast(Intent intent) {
                broadcastIntents[0] = intent;
                signal.countDown();
            }
        };

        setContext(context);

        Intent intent = new Intent(getContext(), ProgramAndCoursesIntentService.class);
        intent.setAction("edu.regis.msse655.scis.service.action.COURSES");
        intent.putExtra("edu.regis.msse655.scis.service.extra.PROGRAMID", 4); // This is the MSCC program
        startService(intent);

        /* The testing thread will wait here until the service thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);

        Intent broadcastIntent = broadcastIntents[0];
        assertNotNull(broadcastIntent);
        assertEquals("edu.regis.msse655.scis.service.action.COURSES_RESULT", broadcastIntent.getAction());

        ArrayList<Course> courses = (ArrayList<Course>) broadcastIntent.getSerializableExtra("edu.regis.msse655.scis.service.extra.COURSES");
        assertEquals(1, courses.size()); // Regis' web service always returns one course per program
        assertTrue(courses.get(0).getName().startsWith("MSCC"));
    }
}

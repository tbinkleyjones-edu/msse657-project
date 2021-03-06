/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Program;

/**
 * Test case to verify ProgramsAndCourseIntentService
 */
public class ProgramsAndCoursesIntentServiceTest extends ServiceTestCase<ProgramAndCourseIntentService> {

    public ProgramsAndCoursesIntentServiceTest() {
        super(ProgramAndCourseIntentService.class);
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

        ProgramAndCourseIntentService.startActionGetPrograms(context);
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
                assertEquals(1, intent.getLongExtra("edu.regis.msse655.scis.service.extra.PROGRAMID", -1));
                return null; // return value doesn't matter here.
            }
        };

        ProgramAndCourseIntentService.startActionGetCourses(context, 1);
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

        // Delete any existing programs that may be in the database.
        new ProgramAndCourseServiceSQLiteImpl(context).clear();

        Intent intent = new Intent(getContext(), ProgramAndCourseIntentService.class);
        intent.setAction("edu.regis.msse655.scis.service.action.PROGRAMS");

        startService(intent);

        /* The testing thread will wait here until the service thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);

        Intent broadcastIntent = broadcastIntents[0];
        assertNotNull(broadcastIntent);
        assertEquals("edu.regis.msse655.scis.service.action.PROGRAMS_RESULT", broadcastIntent.getAction());
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

        // Delete any existing courses that may be in the database.
        new ProgramAndCourseServiceSQLiteImpl(context).clear();

        Intent intent = new Intent(getContext(), ProgramAndCourseIntentService.class);
        intent.setAction("edu.regis.msse655.scis.service.action.COURSES");
        intent.putExtra("edu.regis.msse655.scis.service.extra.PROGRAMID", 4L); // This is the MSCC program
        startService(intent);

        /* The testing thread will wait here until the service thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);

        Intent broadcastIntent = broadcastIntents[0];
        assertNotNull(broadcastIntent);
        assertEquals("edu.regis.msse655.scis.service.action.COURSES_RESULT", broadcastIntent.getAction());

        long programId = broadcastIntent.getLongExtra("edu.regis.msse655.scis.service.extra.PROGRAMID", -1);
        assertEquals(4L, programId); // Regis' web service always returns one course per program
    }
}

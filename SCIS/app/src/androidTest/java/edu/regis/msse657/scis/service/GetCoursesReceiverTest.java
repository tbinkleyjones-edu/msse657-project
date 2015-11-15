/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.test.ApplicationTestCase;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.regis.msse657.scis.model.Course;

/**
 * Test case used to verify GetCourseReceiver.
 */
public class GetCoursesReceiverTest extends ApplicationTestCase<Application> {

    public GetCoursesReceiverTest() {
        super(Application.class);
    }

    /**
     * Verify that the sendBroadcastGetCourses method calls context.sendBroadcast with
     * a properly built Intent.
     */
    @SmallTest
    public void testSendBroadcast() {

        Context context = new MockContext() {
            @Override
            public void sendBroadcast(Intent intent) {
                assertEquals("edu.regis.msse655.scis.service.action.COURSES_RESULT", intent.getAction());
                ArrayList<Course> courses = (ArrayList<Course>) intent.getSerializableExtra("edu.regis.msse655.scis.service.extra.COURSES");
                assertNotNull(courses);
                assertEquals(1, courses.size());
                assertEquals(new Course(1, "test course", 1), courses.get(0));
            }
        };

        List<Course> courses = Arrays.asList(new Course(1, "test course", 1));

        GetCoursesReceiver.sendBroadcastGetCourses(context, courses);
    }

    /**
     * Verify that the onReceive method calls the callback with a valid list of courses
     */
    @SmallTest
    public void testOnReceive() {

        GetCoursesReceiver receiver = new GetCoursesReceiver(new GetCoursesReceiver.CourseCallback() {
            @Override
            public void execute(List<Course> courses) {
                assertEquals(1, courses.size());
                assertEquals(new Course(1, "test course", 1), courses.get(0));
            }
        });


        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "test course", 1));

        Intent intent = new Intent();
        intent.setAction("edu.regis.msse655.scis.service.action.COURSES_RESULT");
        intent.putExtra("edu.regis.msse655.scis.service.extra.COURSES", courses);

        receiver.onReceive(new MockContext(), intent);

    }
}
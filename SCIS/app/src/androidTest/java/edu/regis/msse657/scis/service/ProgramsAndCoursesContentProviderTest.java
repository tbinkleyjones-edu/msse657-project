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

/**
 * A test case to verify ProgramAndCourseContentProvider.
 */
public class ProgramsAndCoursesContentProviderTest extends ApplicationTestCase<Application> {

    public ProgramsAndCoursesContentProviderTest() {
        super(Application.class);
    }

    /**
     * Verify the ContentProvider properly interacts with the IProgramAndCourseService.
     *
     * @throws Exception
     */
    @MediumTest
    public void testProviderOperations() throws Exception {

        ProgramAndCourseContentProvider providerInstance = new ProgramAndCourseContentProvider();

        // TODO: use a mock service and validate the content provider sends valid parameters to the serivce.
    }

}

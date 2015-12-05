/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service.tasks;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by Tim on 12/5/15.
 */
public class ChatTaskTest extends InstrumentationTestCase {

    /**
     * An integration test to validate communication with the live Programs REST end point.
     *
     * @throws Throwable
     */
    @LargeTest
    public void testGetPrograms() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final StringBuilder receivedMessage = new StringBuilder();

        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                ChatTask chatTask = new ChatTask(new ChatTask.ChatCallback() {
                    @Override
                    public void execute(String message) {
                        receivedMessage.append(message);
                        signal.countDown();
                    }
                });

                chatTask.execute("Hello Regis from Tim!");
            }
        });

	    /* The testing thread will wait here until the UI thread releases it
         * above with the countDown() or 30 seconds passes and it times out.
	     */
        signal.await(30, TimeUnit.SECONDS);
        assertEquals("** Thank you **", receivedMessage.toString()); // Regis' web service always returns ...
    }
}

package edu.regis.msse655.scis.model;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Tim on 10/31/15.
 */
public class CourseTest extends ApplicationTestCase<Application> {
    
    public CourseTest() {
        super(Application.class);
    }

    /**
     * Verify serialization by writing/reading an object to/from a byte array
     * @throws Exception
     */
    @SmallTest
    public void testSerializationShouldProduceEqualObjects() throws Exception {
        Course original = new Course(1, "name");

        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oOutputStream = new ObjectOutputStream(baOutputStream);
        oOutputStream.writeObject(original);
        oOutputStream.close();

        ByteArrayInputStream baInputStream = new ByteArrayInputStream(baOutputStream.toByteArray());
        ObjectInputStream oInputStream = new ObjectInputStream(baInputStream);
        Course clone = (Course) oInputStream.readObject();

        assertEquals(original, clone);
    }

}

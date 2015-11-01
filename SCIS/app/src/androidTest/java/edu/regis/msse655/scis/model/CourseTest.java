package edu.regis.msse655.scis.model;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * A test case for the Course class and the Courses helper class.
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

    /**
     * Verify searialization from a JSON string.
     * @throws Exception
     */
    @SmallTest
    public void testFromJsonShouldProduceListOfObjects() throws Exception {
        String json = "[{\"id\":1,\"name\":\"CIS 206 Business Software Applications\",\"pid\":{\"id\":1,\"name\":\"CIS\"}},{\"id\":2,\"name\":\"CN 301 Networking Technologies\",\"pid\":{\"id\":2,\"name\":\"CN\"}}]";
        List<Course> courses = Courses.fromJson(2, json);
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertTrue(courses.get(0).getName().startsWith("CN 301"));
    }

}

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
 * Created by Tim on 10/31/15.
 */
public class ProgramTest extends ApplicationTestCase<Application> {

    public ProgramTest() {
        super(Application.class);
    }

    /**
     * Verify serialization by writing/reading an object to/from a byte array
     * @throws Exception
     */
    @SmallTest
    public void testSerializationShouldProduceEqualObjects() throws Exception {
        Program original = new Program(1, "name");

        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oOutputStream = new ObjectOutputStream(baOutputStream);
        oOutputStream.writeObject(original);
        oOutputStream.close();

        ByteArrayInputStream baInputStream = new ByteArrayInputStream(baOutputStream.toByteArray());
        ObjectInputStream oInputStream = new ObjectInputStream(baInputStream);
        Course clone = (Course) oInputStream.readObject();

        assertEquals(original, clone);
    }

    @SmallTest
    public void testFromJsonShouldProduceListOfObjects() throws Exception {
        String json = "[{\"id\":1,\"name\":\"CIS\"},{\"id\":2,\"name\":\"CN\"}]";
        List<Program> programs = Programs.fromJson(json);
        assertNotNull(programs);
        assertEquals(2, programs.size());
        assertTrue(programs.get(1).getName().equals("CN"));
    }

}

package edu.regis.msse655.scis.components;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.regis.msse655.scis.model.Course;

/**
 * Created by Tim on 10/31/15.
 */
public class CourseArrayAdapter extends ArrayAdapter<Course> {
    public CourseArrayAdapter(Context context, List<Course> objects) {
        super(context, android.R.layout.simple_list_item_activated_1, android.R.id.text1, objects);
    }
}

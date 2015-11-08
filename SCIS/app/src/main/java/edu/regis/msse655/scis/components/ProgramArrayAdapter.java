/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse655.scis.components;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import edu.regis.msse655.scis.model.Program;

/**
 * An ArrayAdapter implementation for Program objects. Uses the built in simple_list_item_activated_1 layout.
 */
public class ProgramArrayAdapter extends ArrayAdapter<Program> {

    public ProgramArrayAdapter(Context context, List<Program> objects) {
        super(context, android.R.layout.simple_list_item_activated_1, android.R.id.text1, objects);
    }
}

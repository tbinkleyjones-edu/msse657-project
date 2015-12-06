/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import edu.regis.msse657.scis.service.tasks.FeedbackTask;

public class FeedbackActivity extends AppCompatActivity {

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edit = (EditText) findViewById(R.id.editText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the view and submit it.
                String text = edit.getText().toString();
                if (!text.isEmpty()) {
                    FeedbackTask feedbackTask = new FeedbackTask(new FeedbackTask.FeedbackCallback() {
                        @Override
                        public void execute(String message) {
                            // Display a toast with the responses and clear the edit box.
                            Snackbar.make(edit, message, Snackbar.LENGTH_LONG).setAction("Feedback", null).show();
                            edit.setText("");
                        }
                    });
                    feedbackTask.execute(text);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

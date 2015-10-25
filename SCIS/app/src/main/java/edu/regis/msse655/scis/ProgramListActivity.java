package edu.regis.msse655.scis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * An activity representing a list of Programs, which when touched,
 * lead to a {@link CourseListActivity} representing
 * item details.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ProgramListFragment}.
 * <p>
 * This activity also implements the required
 * {@link ProgramListFragment.Callbacks} interface
 * to listen for item selections.
 */

public class ProgramListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProgramListFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start the course list activity
                Intent detailIntent = new Intent(ProgramListActivity.this, ShareActivity.class);
                //detailIntent.putExtra(CourseDetailFragment.ARG_ITEM_ID, id); // TODO: add the id of the program
                startActivity(detailIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(getString(R.string.regis_university));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_programs) {
            // do nothing? programs already displayed?
        } else if (id == R.id.nav_feedback) {
            Intent detailIntent = new Intent(this, FeedbackActivity.class);
            startActivity(detailIntent);
        } else if (id == R.id.nav_chat) {
            Intent detailIntent = new Intent(this, ChatActivity.class);
            startActivity(detailIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(String id) {
        // start the course list activity
        Intent detailIntent = new Intent(this, CourseListActivity.class);
        //detailIntent.putExtra(CourseDetailFragment.ARG_ITEM_ID, id); // TODO: add the id of the program
        startActivity(detailIntent);
    }

}

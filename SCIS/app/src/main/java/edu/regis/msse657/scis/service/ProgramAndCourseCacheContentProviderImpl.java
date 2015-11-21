/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Courses;
import edu.regis.msse657.scis.model.Program;
import edu.regis.msse657.scis.model.Programs;
import edu.regis.msse657.scis.service.ProgramAndCourseContract.ProgramTable;
import edu.regis.msse657.scis.service.ProgramAndCourseContract.CourseTable;

/**
 *
 */
public class ProgramAndCourseCacheContentProviderImpl implements IProgramAndCourseCache {

    /*
     * SQL queries and statements
     */
    public static final String[] SQL_COLUMNS_PROGRAM = new String[]{
            ProgramTable._ID,
            ProgramTable.COLUMN_NAME_NAME
    };

    public static final String[] SQL_COLUMNS_COURSE = new String[]{
            CourseTable._ID,
            CourseTable.COLUMN_NAME_NAME,
            CourseTable.COLUMN_NAME_PROGRAM_ID
    };

    public static final String SQL_SELECT_COURSE_BY_PROGRAM_ID = ProgramAndCourseContract.CourseTable.COLUMN_NAME_PROGRAM_ID + "=?";

    private final ContentResolver contentResolver;

    public ProgramAndCourseCacheContentProviderImpl(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    /*
     * IProgramsAndCoursesCache implementation
     */

    @Override
    public List<Program> retrieveAllPrograms() {

        Cursor cursor = contentResolver.query(ProgramTable.TABLE_URI,
                SQL_COLUMNS_PROGRAM,
                null, // no selection
                null, // no selection args
                ProgramTable.COLUMN_NAME_NAME + " ASC"  // order by
        );

        ArrayList<Program> programList = new ArrayList<>();

        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Program program = Programs.fromCursor(cursor);
                programList.add(program);
                cursor.moveToNext();
            }
        }

        return Collections.unmodifiableList(new ArrayList<>(programList));
    }

    @Override
    public void cacheProgram(Program program) {
        // construct the uri containing the program's id
        Uri uri = ContentUris.withAppendedId(ProgramTable.TABLE_URI, program.getId());

        // delete the cached program if it exists
        contentResolver.delete(uri, null, null);

        ContentValues values = Programs.toContentValues(program);
        Uri id = contentResolver.insert(uri, values);
        assert(uri.equals(id));
    }

    @Override
    public List<Course> retrieveCoursesForProgram(long programId) {
        Cursor cursor = contentResolver.query(CourseTable.TABLE_URI,
                SQL_COLUMNS_COURSE,
                SQL_SELECT_COURSE_BY_PROGRAM_ID,
                new String[]{String.valueOf(programId)},
                null, // no order by
                null // no limit
        );

        ArrayList<Course> courseList = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course program = Courses.fromCursor(cursor);
                courseList.add(program);
                cursor.moveToNext();
            }
        }

        return Collections.unmodifiableList(new ArrayList<>(courseList));
    }

    @Override
    public void cacheCourse(Course course) {
        // construct the uri containing the program's id
        Uri uri = ContentUris.withAppendedId(CourseTable.TABLE_URI, course.getId());

        // delete all cached courses
        contentResolver.delete(uri, null, null);

        // copy the reference's values into a ContentValues map.
        ContentValues values = Courses.toContentValues(course);
        Uri id = contentResolver.insert(uri, values);
        assert(uri.equals(id));
    }

    @Override
    public int clear() {
        return contentResolver.delete(ProgramAndCourseContract.URI_CLEAR_CACHE, null, null);
    }
}

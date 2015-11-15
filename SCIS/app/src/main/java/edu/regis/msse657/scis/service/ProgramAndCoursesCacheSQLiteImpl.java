/*
 * Timothy Binkley-Jones
 * MSSE 657 Enterprise Android Software Development
 * Regis University
 */

package edu.regis.msse657.scis.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.regis.msse657.scis.model.Course;
import edu.regis.msse657.scis.model.Program;
import edu.regis.msse657.scis.service.ProgramAndCoursesContract.ProgramTable;
import edu.regis.msse657.scis.service.ProgramAndCoursesContract.CourseTable;

/**
 *
 */
public class ProgramAndCoursesCacheSQLiteImpl extends SQLiteOpenHelper implements IProgramAndCoursesCache {

    /*
     * SQL queries and statements
     */
    private static final String SQL_CREATE_PROGRAM_TABLE =
            "CREATE TABLE " + ProgramTable.TABLE_NAME + " (" +
                    ProgramTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ProgramTable.COLUMN_NAME_NAME + " TEXT" +
                    " )";

    public static final String SQL_DROP_PROGRAM_TABLE =
            "DROP TABLE IF EXISTS " + ProgramTable.TABLE_NAME;

    public static final String[] SQL_COLUMNS_PROGRAM = new String[]{
            ProgramTable._ID,
            ProgramTable.COLUMN_NAME_NAME
    };

    private static final String SQL_CREATE_COURSE_TABLE =
            "CREATE TABLE " + CourseTable.TABLE_NAME + " (" +
                    CourseTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CourseTable.COLUMN_NAME_NAME + " TEXT," +
                    CourseTable.COLUMN_NAME_PROGRAM_ID + " INTEGER" +
                    " )";

    public static final String SQL_DROP_COURSE_TABLE =
            "DROP TABLE IF EXISTS " + CourseTable.TABLE_NAME;

    public static final String[] SQL_COLUMNS_COURSE = new String[]{
            CourseTable._ID,
            CourseTable.COLUMN_NAME_NAME,
            CourseTable.COLUMN_NAME_PROGRAM_ID
    };

    public static final String SQL_SELECT_COURSE_BY_PROGRAM_ID = CourseTable.COLUMN_NAME_PROGRAM_ID + "=?";

    public ProgramAndCoursesCacheSQLiteImpl(Context context) {
        this(context, ProgramAndCoursesContract.DATABASE_NAME);
    }

    /**
     * A protected constructor to enable unit testing
     *
     * @param context
     * @param name
     */
    protected ProgramAndCoursesCacheSQLiteImpl(Context context, String name) {
        super(context, name, null, ProgramAndCoursesContract.DATABASE_VERSION);
    }

    /*
     * SQLiteOpenHelper implementation
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROGRAM_TABLE);
        db.execSQL(SQL_CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this version of onUpgrade just deletes all existing data and calls onCreate.
        db.execSQL(SQL_DROP_COURSE_TABLE);
        db.execSQL(SQL_DROP_COURSE_TABLE);
        onCreate(db);
    }


    /*
     * Private helper methods
     */
    private Program getProgram(Cursor cursor) {
        // fill fields in the same order the appear in the SQL_COLUMNS array.
        long programId = cursor.getInt(0);
        String name = cursor.getString(1);

        Program program = new Program(programId, name);
        return program;
    }
    
    private Course getCourse(Cursor cursor) {
        // fill fields in the same order the appear in the SQL_COLUMNS array.

        long courseId = cursor.getInt(0);
        String name = cursor.getString(1);
        long programId = cursor.getInt(2);

        Course program = new Course(courseId, name, programId);
        return program;
    }

    /*
     * IProgramsAndCoursesCache implementation
     */

    @Override
    public List<Program> retrieveAllPrograms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ProgramTable.TABLE_NAME,
                SQL_COLUMNS_PROGRAM,
                null, // no selection
                null, // no selection args
                null, // no group by
                null, // no having
                ProgramTable.COLUMN_NAME_NAME + " ASC"  // order by
        );

        ArrayList<Program> programList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Program program = getProgram(cursor);
            programList.add(program);
            cursor.moveToNext();
        }

        return Collections.unmodifiableList(new ArrayList<>(programList));
    }

    @Override
    public void cachePrograms(List<Program> programs) {
        SQLiteDatabase db = getWritableDatabase();

        // delete all cached programs
        db.delete(ProgramTable.TABLE_NAME, null, null);

        // insert each program into the database

        for (Program program : programs) {
            // copy the reference's values into a ContentValues map.
            ContentValues values = new ContentValues();
            values.put(ProgramTable._ID, program.getId());
            values.put(ProgramTable.COLUMN_NAME_NAME, program.getName());
            long id = db.insert(ProgramTable.TABLE_NAME, null, values);
        }
    }

    @Override
    public List<Course> retrieveCoursesForProgram(long programId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CourseTable.TABLE_NAME,
                SQL_COLUMNS_COURSE,
                SQL_SELECT_COURSE_BY_PROGRAM_ID,
                new String[]{String.valueOf(programId)}, // no selection args
                null, // no group by
                null, // no having
                null, // no order by
                null // no limit
        );

        ArrayList<Course> courseList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Course program = getCourse(cursor);
            courseList.add(program);
            cursor.moveToNext();
        }

        return Collections.unmodifiableList(new ArrayList<>(courseList));
    }

    @Override
    public void cacheCourses(List<Course> courses) {
        SQLiteDatabase db = getWritableDatabase();

        // delete all cached courses
        db.delete(CourseTable.TABLE_NAME, null, null);

        // insert each program into the database

        for (Course course : courses) {
            // copy the reference's values into a ContentValues map.
            ContentValues values = new ContentValues();
            values.put(CourseTable._ID, course.getId());
            values.put(CourseTable.COLUMN_NAME_NAME, course.getName());
            values.put(CourseTable.COLUMN_NAME_PROGRAM_ID, course.getProgramId());
            db.insert(CourseTable.TABLE_NAME, null, values);
        }

    }

    @Override
    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ProgramTable.TABLE_NAME, null, null);
        db.delete(CourseTable.TABLE_NAME, null, null);
    }


}

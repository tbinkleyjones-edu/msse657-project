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

import edu.regis.msse657.scis.service.ProgramAndCourseContract.ProgramTable;
import edu.regis.msse657.scis.service.ProgramAndCourseContract.CourseTable;

/**
 *
 */
public class ProgramAndCourseServiceSQLiteImpl extends SQLiteOpenHelper implements IProgramAndCoursesService {

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

    private static final String SQL_CREATE_COURSE_TABLE =
            "CREATE TABLE " + CourseTable.TABLE_NAME + " (" +
                    CourseTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CourseTable.COLUMN_NAME_NAME + " TEXT," +
                    CourseTable.COLUMN_NAME_PROGRAM_ID + " INTEGER" +
                    " )";

    public static final String SQL_DROP_COURSE_TABLE =
            "DROP TABLE IF EXISTS " + CourseTable.TABLE_NAME;

    public ProgramAndCourseServiceSQLiteImpl(Context context) {
        this(context, ProgramAndCourseContract.DATABASE_NAME);
    }

    public static final String SQL_SELECT_PROGRAM_BY_ID = ProgramAndCourseContract.ProgramTable._ID + "=?";
    public static final String SQL_SELECT_COURSE_BY_ID = ProgramAndCourseContract.CourseTable._ID + "=?";

    /**
     * A protected constructor to enable unit testing
     *
     * @param context
     * @param name
     */
    protected ProgramAndCourseServiceSQLiteImpl(Context context, String name) {
        super(context, name, null, ProgramAndCourseContract.DATABASE_VERSION);
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
        db.execSQL(SQL_DROP_PROGRAM_TABLE);
        db.execSQL(SQL_DROP_COURSE_TABLE);
        onCreate(db);
    }

    /*
     * IProgramAncCourseService implementation
     */

    @Override
    public int deleteProgram(long programId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ProgramTable.TABLE_NAME,
                SQL_SELECT_PROGRAM_BY_ID, new String[]{String.valueOf(programId)});
    }

    @Override
    public int deleteCourse(long courseId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(CourseTable.TABLE_NAME,
                SQL_SELECT_COURSE_BY_ID, new String[]{String.valueOf(courseId)});
    }

    @Override
    public long insertProgram(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(ProgramTable.TABLE_NAME, null, values);
    }

    @Override
    public long insertCourse(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(CourseTable.TABLE_NAME, null, values);
    }

    @Override
    public Cursor queryPrograms(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(ProgramTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public Cursor queryCourses(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(CourseTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int clear() {
        SQLiteDatabase db = getWritableDatabase();
        int count = db.delete(ProgramTable.TABLE_NAME, null, null);
        count += db.delete(CourseTable.TABLE_NAME, null, null);
        return count;
    }
}

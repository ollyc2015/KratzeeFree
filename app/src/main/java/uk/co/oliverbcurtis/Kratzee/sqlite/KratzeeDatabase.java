package uk.co.oliverbcurtis.Kratzee.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uk.co.oliverbcurtis.Kratzee.ui.common.BaseActivity;

public class KratzeeDatabase extends SQLiteOpenHelper {


    public KratzeeDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    private static final String DATABASE_NAME = "kratzee.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    //Defines table names
    public static final String QUESTION_TABLE = "question";
    public static final String ANSWER_TABLE = "answer";
    public static final String NEW_INDIVIDUAL = "new_individual";
    public static final String NEW_TEAM = "new_team";
    public static final String NEW_TEAM_MEMBERS = "new_teamMembers";
    public static final String EXISTING_INDIVIDUAL = "existing_individual";
    public static final String EXISTING_NEW_TEAM_MEMBERS = "existing_new_teamMembers";
    public static final String EXISTING_TEAM = "existing_team";
    public static final String EXISTING_TEAM_MEMBERS = "existing_teamMembers";
    public static final String LEADERBOARD = "leaderboard";

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

        //Defines table contents for questions
        db.execSQL("CREATE TABLE IF NOT EXISTS " + QUESTION_TABLE + " ("
                + KratzeeContract.QUESTION_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.QUESTION_STRING + " TEXT NOT NULL,"
                + KratzeeContract.QUESTION_LECTURER_ID + " TEXT NOT NULL)");


        //Defines table contents for answers
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ANSWER_TABLE + " ("
                + KratzeeContract.ANSWER_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.ANSWER_STRING + " TEXT NOT NULL,"
                + KratzeeContract.CORRECT + " TEXT NOT NULL,"
                + KratzeeContract.ANSWER_LECTURER_ID + " TEXT NOT NULL,"
                + KratzeeContract.QUESTION_ID + " TEXT NOT NULL)");

        //Defines table contents for an existing team
        db.execSQL("CREATE TABLE IF NOT EXISTS " + EXISTING_TEAM + " ("
                + KratzeeContract.EXISTING_TEAM_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.EXISTING_TEAM_NAME + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_TEAM_POINTS + " INT,"
                + KratzeeContract.EXISTING_TEAM_LECTURER_ID + " TEXT NOT NULL)");

        //Defines table contents for an existing teams team-members
        db.execSQL("CREATE TABLE IF NOT EXISTS " + EXISTING_TEAM_MEMBERS + " ("
                + KratzeeContract.EXISTING_TEAM_MEMBER_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.EXISTING_TEAM_MEMBER_FULLNAME + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_TEAM_MEMBER_STUDENT_NUMBER + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_TEAM_MEMBER_POINTS + " INT,"
                + KratzeeContract.EXISTING_TEAM_MEMBER_PRESENT + " TEXT,"
                + KratzeeContract.EXISTING_TEAM_MEMBER_LECTURER_ID + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_TEAM_MEMBER_TEAM_ID + " TEXT)");

        //Defines table contents for an existing teams team-members
        db.execSQL("CREATE TABLE IF NOT EXISTS " + EXISTING_NEW_TEAM_MEMBERS + " ("
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_FULLNAME + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_STUDENT_NUMBER + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_EMAIL + " TEXT,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_POINTS + " INT,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_PRESENT + " TEXT,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_LECTURER_ID + " TEXT NOT NULL,"
                + KratzeeContract.EXISTING_NEW_TEAM_MEMBER_TEAM_ID + " TEXT)");

        //Defines table contents for an new team
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_TEAM + " ("
                + KratzeeContract.NEW_TEAM_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.NEW_TEAM_NAME + " TEXT NOT NULL,"
                + KratzeeContract.NEW_TEAM_POINTS + " INT,"
                + KratzeeContract.NEW_TEAM_LECTURER_ID + " TEXT)");

        //Defines table contents for a new team's team-members
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_TEAM_MEMBERS + " ("
                + KratzeeContract.NEW_TEAM_MEMBER_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.NEW_TEAM_MEMBER_FULLNAME + " TEXT NOT NULL,"
                + KratzeeContract.NEW_TEAM_MEMBER_STUDENT_NUMBER + " TEXT NOT NULL,"
                + KratzeeContract.NEW_TEAM_MEMBER_POINTS + " INT,"
                + KratzeeContract.NEW_TEAM_MEMBER_LECTURER_ID + " TEXT)");

        //Defines table contents for a EXISTING individual profile
        db.execSQL("CREATE TABLE IF NOT EXISTS " + EXISTING_INDIVIDUAL + " ("
                + KratzeeContract.EXISTING_INDIVIDUAL_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.EXISTING_INDIVIDUAL_FULLNAME + " TEXT,"
                + KratzeeContract.EXISTING_INDIVIDUAL_STUDENT_NUMBER + " TEXT,"
                + KratzeeContract.EXISTING_INDIVIDUAL_POINTS + " INT,"
                + KratzeeContract.EXISTING_INDIVIDUAL_LECTURER_ID + " TEXT)");

        //Defines table contents for a NEW individual profile
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_INDIVIDUAL + " ("
                + KratzeeContract.NEW_INDIVIDUAL_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.NEW_INDIVIDUAL_STUDENT_FULLNAME + " TEXT NOT NULL,"
                + KratzeeContract.NEW_INDIVIDUAL_STUDENT_NUMBER + " TEXT NOT NULL,"
                + KratzeeContract.NEW_INDIVIDUAL_POINTS + " INT,"
                + KratzeeContract.NEW_INDIVIDUAL_LECTURER_ID + " TEXT)");

        //Defines table contents for the leaderboard
        db.execSQL("CREATE TABLE IF NOT EXISTS " + LEADERBOARD + " ("
                + KratzeeContract.LEADERBOARD_ID + " TEXT PRIMARY KEY,"
                + KratzeeContract.LEADERBOARD_FULLNAME + " TEXT NOT NULL,"
                + KratzeeContract.LEADERBOARD_STUDENT_NUMBER + " TEXT NOT NULL,"
                + KratzeeContract.LEADERBOARD_POINTS + " TEXT)");

        } catch (Exception e) {
            BaseActivity.showToast(context, e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if (version == 1) {
            // Add extra fields to database without deleting existing data
            version = 2;
        }
        if (version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ANSWER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + NEW_INDIVIDUAL);
            db.execSQL("DROP TABLE IF EXISTS " + NEW_TEAM);
            db.execSQL("DROP TABLE IF EXISTS " + NEW_TEAM_MEMBERS);
            db.execSQL("DROP TABLE IF EXISTS " + EXISTING_INDIVIDUAL);
            db.execSQL("DROP TABLE IF EXISTS " + EXISTING_NEW_TEAM_MEMBERS);
            db.execSQL("DROP TABLE IF EXISTS " + EXISTING_TEAM);
            db.execSQL("DROP TABLE IF EXISTS " + EXISTING_TEAM_MEMBERS);
            db.execSQL("DROP TABLE IF EXISTS " + LEADERBOARD);
            onCreate(db);
        }
    }
}

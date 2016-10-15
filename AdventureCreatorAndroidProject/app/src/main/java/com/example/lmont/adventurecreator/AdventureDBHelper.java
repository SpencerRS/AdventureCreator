package com.example.lmont.adventurecreator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.Random;

/**
 * Created by lmont on 9/25/2016.
 */
public class AdventureDBHelper extends SQLiteOpenHelper {

    public final static int VERSION = 4;
    public final static String
        TAG = "AdventureDBHelper",
        DB_NAME = "adventureDB",
        STORY_TABLE_NAME = "stories",
        CHAPTER_TABLE_NAME = "chapters",
        SCENES_TABLE_NAME = "scenes",
        TRANSITIONS_TABLE_NAME = "transitions";

    public final static String[]
        STORY_COLUMNS = new String[]{"_id", "title", "description", "genre", "type", "tags"},
        CHAPTER_COLUMNS = new String[]{"_id", "title", "summary", "type", "storyID"},
        SCENE_COLUMNS = new String[]{"_id", "title", "journalText", "flagModifiers", "body", "chapterID"},
        TRANSITION_COLUMNS = new String[]{"_id", "type", "verb", "flag", "attribute", "comparator", "challengeLevel", "fromSceneID", "toSceneID"};

    public final static String CREATE_STORY_TABLE =
        "CREATE TABLE IF NOT EXISTS " + STORY_TABLE_NAME + " (" +
        "_id TEXT," +
        "title TEXT," +
        "description TEXT," +
        "genre TEXT," +
        "type TEXT," +
        "tags STRING)";

    public final static String CREATE_CHAPTER_TABLE =
        "CREATE TABLE IF NOT EXISTS " + CHAPTER_TABLE_NAME + " (" +
        "_id TEXT," +
        "title TEXT," +
        "summary TEXT," +
        "type TEXT," +
        "storyID TEXT)";

    public final static String CREATE_SCENES_TABLE =
        "CREATE TABLE IF NOT EXISTS " + SCENES_TABLE_NAME + " (" +
        "_id TEXT," +
        "title TEXT," +
        "journalText TEXT," +
        "flagModifiers TEXT," +
        "body TEXT," +
        "chapterID TEXT)";

    public final static String CREATE_TRANSITIONS_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TRANSITIONS_TABLE_NAME + " (" +
        "_id TEXT," +
        "type TEXT," +
        "verb TEXT," +
        "flag TEXT," +
        "attribute TEXT," +
        "comparator TEXT," +
        "challengeLevel INTEGER," +
        "fromSceneID TEXT," +
        "toSceneID TEXT)";

    private static AdventureDBHelper instance;

    private APIHelper apiHelper;

    public static AdventureDBHelper getInstance(Context context) {
        if (instance == null)
            instance = new AdventureDBHelper(context.getApplicationContext());

        return instance;
    }

    private AdventureDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        apiHelper = APIHelper.getInstance(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORY_TABLE);
        db.execSQL(CREATE_CHAPTER_TABLE);
        db.execSQL(CREATE_SCENES_TABLE);
        db.execSQL(CREATE_TRANSITIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHAPTER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SCENES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSITIONS_TABLE_NAME);
        onCreate(db);
    }

    public void addStory(ContentValues cv) {
        getWritableDatabase().insert(STORY_TABLE_NAME, null, cv);
    }

    public void addStory(Models.Story story) {
        ContentValues cv = new ContentValues();
        cv.put("title", story.title);
        cv.put("description", story.description);
        cv.put("genre", story.genre);
        cv.put("tags", story.tags);
        cv.put("type", story.type);
        cv.put("_id", story._id);
        getWritableDatabase().insert(STORY_TABLE_NAME, null, cv);
    }

    public void addChapter(ContentValues cv) {
        getWritableDatabase().insert(CHAPTER_TABLE_NAME, null, cv);
    }

    public void addChapter(Models.Chapter chapter) {
        ContentValues cv = new ContentValues();
        cv.put("storyID", chapter.storyID);
        cv.put("title", chapter.title);
        cv.put("summary", chapter.summary);
        cv.put("type", chapter.type);
        cv.put("_id", chapter._id);
        getWritableDatabase().insert(CHAPTER_TABLE_NAME, null, cv);
    }

    public void addScene(ContentValues cv) {
        getWritableDatabase().insert(SCENES_TABLE_NAME, null, cv);
    }

    public void addScene(Models.Scene scene) {
        ContentValues cv = new ContentValues();
        cv.put("chapterID", scene.chapterID);
        cv.put("title", scene.title);
        cv.put("body", scene.body);
        cv.put("journalText", scene.journalText);
        cv.put("flagModifiers", scene.flagModifiers);
        cv.put("_id", scene._id);
        getWritableDatabase().insert(SCENES_TABLE_NAME, null, cv);
    }

    public void addTransition(ContentValues cv) {
        getWritableDatabase().insert(TRANSITIONS_TABLE_NAME, null, cv);
    }

    public void addTransition(Models.Transition transition) {
        ContentValues cv = new ContentValues();
        cv.put("fromSceneID", transition.fromSceneID);
        cv.put("toSceneID", transition.toSceneID);
        cv.put("type", transition.type);
        cv.put("verb", transition.verb);
        cv.put("flag", transition.flag);
        cv.put("attribute", transition.attribute);
        cv.put("comparator", transition.comparator);
        cv.put("challengeLevel", transition.challengeLevel);
        cv.put("_id", transition._id);
        getWritableDatabase().insert(TRANSITIONS_TABLE_NAME, null, cv);
    }

    public Models.Story getStory(String storyID) {
        Cursor cursor = getReadableDatabase().query(STORY_TABLE_NAME, STORY_COLUMNS, "_id = " + storyID, null, null, null, null);
        cursor.moveToFirst();

    }

    public Models.Chapter getChapter(String chapterID) {
        // Do dis
    }

    public Models.Scene getScene(String sceneID) {
        // Do dis
    }

    public Models.Transition getTransition(String transitionID) {
        // Do dis
    }

    public void deleteAll() {
        getWritableDatabase().delete(STORY_TABLE_NAME,null,null);
        getWritableDatabase().delete(CHAPTER_TABLE_NAME,null,null);
        getWritableDatabase().delete(SCENES_TABLE_NAME,null,null);
        getWritableDatabase().delete(TRANSITIONS_TABLE_NAME,null,null);
    }
}

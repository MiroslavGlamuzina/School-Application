package database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import tools.Entry;
import tools.EntryList;
import tools.Tools;

public class Database {

    private DatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String TABLE = "MyTable";

    public final static String PID = "PID";
    public final static String DATE = "DATE";
    public final static String TITLE = "TITLE";
    public final static String NOTE = "NOTE";
    public final static String FLAG = "FLAG";
    public final static String VIDEO = "VIDEO";
    public final static String AUDIO = "AUDIO";
    public final static String DRAWING = "DRAWING";
    public final static String PICTURE = "PICTURE";
    public final static String TAGS = "TAGS";

    /**
     * @param context
     */
    public Database(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long createRecords(String date, String title, String note, String flag, String video, String audio, String drawing,
                              String picture, String tags) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(TITLE, title);
        values.put(NOTE, Tools.getDateString() + Tools.HASH + note);
        values.put(FLAG, Tools.getDateString() + Tools.HASH + flag);
        values.put(VIDEO, Tools.getDateString() + Tools.HASH + video);
        values.put(AUDIO, Tools.getDateString() + Tools.HASH + audio);
        values.put(DRAWING, Tools.getDateString() + Tools.HASH + drawing);
        values.put(PICTURE, Tools.getDateString() + Tools.HASH + picture);
        values.put(TAGS, tags);
        return database.insert(TABLE, null, values);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// UPDnTE ENTIRES
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public long updateDate(String date, String id) {
        ContentValues cv = new ContentValues();
        cv.put(DATE, date);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateTitle(String title, String id) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateNote(String note, String id) {
        String des = getNote(id);
        ContentValues cv = new ContentValues();
        cv.put(NOTE, des + Tools.HASH + Tools.getDateString() + Tools.HASH + note);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateFlag(String flag, String id) {
        // complete the getnote(id) method here to match  flags
        String oldFlags = getFlag(id);
        ContentValues cv = new ContentValues();
        cv.put(FLAG, oldFlags + Tools.HASH + Tools.getDateString() + Tools.HASH + flag);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateVideo(String video, String id) {
        String vid = getVideo(id);
        ContentValues cv = new ContentValues();
        cv.put(VIDEO, vid + Tools.HASH + Tools.getDateString() + Tools.HASH + video);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateAudio(String audio, String id) {
        String aud = getAudio(id);
        ContentValues cv = new ContentValues();
        cv.put(AUDIO, aud + Tools.HASH + Tools.getDateString() + Tools.HASH + audio);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateDrawing(String drawing, String id) {
        String draw = getDrawing(id);
        ContentValues cv = new ContentValues();
        cv.put(DRAWING, draw + Tools.HASH + Tools.getDateString() + Tools.HASH + drawing);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updatePicture(String picture, String id) {
        String pic = getPicture(id);
        ContentValues cv = new ContentValues();
        cv.put(PICTURE, pic + Tools.HASH + Tools.getDateString() + Tools.HASH + picture);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long updateTags(String tag, String id) {
        String tags = getTags(id);
        ContentValues cv = new ContentValues();
        cv.put(TAGS, tags + Tools.HASH + tag);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// REMOVE SINGLE ENTIRES
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // NEEDS MORE WORRKKK!!!!!!!!!!

    public long removeNote(String note, String id) {
        String[] destemp = getNote(id).split(Tools.HASH);
        String temp = "";
        for (int i = 0; i < destemp.length; i++) {
            if (destemp[i].equals(note)) {
                destemp[i] = "";
                destemp[i - 1] = "";
            }
        }
        for (int i = 0; i < destemp.length; i++) {
            if (!destemp[i].equals("") && i < destemp.length) {
                temp += destemp[i];
            } else if (!destemp[i].equals("")) {
                temp += destemp[i] + Tools.HASH;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put(NOTE, temp + Tools.HASH + Tools.getDateString() + Tools.HASH + note);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeFlag(String note, String id) {
        //complete remove the getNote(id) and insert getFlag
        String[] destemp = getFlag(id).split(Tools.HASH);
        String temp = "";
        for (int i = 0; i < destemp.length; i++) {
            if (destemp[i].equals(note)) {
                destemp[i] = "";
                destemp[i - 1] = "";
            }
        }
        for (int i = 0; i < destemp.length; i++) {
            if (!destemp[i].equals("") && i < destemp.length) {
                temp += destemp[i];
            } else if (!destemp[i].equals("")) {
                temp += destemp[i] + Tools.HASH;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put(NOTE, temp + Tools.HASH + Tools.getDateString() + Tools.HASH + note);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeVideo(String video, String id) {
        String vid = getVideo(id);
        ContentValues cv = new ContentValues();
        cv.put(VIDEO, vid + Tools.HASH + Tools.getDateString() + Tools.HASH + video);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAudio(String audio, String id) {
        String aud = getAudio(id);
        ContentValues cv = new ContentValues();
        cv.put(AUDIO, aud + Tools.HASH + Tools.getDateString() + Tools.HASH + audio);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeDrawing(String drawing, String id) {
        String draw = getDrawing(id);
        ContentValues cv = new ContentValues();
        cv.put(DRAWING, draw + Tools.HASH + Tools.getDateString() + Tools.HASH + drawing);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removePicture(String picture, String id) {
        String pic = getPicture(id);
        ContentValues cv = new ContentValues();
        cv.put(PICTURE, pic + Tools.HASH + Tools.getDateString() + Tools.HASH + picture);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeTags(String tag, String id) {
        String tags = getTags(id);
        ContentValues cv = new ContentValues();
        cv.put(TAGS, tags + Tools.HASH + tag);
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// REMOVE All ENTIRES
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public long removeAllDate(String id) {
        ContentValues cv = new ContentValues();
        cv.put(DATE, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllTitle(String id) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllNote(String id) {
        ContentValues cv = new ContentValues();
        cv.put(NOTE, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllFlag(String id) {
        ContentValues cv = new ContentValues();
        cv.put(FLAG, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllVideo(String id) {
        ContentValues cv = new ContentValues();
        cv.put(VIDEO, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllAudio(String id) {
        ContentValues cv = new ContentValues();
        cv.put(AUDIO, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllDrawing(String id) {
        ContentValues cv = new ContentValues();
        cv.put(DRAWING, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllPicture(String id) {
        ContentValues cv = new ContentValues();
        cv.put(PICTURE, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    public long removeAllTags(String id) {
        ContentValues cv = new ContentValues();
        cv.put(TAGS, "");
        String where = PID + " = ?";
        String[] whereArgs = new String[]{id};
        return database.update(TABLE, cv, where, whereArgs);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// RETURN ENTIRES
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getTitle(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + TITLE + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(TITLE));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getDate(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + DATE + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(DATE));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getNote(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + NOTE + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(NOTE));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getFlag(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + FLAG + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(FLAG));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getVideo(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + VIDEO + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(VIDEO));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getAudio(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + AUDIO + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(AUDIO));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getDrawing(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + DRAWING + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(DRAWING));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getPicture(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + PICTURE + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(PICTURE));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public String getTags(String id) {
        String res = "";
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT " + TAGS + " FROM MyTable where PID =" + id, null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res = c.getString(c.getColumnIndex(TAGS));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public ArrayList<String> getAllEntries() {
        ArrayList<String> res = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM MyTable", null);
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                res.add(c.getString(c.getColumnIndex(PID)) + " | " + c.getString(c.getColumnIndex(DATE)) + " | "
                        + c.getString(c.getColumnIndex(TITLE)) + " | " + c.getString(c.getColumnIndex(NOTE))
                        + c.getString(c.getColumnIndex(FLAG)) + " | " + " | " + c.getString(c.getColumnIndex(VIDEO)) + " | " + c.getString(c.getColumnIndex(AUDIO))
                        + " | " + c.getString(c.getColumnIndex(DRAWING)) + " | "
                        + c.getString(c.getColumnIndex(PICTURE)) + " | " + c.getString(c.getColumnIndex(TAGS)));
                i++;
            } while (c.moveToNext());
            c.close();
        }
        return res;
    }

    public int size() {
        database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM MyTable", null);
        return c.getCount();
    }

    public int sizeNote(String id) {
        String des = getNote(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length / 2;
    }

    public int sizeFlag(String id) {
        String des = getFlag(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length / 2;
    }

    public int sizeVideo(String id) {
        String des = getVideo(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length / 2;
    }

    public int sizeAudio(String id) {
        String des = getAudio(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length / 2;
    }

    public int sizePicture(String id) {
        String des = getPicture(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length / 2;
    }

    public int sizeDrawing(String id) {
        String des = getDrawing(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length / 2;
    }

    public int sizeTags(String id) {
        String des = getAudio(id);
        String[] temp = des.split(Tools.HASH);
        return temp.length;
    }

    public void deleteDatabase() {
        database.execSQL("DROP TABLE IF EXISTS MyTable");
        dbHelper.onCreate(database);
    }

    public String getAllDEBUG(String id) {
        String[] note = getNote(id).split(Tools.HASH);
        String[] video = getVideo(id).split(Tools.HASH);
        String[] picture = getPicture(id).split(Tools.HASH);
        String[] drawing = getDrawing(id).split(Tools.HASH);
        String[] audio = getAudio(id).split(Tools.HASH);

        // all into one
        String res = "";
        ArrayList<Entry> entries = new ArrayList<Entry>();
        if (note.length > 1) {
            res += "NOTES PASSED!! " + sizeNote(id) + "\n";
            for (int i = 0; i < note.length; i += 2) {
                entries.add(new Entry(note[i], note[i + 1], Entry.NOTE));
            }
        } else {
            res += "NOTES NOT PASSED! " + sizeNote(id) + "\n";
        }
        if (video.length > 1) {
            res += "VIDEO PASSED!! " + sizeVideo(id) + " \n";
            for (int i = 0; i < video.length; i += 2) {
                entries.add(new Entry(video[i], video[i + 1], Entry.VIDEO));
            }
        } else {
            res += "VIDEO NOT PASSED! " + sizeVideo(id) + " \n";
        }
        if (picture.length > 1) {
            res += "PICTURE PASSED!! " + sizePicture(id) + ", " + picture.length + "\n";
            for (int i = 0; i < picture.length; i += 2) {
                entries.add(new Entry(picture[i], picture[i + 1], Entry.PICTURE));
            }
        } else {
            res += "PICTURE NOT PASSED! " + sizePicture(id) + ", " + picture.length + "\n";
        }
        if (drawing.length > 1) {
            res += "DRAWING PASSED!! " + sizeDrawing(id) + "\n";
            for (int i = 0; i < drawing.length; i += 2) {
                entries.add(new Entry(drawing[i], drawing[i + 1], Entry.DRAWING));
            }
        } else {
            res += "DRAWING NOT PASSED! " + sizeDrawing(id) + "\n";
        }
        if (audio.length > 1) {
            res += "AUDIO PASSED!! " + sizeAudio(id) + "\n";
            for (int i = 0; i < audio.length; i += 2) {
                entries.add(new Entry(audio[i], audio[i + 1], Entry.AUDIO));
            }
        } else {
            res += "AUDIO NOT PASSED! " + sizeAudio(id) + "\n";
        }
        for (int i = 0; i < entries.size(); i++) {
            // Log.d(TAGS, entries.get(i).getDate() + ", " +
            // entries.get(i).getVal() + ", " + entries.get(i).getType());
            res += entries.get(i).getDate() + ", " + entries.get(i).getVal() + ", " + entries.get(i).getType() + "\n";
        }
        return res;
    }

    public ArrayList<Entry> getAllFromID(String id) {
        String[] note = getNote(id).split(Tools.HASH);
        String[] video = getVideo(id).split(Tools.HASH);
        String[] picture = getPicture(id).split(Tools.HASH);
        String[] drawing = getDrawing(id).split(Tools.HASH);
        String[] audio = getAudio(id).split(Tools.HASH);
        String[] flag = getFlag(id).split(Tools.HASH);
        // all into one
        ArrayList<Entry> entries = new ArrayList<Entry>();
        if (note.length > 2) {
            for (int i = 2; i < note.length; i += 2) {
                entries.add(new Entry(note[i], note[i + 1], Entry.NOTE));
            }
        }
        if (video.length > 2) {
            for (int i = 2; i < video.length; i += 2) {
                entries.add(new Entry(video[i], video[i + 1], Entry.VIDEO));
            }
        }
        if (picture.length > 2) {
            for (int i = 2; i < picture.length; i += 2) {
                entries.add(new Entry(picture[i], picture[i + 1], Entry.PICTURE));
            }
        }
        if (drawing.length > 2) {
            for (int i = 2; i < drawing.length; i += 2) {
                entries.add(new Entry(drawing[i], drawing[i + 1], Entry.DRAWING));
            }
        }
        if (audio.length > 2) {
            for (int i = 2; i < audio.length; i += 2) {
                entries.add(new Entry(audio[i], audio[i + 1], Entry.AUDIO));
            }
        }
        if (flag.length > 2) {
            for (int i = 2; i < flag.length; i += 2) {
                entries.add(new Entry(flag[i], flag[i + 1], Entry.FLAG));
            }
        }
        return entries;
    }

    public EntryList getAllList(String id) {
        return new EntryList(getTitle(id), getDate(id), sizeAudio(id), sizeDrawing(id), sizePicture(id), sizeTags(id), sizeNote(id), sizeFlag(id), Integer.parseInt(id));
    }
}
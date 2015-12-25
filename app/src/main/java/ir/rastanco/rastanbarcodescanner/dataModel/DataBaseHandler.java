package ir.rastanco.rastanbarcodescanner.dataModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ShaisteS on 12/12/2015.
 * DataBaseHandler managing CRUD(Create,Read,Update,Delete) operation
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    public DataBaseHandler(Context context){
        super(context, "BarcodeScanner.dbo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tblFileInfo"+"(id Integer primary key AUTOINCREMENT, fileName text, fileType text,DateModified text)");
        Log.v("create", "Create Table File Info");

    }

    public Boolean emptyDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs = db.rawQuery( "select * from tblFileInfo", null );
        if (rs.moveToFirst() ) {
            //Not empty
            return false;
        }
        else
        {
            //Is Empty
            return true;
        }
    }

    public void insertAFileInfo(FileInfo aFile){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("tblFileInfo", null,addFieldToFileInfoTable(aFile));
        Log.v("insert", "insert A File Information into FileInfo Table");
        db.close();
    }

    private ContentValues addFieldToFileInfoTable(FileInfo aFile){
        ContentValues values = new ContentValues();
        values.put("fileName",aFile.getFileName());
        values.put("fileType", aFile.getFileType());
        values.put("DateModified", aFile.getDateModified());
        Log.v("insert field", "insert A Field into FileInfo Table");
        return values;
    }

    public ArrayList<FileInfo> selectAllFileInfo(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs =  db.rawQuery( "select * from tblFileInfo", null );
        ArrayList<FileInfo> allFileInfo=new ArrayList<FileInfo>();
        if (rs != null) {
            if (rs.moveToFirst()) {
                do {
                    FileInfo aFileInfo=new FileInfo(rs.getString(rs.getColumnIndex("fileName")),
                            rs.getString(rs.getColumnIndex("fileType")),
                            rs.getString(rs.getColumnIndex("DateModified")));
                    allFileInfo.add(aFileInfo);
                }
                while (rs.moveToNext());
            }
            rs.close();
        }
        Log.v("select", "Select All File Info");
        return allFileInfo;
    }
    public FileInfo selectAFeed(int idAFileInfo){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rs =  db.rawQuery( "select * from tblFileInfo where id=" + idAFileInfo  + "", null );
        rs.moveToFirst();
        FileInfo aFileInfo=new FileInfo(rs.getString(rs.getColumnIndex("fileName")),
                rs.getString(rs.getColumnIndex("fileType")),
                rs.getString(rs.getColumnIndex("DateModified")));
        rs.close();
        Log.v("select", "select A FileInfo");
        return aFileInfo;
    }

    public void deleteAFeed(int idAFileInfo){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("tblFileInfo", "id=" + idAFileInfo + "", null);
        Log.v("delete", "Delete A File Info");
    }
    public void deleteAllFeed(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("tblFileInfo", null, null);
        Log.v("delete","Delete All FileInfo");
    }

    public void updateFileName(int fileInfoId,String FileName){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("fileName",FileName);
        db.update("tblFileInfo", values, "id=" + fileInfoId + "", null);
        Log.v("update","Update File Name in File Info Table");
    }
    public void updateFileType(int fileInfoId,String FileType){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("fileType",FileType);
        db.update("tblFileInfo", values, "id=" + fileInfoId + "", null);
        Log.v("update","Update File Type in File Info Table");
    }
    public void updateFileDate(int fileInfoId,String FileDate){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("DateModified",FileDate);
        db.update("tblFileInfo", values, "id=" + fileInfoId + "", null);
        Log.v("update","Update File Date Modified in File Info Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO complete onUpgrade Method
    }
}

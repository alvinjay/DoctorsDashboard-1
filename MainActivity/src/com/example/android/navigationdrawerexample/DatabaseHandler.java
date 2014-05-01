/*
 ** Created by Jessie Emmanuel Adante
 *** Created on 4/29/14
 ** Edited by Jose Martin Ipong - 4/29/14
 
 ** This class is the database handler
 */

package com.example.android.navigationdrawerexample;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
	private static final int database_version = 1;
	private static final String database_name = "seg_db_test";
	private static final String table_name = "doctors";
	private static final String personnel_number = "seg_personnel_number";
	//private static final String location_number = "locationnumber";
	//private static final String doctor_username = "doctorusername";
	//private static final String doctor_password = "doctorpassword";
	private static final String doctor_first_name = "seg_firstname";
	//private static final String doctor_middle_name = "doctormiddlename" ;
	private static final String doctor_last_name = "seg_lastname";
	
	public DatabaseHandler(Context context)
	{
		super(context, database_name, null, database_version);
	}
	
	public void onCreate(SQLiteDatabase db)
	{/*
		String create_contacts_table = "CREATE TABLE " + table_name + "(" 
				+ personnel_number + " INTEGER PRIMARY KEY, " + location_number
				+ " TEXT, " + doctor_username + " TEXT, " + doctor_password 
				+ " TEXT, " + doctor_password + " TEXT, " + doctor_first_name 
				+ "TEXT, " + doctor_middle_name + " TEXT, " + doctor_last_name 
				+ " TEXT)";
		
		db.execSQL(create_contacts_table);
		*/
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldversion,
			int newversion)
	{
		db.execSQL("DROP TABLE IF EXIST " + table_name);
		onCreate(db);
		//better if there is a backup of a table
	}
	
	public void addDoctorProfile(DoctorProfile doctor)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(personnel_number, doctor.getPersonnelNumber());
		//values.put(location_number, doctor.getLocationNumber());
		//values.put(doctor_username, doctor.getDoctorUsername());
		//values.put(doctor_password, doctor.getDoctorPassword());
		values.put(doctor_first_name, doctor.getDoctorFirstName());
		//values.put(doctor_middle_name, doctor.getDoctorMiddleName());
		values.put(doctor_last_name, doctor.getDoctorLastName());
		
		db.insert(table_name, null, values);
		db.close();
	}
	
	public boolean ifExists(String personnelnumber){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT seg_personnel_number,seg_firstname,seg_lastname FROM doctors WHERE seg_personnel_number = '" + personnelnumber + "'";
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			return true;
		}
		else{
			return false;
		}
	
	
	}
	
	public DoctorProfile getDoctor(String personnelnumber){
		DoctorProfile doctor = new DoctorProfile("username", "password", "firstname", "lastname");
		String doctor_firstname = "Firstname";
		String doctor_lastname = "Lastname";
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT seg_personnel_number,seg_firstname,seg_lastname FROM doctors WHERE seg_personnel_number = '" + personnelnumber + "'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
    		do {
            	try{
	                DoctorProfile doctor1 = new DoctorProfile(cursor.getString(0), cursor.getString(1), cursor.getString(2));
	                if(doctor1.getPersonnelNumber().equals(personnelnumber)){
	                	return doctor1;
	                }
	                
            	}
            	catch(Exception e){
            		System.out.println(e);
            		return doctor;
            	}
                
            } while (cursor.moveToNext());
        }
    	
		
		
		return doctor;
	}
	//temporary update function
	public void updateDoctor(String personnel_number, String username, String password)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "UPDATE " + table_name + " SET seg_username = '" + username + "', seg_password = '" + password + "' WHERE seg_personnel_number = '" + personnel_number + "'";
		db.execSQL(query);
		/*
		ContentValues values = new ContentValues();
		values.put(personnel_number, doctor.getPersonnelNumber());
		values.put(location_number, doctor.getLocationNumber());
		values.put(doctor_username, doctor.getDoctorUsername());
		values.put(doctor_password, doctor.getDoctorPassword());
		values.put(doctor_first_name, doctor.getDoctorFirstName());
		values.put(doctor_middle_name, doctor.getDoctorMiddleName());
		values.put(doctor_last_name, doctor.getDoctorLastName());
		
		return db.update(table_name, values, personnel_number + " = ?",
				new String[] { String.valueOf(doctor.getPersonnelNumber())});
			*/
		
	}
	
	public boolean checkDoctorCredentials(String username, String password){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT seg_username, seg_password FROM " + table_name + " WHERE seg_username = '" + username + "' AND seg_password = '" + password + "'";
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			return true;
		}
		else{
			return false;
		}
	}
	
}

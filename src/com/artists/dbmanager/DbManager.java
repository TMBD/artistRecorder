package com.artists.dbmanager;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.artists.beans.Artist;
import com.artists.beans.Langage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
	private static Connection connexion;
	private static ResultSet globalResultSet;
	
	private final static String ARTISTS_DB = DbConsts.artists_db.toString();
	private final static String ID = DbConsts.id.toString();
	private final static String ARTISTS_TB = DbConsts.artists_tb.toString();
	private final static String LANGAGES_TB = DbConsts.langages_tb.toString();
	private final static String NAME = DbConsts.name.toString();
	private final static String NUMBER = DbConsts.number.toString();
	private final static String LANGAGE = DbConsts.langage.toString();

	private final static String TABLE_ARTISTS_CREATER = "CREATE TABLE IF NOT EXISTS "+ARTISTS_TB+" (" + 
			"	"+ID+"	INTEGER NOT NULL PRIMARY KEY IDENTITY ," + 
			"	"+NAME+"	VARCHAR(200) NOT NULL ," + 
			"	"+NUMBER+"	INTEGER NOT NULL ," + 
			"	"+LANGAGE+"	VARCHAR(200) NOT NULL, "
					+ "UNIQUE("+NAME+"),"
					+ "UNIQUE("+NUMBER+"));";
	
	private final static String TABLE_LANGAGES_CREATER = "CREATE TABLE IF NOT EXISTS "+LANGAGES_TB+" (" + 
			"	"+ID+"	INTEGER NOT NULL PRIMARY KEY IDENTITY," + 
			"	"+LANGAGE+"	VARCHAR(200) NOT NULL, "
					+ "UNIQUE("+LANGAGE+"));";
	
	private static String getDbSelector(String whatToSelect, String tableName, String conditionClause) {
		return "SELECT " + whatToSelect + " FROM " + tableName + " WHERE " + conditionClause+" ;";
	}
	
	private static String getDbSelector(String whatToSelect, String tableName) {
		return "SELECT " + whatToSelect + " FROM " + tableName+" ;";
	}
	
	private static String getDbUpdater(String whatToSet, String tableName, String conditionClause) {
		return "UPDATE " + tableName + " SET " + whatToSet + " WHERE " + conditionClause+" ;";
	}
	
	private static String getDbInseter(String whatToInsert, String tableName, String valuesToInsert) {
		return "INSERT INTO " + tableName + " " + whatToInsert+ " VALUES "+valuesToInsert+" ;";
	}
	
	private static String getDbDeleter(String tableName, String conditionClause) {
		return "DELETE FROM " + tableName + " WHERE " + conditionClause+" ;";
	}
	
	private static boolean openConnectionToDb() {
		try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        }catch (Exception e) {
            return false;
        }
        try {
            connexion = DriverManager.getConnection("jdbc:hsqldb:file:DATABASE/"+ARTISTS_DB+";ifexists=true", "SA", "");
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
	}
	
	private static boolean closeConnectionToBd(){
        try {
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
	
	public static boolean dbExists() {
		if(openConnectionToDb()) {
			closeConnectionToBd();
			return true;
		}
		else return false;
	}
	
	public static boolean createDb() {
		try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        }catch (Exception e) {
            return false;
        }
        try {
            connexion = DriverManager.getConnection("jdbc:hsqldb:file:DATABASE/"+ARTISTS_DB, "SA", "");
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        closeConnectionToBd();
        boolean succededQueryForLangagesTable = dbExecuter(TABLE_LANGAGES_CREATER);
        
        if(succededQueryForLangagesTable) return dbExecuter(TABLE_ARTISTS_CREATER);
        return false;
	}
	
	
	private static boolean dbExecuter(String queryStr) {
		try {
			openConnectionToDb();
            Statement statement = connexion.createStatement();
            globalResultSet = statement.executeQuery(queryStr);
            
            statement.executeQuery("SHUTDOWN");
            statement.close();
            closeConnectionToBd();
            
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
		return true;
	}
	
	public static boolean insertIntoArtists_tb(Artist artist) {
		String whatToInsert = "(" + NAME + ", " + NUMBER + ", " + LANGAGE + ")";
		String valuesToInsert = "('" + artist.getName() + "', " + artist.getNumber() + ", '" + artist.getLangage() + "')";
		String queryStr = getDbInseter(whatToInsert, ARTISTS_TB, valuesToInsert);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean insertIntoLangages_tb(Langage langage) {
		String whatToInsert = "(" + LANGAGE + ")";
		String valuesToInsert = "('" + langage.getLangage() + "')";
		String queryStr = getDbInseter(whatToInsert, LANGAGES_TB, valuesToInsert);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean deleteFromArtists_tb(Artist artist) {
		String conditionClause = NAME + " = '"+artist.getName()+"' AND " + NUMBER + " = " + artist.getNumber();
		String queryStr = getDbDeleter(ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean deleteFromArtists_tb_ByName(String name) {
		String conditionClause = NAME + " = '"+name+"'" ;
		String queryStr = getDbDeleter(ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean deleteFromArtists_tb_ByNumber(long number) {
		String conditionClause = NUMBER + " = "+number;
		String queryStr = getDbDeleter(ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean deleteFromLangages_tb(Langage langage) {
		String conditionClause = LANGAGE + " = '"+langage.getLangage()+"'";
		String queryStr = getDbDeleter(LANGAGES_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean deleteFromLangages_tb_ByLangage(String langage) {
		String conditionClause = LANGAGE + " = '"+langage+"'" ;
		String queryStr = getDbDeleter(LANGAGES_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	
	public static boolean updateArtists_tb(Artist oldArtist, Artist newArtist) {
		String whatToSet = NAME + " = '"+newArtist.getName()+"' , " + NUMBER + " = " + newArtist.getNumber();
		String conditionClause = NAME + " = '"+oldArtist.getName()+"' AND " + NUMBER + " = " + oldArtist.getNumber();
		String queryStr = getDbUpdater(whatToSet, ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static boolean updateLangages_tb(Langage  oldLangage, Langage  newLangage) {
		String whatToSet = LANGAGE + " = '"+newLangage.getLangage()+"'";
		String conditionClause = LANGAGE + " = '"+oldLangage.getLangage()+"'";
		String queryStr = getDbUpdater(whatToSet, LANGAGES_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		return succededQuery;
	}
	
	public static Artist selectArtist(Artist artist) {
		String whatToSelect = "*";
		String conditionClause = NAME + " = '"+artist.getName()+"' AND " + NUMBER + " = " + artist.getNumber();
		String queryStr = getDbSelector(whatToSelect, ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		
		try {
			if(resultSet.next()){
				Langage langage = new Langage(resultSet.getString(LANGAGE));
				return new Artist(resultSet.getString(NAME), resultSet.getLong(NUMBER), langage) ;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	
	public static Artist selectArtistByName(String name) {
		String whatToSelect = "*";
		String conditionClause = NAME + " = '"+name+"'";
		String queryStr = getDbSelector(whatToSelect, ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		
		try {
			if(resultSet.next()){
				Langage langage = new Langage(resultSet.getString(LANGAGE));
				return new Artist(resultSet.getString(NAME), resultSet.getLong(NUMBER), langage) ;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	public static Artist selectArtistByNumber(long number) {
		String whatToSelect = "*";
		String conditionClause = NUMBER + " = '"+number+"'";
		String queryStr = getDbSelector(whatToSelect, ARTISTS_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		
		try {
			if(resultSet.next()){
				Langage langage = new Langage(resultSet.getString(LANGAGE));
				return new Artist(resultSet.getString(NAME), resultSet.getLong(NUMBER), langage) ;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	public static ArrayList<Artist> selectAllArtists() {
		String whatToSelect = "*";
		String queryStr = getDbSelector(whatToSelect, ARTISTS_TB);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		ArrayList<Artist> allArtists = new ArrayList<Artist>();
		
		try {
			while(resultSet.next()){
				Langage langage = new Langage(resultSet.getString(LANGAGE));
				Artist artist = new Artist(resultSet.getString(NAME), resultSet.getLong(NUMBER), langage);
				allArtists.add(artist);
			}
			return allArtists;
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	public static Langage selectLangage(Langage langage) {
		String whatToSelect = "*";
		String conditionClause = LANGAGE + " = '"+langage.getLangage()+"'";
		String queryStr = getDbSelector(whatToSelect, LANGAGES_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		
		try {
			if(resultSet.next()){
				return new Langage(resultSet.getString(LANGAGE));
			}
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	public static Langage selectLangage(String langage) {
		String whatToSelect = "*";
		String conditionClause = LANGAGE + " = '"+langage+"'";
		String queryStr = getDbSelector(whatToSelect, LANGAGES_TB, conditionClause);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		
		try {
			if(resultSet.next()){
				return new Langage(resultSet.getString(LANGAGE));
			}
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	
	public static ArrayList<Langage> selectAllLangages() {
		String whatToSelect = "*";
		String queryStr = getDbSelector(whatToSelect, LANGAGES_TB);
		boolean succededQuery = dbExecuter(queryStr);
		if(succededQuery == false) return null;
		ResultSet resultSet = globalResultSet;
		ArrayList<Langage> allLangages = new ArrayList<Langage>();
		
		try {
			while(resultSet.next()){
				Langage langage = new Langage(resultSet.getString(LANGAGE));
				allLangages.add(langage);
			}
			return allLangages;
		} catch (SQLException ex) {
			Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
		}
            
    	return null;
	}
	
	
	
	
	

}

package com.artists.beans;
import java.util.ArrayList;

import com.artists.dbmanager.DbManager;

public class Langage {
	private String langage;

	
	public Langage(String langage) {
		this.langage = langage;
	}
	
	public String getLangage() {
		return langage;
	}

	public void setLangage(String langage) {
		this.langage = langage;
	}
	
	
	public boolean addToDb() {
		return DbManager.insertIntoLangages_tb(this);
	}
	
	public boolean deleteFromDb() {
		return DbManager.deleteFromLangages_tb(this);
	}
	
	public boolean deleteFromDbByLangage(String langage) {
		return DbManager.deleteFromLangages_tb_ByLangage(langage);
	}
	
	public boolean updateInDb(Langage oldLangage) {
		return DbManager.updateLangages_tb(oldLangage, this);
	}
	
	public static Langage search(Langage langage) {
		return DbManager.selectLangage(langage);
	}
	
	public static Langage searchByLangage(String langage) {
		return DbManager.selectLangage(langage);
	}
	
	public static ArrayList<Langage> searchAllLangages() {
		return DbManager.selectAllLangages();
	}
	
	
	public boolean equals(Langage l) {
		return this.langage.equalsIgnoreCase(l.langage);
	}
	
	@Override
	public String toString() {
		return this.langage;
	}
	
}

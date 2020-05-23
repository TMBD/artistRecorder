package com.artists.beans;
import java.util.ArrayList;

import com.artists.dbmanager.DbManager;

public class Artist {
	private String name;
	private long number;
	private Langage langage;
	
	public Artist(String name, long number, Langage langage) {
		super();
		this.name = name;
		this.number = number;
		this.langage = langage;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public Langage getLangage() {
		return langage;
	}
	public void setLangage(Langage langage) {
		this.langage = langage;
	}
	
	
	
	public boolean addToDb() {
		return DbManager.insertIntoArtists_tb(this);
	}
	
	public boolean deleteFromDb() {
		return DbManager.deleteFromArtists_tb(this);
	}
	
	public boolean deleteFromDbByName(String name) {
		return DbManager.deleteFromArtists_tb_ByName(name);
	}
	
	public boolean deleteFromDbByNumber(long number) {
		return DbManager.deleteFromArtists_tb_ByNumber(number);
	}
	
	public boolean updateInDb(Artist oldArtist) {
		return DbManager.updateArtists_tb(oldArtist, this);
	}
	
	public static Artist search(Artist artist) {
		return DbManager.selectArtist(artist);
	}
	
	public static Artist searchByName(String name) {
		return DbManager.selectArtistByName(name);
	}
	
	public static Artist searchByNumber(long number) {
		return DbManager.selectArtistByNumber(number);
	}
	
	public static ArrayList<Artist> searchAllArtists() {
		return DbManager.selectAllArtists();
	}
	
}

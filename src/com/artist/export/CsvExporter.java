package com.artist.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.artists.beans.Artist;

public class CsvExporter {

	private static final String fileName = "Artists.csv";
	private static final String numberCol = "Number";
	private static final String nameCol = "Name";
	private static final String langageCol = "Langage";
	
	public static boolean createCsvFile(ArrayList<Artist> artists) {
		PrintWriter writer = null;
		try{
			  writer = new PrintWriter(new File(fileName));
			  StringBuilder sb = new StringBuilder();
			  sb.append(numberCol+",");
			  sb.append(nameCol+",");
			  sb.append(langageCol);
			  sb.append('\n');
			
			  sb.append(createCsvString(artists));
			
			  writer.write(sb.toString());
			  writer.close();
			  System.out.println("done !");
			  
	    } catch (FileNotFoundException e) {
	      System.out.println(e.getMessage());
	      return false;
	    }
		return true;
		
	}
	
	private static String createCsvString(ArrayList<Artist> artists) {
		String csvString = "";
		for (Artist artist : artists) {
			csvString = csvString + artist.getNumber()+","+artist.getName()+","+artist.getLangage().getLangage()+"\n" ;
		}
		
		return csvString;
	}
	
}

package com.artists.front.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;

import com.artist.export.CsvExporter;
import com.artists.beans.*;
import com.artists.dbmanager.DbManager;

public class MainWindow {

	private JFrame frame;
	private JTextField tf_VoiceNumber;
	private JTextField tf_Name;
	private JTextField tf_AddLangage;
	
	private JPanel panel_ManageLangage;
	private Panel panel_SearchArtistBy;
	private Panel panel_ArtistFields;
	
	private JComboBox<String> cb_DeleteLangage;
	private JComboBox<String> cb_Langage;
	
	private JLabel lbl_ArtistDetails_Title;
	private JLabel lbl_ManageLanagages_Title;
	
	ButtonGroup rdbtngrp_SearchArtistBy = new ButtonGroup();
	JRadioButton rdbtn_SearchArtistByVoiceId;
	JRadioButton rdbtn_SearchArtistByName;
	
	private ArrayList<Langage> allLangages = new ArrayList<Langage>();
	private boolean dbExisted;
	private boolean isSearchingArtist = true;
	private JTextField tf_searchLangage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 480, 596);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JButton btn_SearchArtist = new JButton("Search artist");
		btn_SearchArtist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_ManageLangage.setVisible(false);
				rdbtn_SearchArtistByName.setVisible(true);
				rdbtn_SearchArtistByVoiceId.setVisible(true);
				panel_SearchArtistBy.setVisible(true);
				panel_ArtistFields.setVisible(true);
				tf_searchLangage.setVisible(true);
				cb_Langage.setVisible(false);
				isSearchingArtist = true;
				lbl_ArtistDetails_Title.setText("SEARCH ARTIST");
				
			}
		});
		btn_SearchArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(btn_SearchArtist);
		
		JButton btn_AddArtist = new JButton("Add artist");
		btn_AddArtist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_SearchArtistBy.setVisible(false);
				panel_ManageLangage.setVisible(false);
				rdbtn_SearchArtistByName.setVisible(false);
				rdbtn_SearchArtistByVoiceId.setVisible(false);
				panel_ArtistFields.setVisible(true);
				tf_searchLangage.setVisible(false);
				cb_Langage.setVisible(true);
				isSearchingArtist = false;
				lbl_ArtistDetails_Title.setText("ADD ARTIST");
				tf_searchLangage.setText("");
				
//				for (int i = 1000; i < 10000; i++) { //887
//					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//					Artist a = new Artist("Artist name "+i+" - "+timestamp, i, new Langage("English"));
//					if(a.addToDb()) System.out.println(i);
//				}
			}
		});
		btn_AddArtist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(btn_AddArtist);
		
		JButton btn_ManageLangage = new JButton("Manage languages");
		btn_ManageLangage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_SearchArtistBy.setVisible(false);
				panel_ArtistFields.setVisible(false);
				rdbtn_SearchArtistByName.setVisible(false);
				rdbtn_SearchArtistByVoiceId.setVisible(false);
				panel_ManageLangage.setVisible(true);
				tf_searchLangage.setText("");
			}
		});
		btn_ManageLangage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(btn_ManageLangage);
		
		JButton btn_ExportData = new JButton("Export data");
		btn_ExportData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<Artist> allArtists = Artist.searchAllArtists();
				if(allArtists != null) {
					if(CsvExporter.createCsvFile(allArtists)) {
						JOptionPane.showMessageDialog(null, "Artists has been exported successfully !", "Info", JOptionPane.INFORMATION_MESSAGE);
						tf_searchLangage.setText("");
					}else JOptionPane.showMessageDialog(null, "An error has occured while trying to create the file ! \n Please make sure that it isn't open or being used by another process !", "Error", JOptionPane.ERROR_MESSAGE);
				}else JOptionPane.showMessageDialog(null, "An error has occured while trying to access the database !", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		btn_ExportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(btn_ExportData);
		frame.getContentPane().setLayout(null);
		
		panel_ArtistFields = new Panel();
		panel_ArtistFields.setBounds(0, 0, 478, 523);
		frame.getContentPane().add(panel_ArtistFields);
		panel_ArtistFields.setLayout(null);
		
		JLabel lbl_VoiceNumber = new JLabel("Voice number");
		lbl_VoiceNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_VoiceNumber.setBounds(49, 100, 109, 24);
		panel_ArtistFields.add(lbl_VoiceNumber);
		
		JLabel lbl_Name = new JLabel("Name");
		lbl_Name.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Name.setBounds(49, 229, 109, 24);
		panel_ArtistFields.add(lbl_Name);
		
		JLabel lbl_Langage = new JLabel("Language");
		lbl_Langage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_Langage.setBounds(49, 363, 75, 24);
		panel_ArtistFields.add(lbl_Langage);
		
		tf_VoiceNumber = new JTextField();
		tf_VoiceNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_VoiceNumber.setBounds(49, 128, 370, 35);
		panel_ArtistFields.add(tf_VoiceNumber);
		tf_VoiceNumber.setColumns(10);
		
		cb_Langage = new JComboBox<String>();
		cb_Langage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cb_Langage.setBounds(49, 389, 370, 35);
		cb_Langage.setVisible(false);
		panel_ArtistFields.add(cb_Langage);
		
		tf_Name = new JTextField();
		tf_Name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_Name.setBounds(49, 256, 370, 35);
		panel_ArtistFields.add(tf_Name);
		tf_Name.setColumns(10);
		
		lbl_ArtistDetails_Title = new JLabel("SEARCH ARTIST");
		lbl_ArtistDetails_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ArtistDetails_Title.setFont(new Font("Tahoma", Font.BOLD, 25));
		lbl_ArtistDetails_Title.setBounds(0, 0, 478, 58);
		panel_ArtistFields.add(lbl_ArtistDetails_Title);
		
		JButton btn_ValidateArtistField = new JButton("Validate");
		btn_ValidateArtistField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = tf_Name.getText();
				String numberStr = tf_VoiceNumber.getText();
				Long number;
				Artist artist = null;
				if(isSearchingArtist) {
					if(rdbtn_SearchArtistByName.isSelected()) {
						if(isBlanc(name) == false) {
							artist = Artist.searchByName(name);
							if(artist != null) {
								tf_VoiceNumber.setText(String.valueOf(artist.getNumber()));
								tf_Name.setText(artist.getName());
								tf_searchLangage.setText(artist.getLangage().getLangage());
							}else JOptionPane.showMessageDialog(null, "No artist named "+name+" has been found !", "Info", JOptionPane.INFORMATION_MESSAGE);
							
						}else JOptionPane.showMessageDialog(null, "Please fill the name of the artist you are searching for !", "Alert", JOptionPane.WARNING_MESSAGE);
						
					}
					else{
						if(isBlanc(numberStr) == false) {
							try{
								number = Long.parseLong(numberStr);
								artist = Artist.searchByNumber(number);
								if(artist != null) {
									tf_VoiceNumber.setText(String.valueOf(artist.getNumber()));
									tf_Name.setText(artist.getName());
									tf_searchLangage.setText(artist.getLangage().getLangage());
								}else JOptionPane.showMessageDialog(null, "No artist with the voice number "+numberStr+" has been found !", "Info", JOptionPane.INFORMATION_MESSAGE);
							} catch (NumberFormatException  ex) {
								JOptionPane.showMessageDialog(null, "Please fill a correct artist voice number !", "Alert", JOptionPane.WARNING_MESSAGE);
							}
						}else JOptionPane.showMessageDialog(null, "Please fill the voce number of the artist you are searching for !", "Alert", JOptionPane.WARNING_MESSAGE);
					}
					
				} 
				else { //isSearchingArtist == false (i.e. add artist)
					if(isBlanc(numberStr) == false && isBlanc(name) == false) {
						if(cb_Langage.getSelectedIndex()>-1) {
							String langageStr = cb_Langage.getSelectedItem().toString();
							try{
								number = Long.parseLong(numberStr);
								
								if(Artist.searchByNumber(number) == null) {
									if(Artist.searchByName(name) == null) {
										artist = new Artist(name, number, new Langage(langageStr));
										if(artist.addToDb()) {
											JOptionPane.showMessageDialog(null, "Artist successfully added !", "Info", JOptionPane.INFORMATION_MESSAGE);
											tf_VoiceNumber.setText("");
											tf_Name.setText("");
										}else JOptionPane.showMessageDialog(null, "An error has occured while adding the artist to the database !", "Alert", JOptionPane.ERROR_MESSAGE);
								
									}else JOptionPane.showMessageDialog(null, "An artist named "+name+" already  exists !", "Alert", JOptionPane.WARNING_MESSAGE);
								}else JOptionPane.showMessageDialog(null, "An artist with the voice number "+numberStr+" already  exists !", "Alert", JOptionPane.WARNING_MESSAGE);
							
							} catch (NumberFormatException  ex) {
								JOptionPane.showMessageDialog(null, "Please fill a correct artist voice number !", "Alert", JOptionPane.WARNING_MESSAGE);
							}
						}else JOptionPane.showMessageDialog(null, "The language you selected doesn't existe, you have to add it before !", "Alert", JOptionPane.WARNING_MESSAGE);
					}else JOptionPane.showMessageDialog(null, "Please fill correctly all the fields !", "Alert", JOptionPane.WARNING_MESSAGE);				
				}
			}
		});
		btn_ValidateArtistField.setForeground(new Color(0, 102, 0));
		btn_ValidateArtistField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
			} 
		});
		btn_ValidateArtistField.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_ValidateArtistField.setBounds(49, 461, 140, 35);
		panel_ArtistFields.add(btn_ValidateArtistField);
		
		rdbtn_SearchArtistByVoiceId = new JRadioButton("Search by voice number");
		rdbtn_SearchArtistByVoiceId.setBounds(264, 103, 159, 21);
		panel_ArtistFields.add(rdbtn_SearchArtistByVoiceId);
		rdbtn_SearchArtistByVoiceId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtn_SearchArtistByVoiceId.addMouseListener(new MouseAdapter() {
		});
		rdbtn_SearchArtistByVoiceId.setSelected(true);
		rdbtngrp_SearchArtistBy.add(rdbtn_SearchArtistByVoiceId);
		
		rdbtn_SearchArtistByName = new JRadioButton("Search by name");
		rdbtn_SearchArtistByName.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtn_SearchArtistByName.setBounds(309, 232, 117, 21);
		panel_ArtistFields.add(rdbtn_SearchArtistByName);
		rdbtn_SearchArtistByName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtn_SearchArtistByName.addMouseListener(new MouseAdapter() {
		});
		rdbtngrp_SearchArtistBy.add(rdbtn_SearchArtistByName);
		
		panel_SearchArtistBy = new Panel();
		panel_SearchArtistBy.setBounds(309, 449, 126, 64);
		panel_ArtistFields.add(panel_SearchArtistBy);
		panel_SearchArtistBy.setLayout(null);
		
		JButton btn_DeleteArtist = new JButton("Delete artist");
		btn_DeleteArtist.setBounds(10, 20, 106, 21);
		panel_SearchArtistBy.add(btn_DeleteArtist);
		btn_DeleteArtist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = tf_Name.getText();
				String numberStr = tf_VoiceNumber.getText();
				Long number;
				Artist artist;
				String langageStr = cb_Langage.getSelectedItem().toString();
				try{
					number = Long.parseLong(numberStr);
					artist = new Artist(name, number, new Langage(langageStr));
					if(Artist.search(artist) != null) {
						int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this artist ?", "Alert", JOptionPane.YES_NO_OPTION);
						if(choice == JOptionPane.OK_OPTION) {
							if(artist.deleteFromDb()) {
								JOptionPane.showMessageDialog(null, "The artist has been deleted successfully !", "Info", JOptionPane.INFORMATION_MESSAGE);
								tf_Name.setText("");
								tf_VoiceNumber.setText("");
							}else JOptionPane.showMessageDialog(null, "An error has occured while deleting the artist from the database !", "Info", JOptionPane.ERROR_MESSAGE);
						}
					}else JOptionPane.showMessageDialog(null, "This artist doesn't exist !", "Alert", JOptionPane.WARNING_MESSAGE);
				} catch (NumberFormatException  ex) {
					JOptionPane.showMessageDialog(null, "Please fill a correct artist voice number !", "Alert", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btn_DeleteArtist.setForeground(new Color(255, 0, 0));
		
		tf_searchLangage = new JTextField();
		tf_searchLangage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_searchLangage.setColumns(10);
		tf_searchLangage.setBounds(49, 389, 370, 35);
		panel_ArtistFields.add(tf_searchLangage);
		
		panel_ManageLangage = new JPanel();
		panel_ManageLangage.setBounds(0, 0, 478, 523);
		frame.getContentPane().add(panel_ManageLangage);
		panel_ManageLangage.setLayout(null);
		panel_ManageLangage.setVisible(false);
		
		JButton btn_AddLangage = new JButton("Add language");
		btn_AddLangage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isBlanc(tf_AddLangage.getText())==false) {
					Langage langage = new Langage(tf_AddLangage.getText());
					
					if(containsLangage(langage) == false) {
						if(langage.addToDb()) {
							JOptionPane.showMessageDialog(null, "The language hase been successfully added !", "Info", JOptionPane.INFORMATION_MESSAGE);
							allLangages.add(langage);
							loadLangageToLangageBoxes(langage);
							tf_AddLangage.setText("");
						}
						else JOptionPane.showMessageDialog(null, "An error has occured while trying to add the language !", "Error", JOptionPane.ERROR_MESSAGE);
					}else JOptionPane.showMessageDialog(null, "The language already exists !", "Alert", JOptionPane.WARNING_MESSAGE);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "The language input is empty !", "Alert", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		btn_AddLangage.setForeground(new Color(34, 139, 34));
		btn_AddLangage.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_AddLangage.setBounds(51, 185, 140, 35);
		panel_ManageLangage.add(btn_AddLangage);
		
		JButton btn_DeleteLangage = new JButton("Delete");
		btn_DeleteLangage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Langage langage = new Langage(cb_DeleteLangage.getSelectedItem().toString());
				if(containsLangage(langage) == true) {
					int choice = JOptionPane.showConfirmDialog(null, "Do you really want to delete this language ?", "Alert", JOptionPane.YES_NO_OPTION);
					if(choice == JOptionPane.OK_OPTION) {
						if(langage.deleteFromDb()) {
							JOptionPane.showMessageDialog(null, "The language hase been added successfully deleted !", "Info", JOptionPane.INFORMATION_MESSAGE);
							removeLangageFromAllLangages(langage);
							loadAllLangagesToLangageBoxes(allLangages);
						}
						else JOptionPane.showMessageDialog(null, "An error has occured while trying to delete the language from the database !", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else JOptionPane.showMessageDialog(null, "This language doesn't exist !", "Alert", JOptionPane.WARNING_MESSAGE);;
			}
		});
		btn_DeleteLangage.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_DeleteLangage.setForeground(Color.RED);
		btn_DeleteLangage.setBounds(51, 461, 140, 35);
		panel_ManageLangage.add(btn_DeleteLangage);
		
		tf_AddLangage = new JTextField();
		
		tf_AddLangage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_AddLangage.setBounds(51, 128, 370, 35);
		panel_ManageLangage.add(tf_AddLangage);
		tf_AddLangage.setColumns(10);
		
		cb_DeleteLangage = new JComboBox<String>();
		cb_DeleteLangage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cb_DeleteLangage.setBounds(51, 389, 370, 35);
		panel_ManageLangage.add(cb_DeleteLangage);
		
		JLabel lbl_AddLangage = new JLabel("Language to add");
		lbl_AddLangage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_AddLangage.setBounds(51, 99, 152, 29);
		panel_ManageLangage.add(lbl_AddLangage);
		
		JLabel lbl_DeleteLangage = new JLabel("Language to delete");
		lbl_DeleteLangage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_DeleteLangage.setBounds(51, 359, 170, 29);
		panel_ManageLangage.add(lbl_DeleteLangage);
		
		lbl_ManageLanagages_Title = new JLabel("MANAGE LANGAGES");
		lbl_ManageLanagages_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ManageLanagages_Title.setFont(new Font("Tahoma", Font.BOLD, 25));
		lbl_ManageLanagages_Title.setBounds(0, 0, 478, 58);
		panel_ManageLangage.add(lbl_ManageLanagages_Title);
		
		rdbtngrp_SearchArtistBy = new ButtonGroup();
		
		this.dbExisted = DbManager.dbExists();
		if(this.dbExisted==false) {
			int choice = JOptionPane.showConfirmDialog(null, "It seems that the database doesn't exist. Do you want to create automatically a new one ?", "Alert", JOptionPane.YES_NO_OPTION);
			if(choice == JOptionPane.OK_OPTION) {
				if(DbManager.createDb()) {
					JOptionPane.showMessageDialog(null, "The databse has been created successfully !", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(null, "An error has occured while creating the database !", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(this.dbExisted) {
			allLangages = Langage.searchAllLangages();
			loadAllLangagesToLangageBoxes(allLangages);
		}
	}
	
	private void loadAllLangagesToLangageBoxes(ArrayList<Langage> langages) {
		cb_DeleteLangage.removeAllItems();
		cb_Langage.removeAllItems();
		
		for (Langage l : langages) {
			cb_DeleteLangage.addItem(l.getLangage());
			cb_Langage.addItem(l.getLangage());
		}
	}
	
	private void loadLangageToLangageBoxes(Langage l) {
		cb_DeleteLangage.addItem(l.getLangage());
		cb_Langage.addItem(l.getLangage());
	}
	
	
	private boolean containsLangage(Langage l) {
		for (Langage langage : allLangages) {
			if(langage.equals(l)) return true;
		}
		return false;
	}
	
	private boolean removeLangageFromAllLangages(Langage l) {
		for (int i = 0; i < allLangages.size(); i++) {
			if(allLangages.get(i).equals(l)) {
				allLangages.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public static boolean isBlanc(String str) {
		if(str.length() <= 0) return true;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != ' ') return false;
		}
		return true;
	}
}

package fvsl.memory.client.ui;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import fvsl.memory.client.ui.Request.LobbyCreationResult;


public class CreateLobbyPageView extends Page {

	private CreateLobbyPageModel model;
	private CreateLobbyPageController controller;
	
	public CreateLobbyPageView(){
		super();
	}

	private JButton creaButton;
	private	JButton backButton;
	private JTextField lobbyNameField;
	private JTextField passwordField;
	private JComboBox nGCombo;
	private JComboBox nCoppieCombo;
	private JComboBox timerCombo;
	
	@Override
	protected void loadComponents() {
		JPanel pannello=new JPanel();
		add(pannello);
		pannello.setLayout(new GridLayout(6,2,55,55));
		
		lobbyNameField=new JTextField();
		lobbyNameField.setColumns(10);
		
		
		JLabel nGiocatoriLabel= new JLabel("numero giocatori");
		
		JLabel nCoppieLabel= new JLabel("numero coppie");
		
		JLabel timerLabel= new JLabel("timer");
		
		JLabel passwordLabel= new JLabel("inserire Password");
		
		
		passwordField=new JTextField();
		passwordField.setColumns(10);
		
		//I valori di default dovrebbe prenderli da server
		nGCombo= new JComboBox();
		nCoppieCombo= new JComboBox();
		timerCombo= new JComboBox();
		
		creaButton= new JButton("Crea Stanza");
		backButton=new JButton("Torna Indietro");
		
		pannello.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pannello.add(new JLabel("createLobbyPage"));
		pannello.add(lobbyNameField);
		pannello.add(nGiocatoriLabel);
		pannello.add(nGCombo);
		pannello.add(nCoppieLabel);
		pannello.add(nCoppieCombo);
		pannello.add(timerLabel);
		pannello.add(timerCombo);
		pannello.add(passwordLabel);
		pannello.add(passwordField);
		pannello.add(creaButton);
		pannello.add(backButton);
		
	
		
	}
	
	@Override
	protected void setUpListeners() {
		
		lobbyNameField.getDocument().addDocumentListener(new DocumentListener() { 
			  public void changedUpdate(DocumentEvent e) {
				  	  notifyProperty(); 
				  } 
				  public void removeUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				  public void insertUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				 
				  public void notifyProperty() { 
				     model.getLobby().setName(lobbyNameField.getText());
				  } 
		});
		
		passwordField.getDocument().addDocumentListener(new DocumentListener() { 
			  public void changedUpdate(DocumentEvent e) {
				  	  notifyProperty(); 
				  } 
				  public void removeUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				  public void insertUpdate(DocumentEvent e) {
					  notifyProperty(); 
				  } 
				 
				  public void notifyProperty() { 
				     model.setPassword(passwordField.getText());
				  } 
		});
		
		nGCombo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        model.getLobby().setNumberOfPlayers(Integer.parseInt((String)nGCombo.getSelectedItem()));
		    }
		});
			
		nCoppieCombo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        model.getLobby().setNumberOfPlayers(Integer.parseInt((String)nCoppieCombo.getSelectedItem()));
		    }
		});
		
		timerCombo.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        model.getLobby().setNumberOfPlayers(Integer.parseInt((String)timerCombo.getSelectedItem()));
		    }
		});
		
		creaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LobbyCreationResult result = 
				controller.attemptToCreateLobby(model.getLobby(), model.getPassword());
				
				if (!(result == LobbyCreationResult.Accepted)){
					JOptionPane.showMessageDialog(container, "Unable to join lobby: " + result.toString());
				}
			}
		});
	}

	@Override
	protected void loadData() {
		model = new CreateLobbyPageModel();
		model.setLobby(new Lobby("New Lobby", model.getPossiblePlayers()[0], model.getPossiblePairs()[0], model.getPossibleTimers()[0], null));
		controller = new CreateLobbyPageController();
		//Prendi valori di "default" qui (dal controller) e mettili nel model (vanno creati altri campi)
	}

	@Override
	protected void populateViews() {
		//Imposta i valori qui (li prendi dal model e li metti nella view)
		lobbyNameField.setText(model.getLobby().getName());
		passwordField.setText(model.getPassword());
		nGCombo.setModel(new DefaultComboBoxModel<Integer>(model.getPossiblePlayers()));
		nCoppieCombo.setModel(new DefaultComboBoxModel<Integer>(model.getPossiblePairs()));
		timerCombo.setModel(new DefaultComboBoxModel<Integer>(model.getPossibleTimers()));
	}

}

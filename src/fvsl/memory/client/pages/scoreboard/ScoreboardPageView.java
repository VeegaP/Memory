package fvsl.memory.client.pages.scoreboard;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import fvsl.memory.client.pages.Page;
import fvsl.memory.client.pages.game.GamePageController;
import fvsl.memory.client.pages.game.GamePageModel;
import fvsl.memory.common.entities.Lobby;
import fvsl.memory.common.entities.Player;
import fvsl.memory.common.util.StringResources;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Vector;

public class ScoreboardPageView extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9192421170975614105L;

	public ScoreboardPageView(Lobby lobby) {
		super(lobby);
		controller = new ScoreboardPageController();
	}
	private ScoreboardPageModel model;
	private Lobby bufferLobby;
	private ScoreboardPageController controller;
	@Override
	protected void bufferize(Object o) {
		bufferLobby = (Lobby) o;
	}
	private JButton backButton;
	private JTable scoreTable;
	@Override
	protected void loadComponents() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		add(panel);
		JPanel Panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		scoreTable=new JTable();
		panel.add(Box.createRigidArea(new Dimension(350,30)));
		panel.add(new JScrollPane(scoreTable));
		panel.add(Box.createRigidArea(new Dimension(350,30)));
		backButton=new JButton("back");
		panel.add(Box.createVerticalGlue());
		panel.add(backButton);
	}

	@Override
	protected void setUpListeners() {
		// TODO Auto-generated method stub
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				controller.backToMainPage();

			}
		});

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		model = new ScoreboardPageModel();
		model.setLobby(bufferLobby);
	}

	@Override
	protected void populateViews() {
		TableModel tableModel = new PlayersTableModel(model.getLobby().getConnectedPlayers());
		scoreTable.setModel(tableModel);

	}

	protected class PlayersTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		private Vector<Player> list = new Vector<Player>();
		private String[] columnNames = { StringResources.player.toString(), StringResources.score.toString()};

		public PlayersTableModel(Vector<Player> list) {
			this.list = list;
			this.list.sort(new Comparator<Player>() {
				@Override
				public int compare(Player p1, Player p2) {
					return p1.getScore() < p2.getScore() ? 1 : p1.getScore() > p2.getScore() ? -1 : 0;
				}
			});
			
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return list.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Player player = list.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return player.getName();
			case 1:
				return player.getScore();
			}
			return null;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Integer.class;
			}
			return null;
		}
	}

	@Override
	protected void onExit() {
		// TODO Auto-generated method stub
		
	}
}
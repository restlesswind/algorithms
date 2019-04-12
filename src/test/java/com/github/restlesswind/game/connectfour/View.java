package com.github.restlesswind.game.connectfour;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JFrame {

	private JLabel[][] texts;
	private JPanel scoresPane, mainPane;// create Panels
	private JLabel labelScores, textScores, textSteps; // create labels
	Font font = new Font("", Font.BOLD, 14); // set 2 type of font size and the color
	Font font2 = new Font("", Font.BOLD, 30);
	private int scores = 0;
	private ConnectFour game;

	public View(ConnectFour game) {
		this.game = game;
		setResizable(true);
		getContentPane().setLayout(null);
		setBounds(500, 50, 550, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Court");

		scoresPane = new JPanel();
		scoresPane.setBackground(Color.green);
		scoresPane.setBounds(20, 20, 460, 40);

		getContentPane().add(scoresPane);
		scoresPane.setLayout(null);

		labelScores = new JLabel("Scores:"); // add the word :score to the panel
		labelScores.setFont(font);
		labelScores.setBounds(0, 5, 100, 30); // set the size and location
		scoresPane.add(labelScores);// add the label to the panel

		textScores = new JLabel(String.valueOf(scores)); // print the player's score to the panel
		textScores.setFont(font);
		textScores.setBounds(300, 5, 150, 30);
		scoresPane.add(textScores);
		mainPane = new JPanel(); // set main panel
		mainPane.setBounds(20, 70, 460, 500);

		this.getContentPane().add(mainPane);
		mainPane.setLayout(new GridLayout(ConnectFour.rows + 2, ConnectFour.cols));

		JPanel bottomPanel = new JPanel();
		textSteps = new JLabel("Steps:");

		texts = new JLabel[ConnectFour.rows][ConnectFour.cols];// declare the array as the block for the game
		for (int y = 0; y < ConnectFour.rows; y++) {
			for (int x = 0; x < ConnectFour.cols; x++) {
				texts[y][x] = new JLabel(); // get new label

				texts[y][x].setBounds(60 * y, 60 * x, 60, 60); // set the size and spot for the square
				// setColor(i, j, ""); //set the background color
				texts[y][x].setOpaque(true);
				texts[y][x].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));// set color for border
				mainPane.add(texts[y][x]);
			}
		}

		for (int i = 0; i < ConnectFour.cols; i++) {
			JButton button = new JButton("Add");
			button.addActionListener(new ButtonListener(i, MoveType.ADD));
			mainPane.add(button);
		}

		for (int i = 0; i < ConnectFour.cols; i++) {
			JButton button = new JButton("Remove");
			button.addActionListener(new ButtonListener(i, MoveType.REMOVE));
			mainPane.add(button);
		}
	
		setVisible(true);

	}

	class ButtonListener implements ActionListener {
		private int col = 0;
		// add: type = 0; remove: type = 1
		private MoveType type = MoveType.ADD;

		ButtonListener(int col, MoveType type) {
			this.col = col;
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			game.play(col, type);
		}
	}

	public void redraw(Player[][] board) {
		for (int y = 0; y < Board.ROWS; y++) {
			for (int x = 0; x < Board.COLS; x++) {
				switch (board[y][x]) {
				case EMPTY:
					texts[y][x].setBackground(Color.WHITE);
					break;
				case HUMAN:
					texts[y][x].setBackground(Color.RED);
					break;
				case AI:
					texts[y][x].setBackground(Color.YELLOW);
					break;
				}
			}
		}
	}

}

/**
 * A tool to help children practice basic mathematical operations
 * @author Alexander Shmakov
 * @version 2.0
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.PrintStream;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class BasicMath {
	
	private final int RANGE = 10;
	private final String[] signs = { "+", "-", "*", "/" };
	private JFrame frame;
	private JButton checkButton;
	private JButton restart;
	private JPanel allPanels;
	private JPanel display;
	private JPanel input;
	private JTextField answerField;
	private JTextField questionField;
	private JLabel questionDisplay;
	private JLabel messageDisplay;
	private Border border;
	private Border tb;
	private int first;
	private int second;
	private Random rand;
	private String[] message;
	private String question;
	
	public BasicMath() {
		this.frame = new JFrame("Basic Math");
		this.frame.setPreferredSize(new Dimension(400, 400));
		this.frame.setDefaultCloseOperation(3);
		
		this.restart = new JButton("restart");
		this.restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				BasicMath.this.question = makeQuestion();
				System.out.println("new first= " + BasicMath.this.first + " new second = " + BasicMath.this.second);
				BasicMath.this.questionDisplay.setText(BasicMath.this.question);
				BasicMath.this.messageDisplay.setText("");
				BasicMath.this.answerField.setText("");
			}
		});
		this.checkButton = new JButton("Check Answer");
		this.checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {

				if (BasicMath.this.filter(BasicMath.this.answerField.getText())) {
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[2]);
				}
				else if (BasicMath.this.result(BasicMath.this.question) == Integer.parseInt(BasicMath.this.answerField.getText())) {
					System.out.println("what we get after result(ansField)= " + BasicMath.this.result(BasicMath.this.question));
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[0]);
					BasicMath.this.question = makeQuestion();
					System.out.println("new first= " + BasicMath.this.first + " new second = " + BasicMath.this.second);
					BasicMath.this.questionDisplay.setText(BasicMath.this.question);
				}
				else if (BasicMath.this.result(BasicMath.this.question) != Integer.parseInt(BasicMath.this.answerField.getText())) {
					System.out.println("what we get after result(ansField)= " + BasicMath.this.result(BasicMath.this.question));
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[1]);
				}
				else {
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[2]);
				}
				BasicMath.this.answerField.setText("");
			}
		});

		this.rand = new Random();
		this.question = makeQuestion();
		System.out.println("new first= " + BasicMath.this.first + " new second = " + BasicMath.this.second);
		this.allPanels = new JPanel(new GridLayout(3, 1));
		
		this.display = new JPanel();
		this.questionDisplay = new JLabel(this.question);
		this.questionDisplay.setForeground(Color.RED);
		this.questionDisplay.setFont(this.questionDisplay.getFont().deriveFont(64.0F));
		
		this.input = new JPanel();
		this.answerField = new JTextField(2);
		this.answerField.setFont(this.questionDisplay.getFont().deriveFont(64.0F));
		this.answerField.setPreferredSize(new Dimension(70, 65));
		this.messageDisplay = new JLabel("");
		this.messageDisplay.setFont(this.messageDisplay.getFont().deriveFont(50.0F));
		
		this.message = new String[3];
		this.message[0] = "Yes! Good work";
		this.message[1] = "No! Try again";
		this.message[2] = "Numbers Only!";
		
		this.border = new LineBorder(Color.BLUE, 5, true);
		
		this.display.setBorder(new TitledBorder(this.border, "Question"));
		this.input.setBorder(new TitledBorder(this.border, "Answer"));
		
		this.display.add(this.questionDisplay);
		
		this.input.add(this.answerField);
		this.input.add(this.checkButton);
		this.input.add(this.restart);
		
		this.allPanels.add(this.display);
		this.allPanels.add(this.input);
		this.allPanels.add(this.messageDisplay);
		
		this.frame.add(this.allPanels);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BasicMath();
			}
		});
	}
	
	public int randSign() {
		return this.rand.nextInt(this.signs.length);
	}

	public int randNum() {
		return this.rand.nextInt(this.RANGE + 1);
	}

	public int getMax(int a, int b) {
		return (a>b ? a:b);
	}

	public int getMin(int a, int b) {
		return (a>b ? b:a);
	}

	public String makeQuestion() {
		this.first = randNum();
		this.second = randNum();
		int signIdx = randSign();
		double temp = 0;

		switch(signIdx) {
			
			case 0:
				return "" + this.first + " " + this.signs[signIdx].charAt(0) + " " + this.second;

			case 1:
				return "" + getMax(this.first, this.second) + " " + this.signs[signIdx].charAt(0) + " " + getMin(this.first, this.second);

			case 2:
				do {
					this.first = randNum();
					this.second = randNum();
					temp = this.first * this.second;
				} while (temp > this.RANGE);
				return "" + this.first + " " + this.signs[signIdx].charAt(0) + " " + this.second;

			case 3:
				do {
					this.first = randNum();
					this.second = randNum();
					if(this.second == 0) {
						continue;
					}
					temp = (double)this.first / this.second;
				} while (temp % 1.0 > 0.0);
				return "" + this.first + " " + this.signs[signIdx].charAt(0) + " " + this.second;
		}
		return "";
	}
	
	public double result(String questionStr) {
		String str = questionStr.replace(" ", "");
		String left = null;
		String right = null;
		double result = -1;
		int signIdx = 0;

		for (int i = 0; i < this.signs.length; i++) {
			if(str.contains(signs[i])) {
				signIdx = i;
				left = str.substring(0, str.indexOf(""+signs[i].charAt(0)));
				right = str.substring(str.indexOf(""+signs[i].charAt(0)) + 1, str.length());
				break;
			}
		}

		switch(signIdx) {
			case 0:
				result = Integer.parseInt(left) + Integer.parseInt(right);
				break;
			case 1:
				result = Integer.parseInt(left) - Integer.parseInt(right);
				break;
			case 2:
				result = Integer.parseInt(left) * Integer.parseInt(right);
				break;
			case 3:
				result = (double)Integer.parseInt(left) / Integer.parseInt(right);
		}

		return result;
	}
	
	public boolean filter(String input) {
		for (int i = 0; i < input.length(); i++) {
			if ((!Character.isDigit(input.charAt(i))) && (input.charAt(i) != '-')) {
				return true;
			}
		}
		return false;
	}
}

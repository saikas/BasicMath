/**
 * A tool to help children practice basic mathematical operations
 * @author Alexander Shmakov
 * @version 1.0
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.PrintStream;

public class BasicMath
{
	private final int RANGE = 10;
	private final String[] signs = { "\u002B", "\u2212"};
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
	
	public BasicMath()
	{
		this.frame = new JFrame("Basic Math");
		this.frame.setPreferredSize(new Dimension(400, 400));
		this.frame.setDefaultCloseOperation(3);
		
		this.restart = new JButton("restart");
		this.restart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				BasicMath.this.first = BasicMath.this.newNums(BasicMath.this.rand, 10);
				BasicMath.this.second = BasicMath.this.newNums(BasicMath.this.rand, 10);
				System.out.println("new first = " + BasicMath.this.first + " new second = " + BasicMath.this.second);
				BasicMath.this.question = BasicMath.this.makeQuestion(BasicMath.this.first, BasicMath.this.second);
				BasicMath.this.questionDisplay.setText(BasicMath.this.question);
				BasicMath.this.messageDisplay.setText("");
				BasicMath.this.answerField.setText("");
			}
		});
		this.checkButton = new JButton("check answer");
		this.checkButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				System.out.println("what we get after result(ansField)= " + BasicMath.this.result(BasicMath.this.question));
				if (BasicMath.this.filter(BasicMath.this.answerField.getText()))
				{
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[2]);
				}
				else if (BasicMath.this.result(BasicMath.this.question) == Integer.parseInt(BasicMath.this.answerField.getText()))
				{
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[0]);
					BasicMath.this.first = BasicMath.this.newNums(BasicMath.this.rand, 10);
					BasicMath.this.second = BasicMath.this.newNums(BasicMath.this.rand, 10);
					System.out.println("new first= " + BasicMath.this.first + " new second = " + BasicMath.this.second);
					BasicMath.this.question = BasicMath.this.makeQuestion(BasicMath.this.first, BasicMath.this.second);
					BasicMath.this.questionDisplay.setText(BasicMath.this.question);
				}
				else if (BasicMath.this.result(BasicMath.this.question) != Integer.parseInt(BasicMath.this.answerField.getText()))
				{
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[1]);
				}
				else
				{
					BasicMath.this.messageDisplay.setText(BasicMath.this.message[2]);
				}
				BasicMath.this.answerField.setText("");
			}
		});
		this.rand = new Random();
		this.first = newNums(this.rand, 10);
		this.second = newNums(this.rand, 10);
		this.question = makeQuestion(this.first, this.second);
		
		this.allPanels = new JPanel(new GridLayout(3, 1));
		
		this.display = new JPanel();
		this.question = makeQuestion(this.first, this.second);
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
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new BasicMath();
			}
		});
	}
	
	public int newNums(Random paramRandom, int paramInt)
	{
		return paramRandom.nextInt(paramInt);
	}
	
	public String makeQuestion(int paramInt1, int paramInt2)
	{
		return "" + paramInt1 + " " + randSign(paramInt1, paramInt2) + " " + paramInt2;
	}
	
	public char randSign(int paramInt1, int paramInt2)
	{
		if (paramInt1 < paramInt2) {
			return this.signs[0].charAt(0);
		}
		return this.signs[1].charAt(0);
	}
	
	public int result(String paramString)
	{
		String str = paramString.replace(" ", "");
		System.out.println("temp = " + str);
		int result = -1;
		for (int j = 0; j < str.length(); j++)
		{
			if (str.charAt(j) == '+')
			{
				result = Integer.parseInt(str.substring(0, j)) + Integer.parseInt(str.substring(j + 1, str.length()));
				return result;
			}
			if (str.charAt(j) == '-')
			{
				result = Integer.parseInt(str.substring(0, j)) - Integer.parseInt(str.substring(j + 1, str.length()));
				return result;
			}
		}
		return result;
	}
	
	public boolean filter(String paramString)
	{
		for (int i = 0; i < paramString.length(); i++) {
			if ((!Character.isDigit(paramString.charAt(i))) && (paramString.charAt(i) != '-')) {
				return true;
			}
		}
		return false;
	}
}

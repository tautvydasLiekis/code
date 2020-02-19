package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JButton;

import main.db.sqlite.Main.CareerStatsData;
import main.enums.EnumPsicholigicalType;
import main.enums.EnumTestAnswerType;
import override.swing.JPanel2;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KTCareerQuestion extends JFrame {

	private final String CONST_SCREEN = "img\\screen.png";
	
	private JTextPane tpQuestion;
	private JLabel lblPesimist;
	private JLabel lblRealist;
	private JLabel lblOptimist;
	private JLabel lblAnswer;
	private JFormattedTextField textAnswer;
	private JButton btnConfirmAndNext;
	
	private int questionNumber;
	private JButton btnBegin;
	
	private static CareerStatsData data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				
					data = new CareerStatsData();
					KTCareerQuestion frame = new KTCareerQuestion();
					frame.setVisible(true);
					frame.setFrameMaximize();
					frame.setVisibleFrameElements(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public KTCareerQuestion() {
		// create text color using rgb
		Color color = new Color(40, 37, 37);

		setResizable(false);
		this.setBackground(UIManager.getColor("activeCaption"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.setBounds(0, 0, 546, 406);
		JPanel2 pnlMain = new JPanel2(CONST_SCREEN);
		pnlMain.setOpaque(false);
		pnlMain.setBorder(new EmptyBorder(15, 15, 15, 15));
		this.setContentPane(pnlMain);
		pnlMain.setLayout(new BorderLayout(0, 0));

		tpQuestion = new JTextPane();
		tpQuestion.setOpaque(false);
		tpQuestion.setEditable(false);
		tpQuestion.setFocusable(false);
		tpQuestion.setForeground(new Color(65, 105, 225));
		tpQuestion.setFont(new Font("SansSerif", Font.BOLD, 50));
		tpQuestion.setMargin(new Insets(100, 3, 3, 200));
		pnlMain.add(tpQuestion, BorderLayout.CENTER);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBorder(new EmptyBorder(0, 0, 50, 0));
		pnlBottom.setOpaque(false);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
		lblAnswer = new JLabel("Jūsu atsakymas:");
		lblAnswer.setForeground(color);
		lblAnswer.setFont(new Font("SansSerif", Font.BOLD, 24));
		pnlBottom.add(lblAnswer);
		
		textAnswer = new JFormattedTextField();
		textAnswer.setToolTipText("Metai, pvz. 2018, 2050");
		textAnswer.setFont(new Font("SansSerif", Font.BOLD, 24));
		pnlBottom.add(textAnswer);
		textAnswer.setColumns(10);
		
		btnConfirmAndNext = new JButton("");
		btnConfirmAndNext.setPreferredSize(new Dimension(340, 40));
		btnConfirmAndNext.setFont(new Font("SansSerif", Font.BOLD, 24));
		pnlBottom.add(btnConfirmAndNext);
		btnConfirmAndNext.addActionListener(new btnConfirmAndNextOnClick());
		
		btnBegin = new JButton("Pradėti");
		btnBegin.setPreferredSize(new Dimension(140, 40));
		btnConfirmAndNext.setPreferredSize(new Dimension(340, 40));
		btnBegin.setFont(new Font("SansSerif", Font.BOLD, 24));
		pnlBottom.add(btnBegin);
		btnBegin.addActionListener(new btnBeginOnClick());
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(new EmptyBorder(120, 5, 0, 5));
		pnlTop.setOpaque(false);
		pnlMain.add(pnlTop, BorderLayout.NORTH);
		pnlTop.setLayout(new BorderLayout(0, 0));
		
		lblPesimist = new JLabel();
		lblPesimist.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesimist.setForeground(new Color(165, 42, 42));
		lblPesimist.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 36));
		pnlTop.add(lblPesimist, BorderLayout.WEST);
		
		lblRealist = new JLabel();
		lblRealist.setForeground(new Color(0, 128, 0));
		lblRealist.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 36));
		lblRealist.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTop.add(lblRealist, BorderLayout.CENTER);
		
		lblOptimist = new JLabel();
		lblOptimist.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptimist.setForeground(new Color(128, 0, 128));
		lblOptimist.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 36));
		pnlTop.add(lblOptimist, BorderLayout.EAST);
	}

	/**
	 * Make frame maximize in Window Screen
	 */
	private void setFrameMaximize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int height = screenSize.height;
	    int width = screenSize.width;
		setBounds(0, 0, width, height);
	}
	
	private void getQuestionAndSetToFrameElements(int AQuestionId) {
		QuestionXMLReader qxml = new QuestionXMLReader();
		qxml.loadQuestionSection();

		btnConfirmAndNext.setText(String.format("Klausimas %d iš %d.", AQuestionId + 1, qxml.getQuestionsCount()));
		if (questionNumber == qxml.getQuestionsCount()-1)
			btnConfirmAndNext.setText(btnConfirmAndNext.getText() +  " Užbaigti");
		else
			btnConfirmAndNext.setText(btnConfirmAndNext.getText() +  " Tęsti");
		
		tpQuestion.setText(qxml.getQuestion(AQuestionId));
		textAnswer.setText("");
		textAnswer.requestFocus();
	}
	
	private void setVisibleFrameElements(boolean AVisible) {
		tpQuestion.setText("Įvertink kas esi:\n"
				+ "Pesimistas, Realistas, Optimistas\n\n\n"
				+ "Spauskite \"Pradėti\" mygtuką.");
		tpQuestion.setVisible(true);
		
		btnConfirmAndNext.setText("");
		btnConfirmAndNext.setVisible(AVisible);
		
		textAnswer.setText("");
		textAnswer.setVisible(AVisible);
		
		lblAnswer.setVisible(AVisible);
		btnBegin.setVisible(!AVisible);
		
		getGenerateAnswerStatistic(!AVisible);
	}

	private void getGenerateAnswerStatistic(boolean AVisible) {
		lblPesimist.setVisible(AVisible);
		lblRealist.setVisible(AVisible);
		lblOptimist.setVisible(AVisible);
		
		// when test is solving only then we showing statistic result. I do that for low iterations
		if (AVisible) {
			Config cfg = new Config();
			if (cfg.configShowUsersStatistic) {
				CareerStatsData tempStatData = new CareerStatsData();
				lblPesimist.setText(EnumPsicholigicalType.Pessimist.getTranslation()
						+ " " + tempStatData.getPessimistPercenageStat() + "%");
				lblRealist.setText(EnumPsicholigicalType.Realist.getTranslation()
						+ " " + tempStatData.getRealistPercenageStat() + "%");
				lblOptimist.setText(EnumPsicholigicalType.Optimist.getTranslation()
						+ " " + tempStatData.getOptimistPercenageStat() + "%");
			}
		}
	}
	
	class btnBeginOnClick implements ActionListener {        
		public void actionPerformed(ActionEvent e) {
			setVisibleFrameElements(true);
			
			// for first set as first question
			questionNumber = 0;
			// get first question and show it to person
			getQuestionAndSetToFrameElements(questionNumber);
			
			// create session of user
			data.createSessionUser(null);
		}
	}
	
	class btnConfirmAndNextOnClick implements ActionListener {  
		public void actionPerformed(ActionEvent e) {
			if (textAnswer.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Atsakymo reikšmė turi būti užpildyta!");
			}else if (textAnswer.getText().length() != 4) {
				JOptionPane.showMessageDialog(null, "Įrašykite metus, pvz. 2018, 2050");
			} else {
				int answerValue;
				try {
					answerValue = Integer.parseInt(textAnswer.getText());
					
					// if goes here, then value is integer, else catch block appears
					QuestionXMLReader qxml = new QuestionXMLReader();
					qxml.loadQuestionSection();
					data.setQuestionAnswer(qxml.getQuestionAnswer(questionNumber), answerValue, 
							qxml.getQuestionAnswerRange(questionNumber));	
									
					questionNumber++;
					if (questionNumber > qxml.getQuestionsCount()-1)
					{		
						Config cfg = new Config();
						tpQuestion.setVisible(false);
						lblAnswer.setVisible(false);
						btnConfirmAndNext.setVisible(false);
						textAnswer.setVisible(false);
						if (cfg.configAnswerType == EnumTestAnswerType.AnswerStatistic)
							data.getPersonPsychologicalPortrait();
						else
							data.getPersonPsychologicalAverageStatus(cfg.configRealistAnswerRange);
						
						questionNumber = 0;
						setVisibleFrameElements(false);
					} else {
						// show next question for person
						getQuestionAndSetToFrameElements(questionNumber);
					}					
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Atsakymo reikšmė turi būti skaitinė!");
				}
			}
		}
	}
}

package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.EtchedBorder;

import override.swing.JPanel2;

public class FrameText extends JDialog {

	private JPanel2 pnlMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameText frame = new FrameText("", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameText(String AAnswer, String AImagePath) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBackground(new Color(255, 255, 255));
		setUndecorated(true);
		setType(Type.UTILITY);
		setTitle("Testo pabaiga");
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 300, 250);
		pnlMain = new JPanel2(AImagePath);
		pnlMain.setBackground(new Color(255, 255, 255));
		pnlMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(0, 0, 0), new Color(128, 128, 128)));
		setContentPane(pnlMain);
		pnlMain.setLayout(new BorderLayout(0, 0));
		
		JLabel lblAnswer = new JLabel(AAnswer);
		lblAnswer.setForeground(new Color(65, 105, 225));
		lblAnswer.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAnswer.setHorizontalAlignment(SwingConstants.CENTER);
		pnlMain.add(lblAnswer, BorderLayout.NORTH);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setPreferredSize(new Dimension(10, 40));
		pnlBottom.setBorder(new EmptyBorder(0, 0, 50, 0));
		pnlBottom.setOpaque(false);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(null);
		
		JButton btnClose = new JButton("Užbaigti testą");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnClose.setBounds(51, 0, 191, 32);
		pnlBottom.add(btnClose);
		
		setLocationRelativeTo(null);
	}
}

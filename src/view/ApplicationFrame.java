package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JTable table_directed_networks;
	private static JTable table_undirected_networks;
	private JPanel contentPane;

	public ApplicationFrame(JTable t1, JTable t2) {
		setBounds(100, 100, 847, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDirected = new JLabel("Results for directed networks:");
		lblDirected.setBounds(5, 21, 215, 16);
		contentPane.add(lblDirected);
		table_directed_networks = t1;
		JScrollPane scroll_simple_comparison = new JScrollPane(table_directed_networks);
		scroll_simple_comparison.setBounds(5, 49, 806, 100);
		scroll_simple_comparison.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_simple_comparison);
		
		JLabel lblNewLabel = new JLabel("Results for undirected networks:");
		lblNewLabel.setBounds(5, 161, 294, 16);
		contentPane.add(lblNewLabel);
		table_undirected_networks = t2;
		JScrollPane scroll_2steps_comparison = new JScrollPane(table_undirected_networks);
		scroll_2steps_comparison.setBounds(5, 180, 806, 100);
		scroll_2steps_comparison.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(scroll_2steps_comparison);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	}
	
}

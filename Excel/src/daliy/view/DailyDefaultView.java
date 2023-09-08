package daliy.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import daliy.vo.DailyDefaultVO;

public class DailyDefaultView  extends JPanel{
	DailyDefaultVO defaultVO = null;
	Date date;
	JLabel datelbl;
	JTextArea area;
	JPanel panS = new JPanel(new FlowLayout());
	JButton btnSave = new JButton("일기 저장");
	
	public DailyDefaultView(Date date) {
		setLayout(new BorderLayout());
		this.date = date;
		datelbl = new JLabel((date.getYear()+1900)+"년"+(date.getMonth() + 1)+"월"+date.getDate()+"일");
		
		area = new JTextArea();
		area.setLineWrap(true);

		setBorder(BorderFactory.createRaisedBevelBorder()); //테두리
		setOpaque(true); // 배경 투명하게
		setBackground(new Color(255, 242, 245)); // 배경 핑쿠
		
		panS.add(btnSave);
	}
	
	public void initView() {
		putDailyResult();
		JScrollPane scroll = new JScrollPane(area);
		
		panS.setBorder(BorderFactory.createRaisedBevelBorder()); //테두리
		panS.setOpaque(true); // 배경 투명하게
		panS.setBackground(new Color(255, 242, 245)); // 배경 핑쿠
		
		add("North", datelbl);
		add("Center", scroll);
		add("South", panS);
	}
	
	public void putDailyResult() {
			if(defaultVO != null) {
				area.setText(defaultVO.getContent());
			} else {
				area.setText("");
			}
	}
	
	public DailyDefaultVO neededData() {
		DailyDefaultVO vo = new DailyDefaultVO();
		vo.setDate(date);
		vo.setContent(area.getText());
		
		return vo;
	}
	
	public void initData() {
		area.setText("");
	}
	
	public JButton getBtnSave() {
		return btnSave;
	}
	
	public void setDailyDefaulyVO(DailyDefaultVO defaultVO) {
		this.defaultVO = defaultVO; 
	}
}
package daliy.controller;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import daliy.view.DailyCalendarView;
import daliy.view.DailyDefaultView;
import daliy.vo.DailyDefaultVO;

public class DailyController extends JFrame {
	Date now; // 클릭된 날짜
	DailyCalendarView calendarPan;
	DailyDefaultView defaultPan;
	
	// 각 창 VO들
	DailyDefaultVO defaultVO = null;
	
	// 캘린더 뷰 버튼들
	JPanel[] calendartfs;  // the buttons on the face of the view
	JButton prevBtn, nextBtn, weekPrevBtn, weekNextBtn, goBtn;
	
	public DailyController() {
		Toolkit theKit = getToolkit();				//윈도우 툴킷 구하기
		Dimension wndSize = theKit.getScreenSize();	//화면 크기 구하기

		//위치는 화면 가운데, 크기는 화면 크기의 1/2 X 3/5
		setBounds(wndSize.width/4, wndSize.height/4,		//위치
				wndSize.width/2, (wndSize.height/5) * 3);	//크기
		
		// 캘린더 뷰 (첫 화면)
		calendarPan = new DailyCalendarView();
		calendartfs = calendarPan.getPanel();
		prevBtn = calendarPan.getPrevBtn();
		nextBtn = calendarPan.getNextBtn();
		weekPrevBtn = calendarPan.getWeekPrevBtn();
		weekNextBtn = calendarPan.getWeekNextBtn();
		goBtn = calendarPan.getGoBtn();
		
		prevBtn.addActionListener(BtnL);
		nextBtn.addActionListener(BtnL);
		weekPrevBtn.addActionListener(BtnL);
		weekNextBtn.addActionListener(BtnL);
		
		add(calendarPan);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("KSW Diary");
		setVisible(true);
	}
	
	// 캘린더 뷰 창 각 버튼에 대한 리스너 구현
	ActionListener BtnL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if((JButton)e.getSource() == prevBtn) {
				calendarPan.prevMonth();
				calendarPan.update();
			} else if((JButton)e.getSource() == nextBtn) {
				calendarPan.nextMonth();
				calendarPan.update();
			} else if((JButton)e.getSource() == weekPrevBtn) {
				int res = calendarPan.prevWeek();
				if(res != 0) calendarPan.update();
			} else if((JButton)e.getSource() == weekNextBtn) {
				int res = calendarPan.nextWeek();
				if(res != 7) calendarPan.update();
			} else if((JButton)e.getSource() == goBtn) {
				try
				{
					int year = Integer.parseInt(calendarPan.getYearField().getText());
					int month = Integer.parseInt(calendarPan.getMonthChoice().getSelectedItem().trim());
					calendarPan.goMonth(year, month - 1);
					calendarPan.update();
				}
				catch (NumberFormatException ex)		//년도 입력필드에 숫자가 아닌 것이 입력될 경우
				{
					JOptionPane.showMessageDialog(null,		//에러 메시지를 출력
						"Error: Bad input year on the text field.");
				}
			}
		}
	};
	
	
	public static void main(String[] args) {
		new DailyController();
	}
}

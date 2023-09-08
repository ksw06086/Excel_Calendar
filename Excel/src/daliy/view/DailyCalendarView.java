package daliy.view;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.io.FileInputStream;
import java.util.Calendar;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import daliy.vo.CellFace;

public class DailyCalendarView extends JPanel {
	public static final int NUM_CELL = 37;	//날짜 버튼의 총 갯수
	public static final int LINE_NUMBER = 35;
	private CellFace[] cell; // 화면에 보이는 캘린더
	
	private GregorianCalendar today;	//오늘 달력
	private GregorianCalendar calendar;	//화면에 보여줄 달력
	
	private JLabel titleLabel, weekLabel;
	private JTextField yearField;
	private Choice monthChoice;

	private JPanel listPanel;
	private JPanel[] panels;  // the buttons on the face of the view
	private JButton prevBtn, nextBtn, goBtn, weekPrevBtn, weekNextBtn;

	public int weekNum = 1;
	
	public DailyCalendarView() {

			today = new GregorianCalendar();
			calendar = new GregorianCalendar();
			cell = new CellFace[NUM_CELL];
			goMonth(today.get(today.YEAR), today.get(today.MONTH));
			
			//상단의 년도와 달 이동 버튼 패널과 요일 레이블 패널
			JPanel upperPanel = new JPanel();	
			upperPanel.setBorder(BorderFactory.createRaisedBevelBorder()); //테두리
			upperPanel.setOpaque(true); // 배경 투명하게
			upperPanel.setBackground(new Color(255, 242, 245)); // 배경 핑쿠
			//상단의 년도와 달 이동 버튼 패널
			JPanel titlePanel = new JPanel(new FlowLayout());
			titlePanel.setBorder(BorderFactory.createLoweredBevelBorder()); //테두리
			titlePanel.setOpaque(true); // 배경 투명하게
			titlePanel.setBackground(Color.white); // 배경 하얀색
			
			prevBtn = new JButton("<");
			setForeground(Color.black);	//글자색 지정
			setBorder(BorderFactory.createRaisedBevelBorder()); //버튼 테두리 추가
			titlePanel.add(prevBtn);
			titleLabel = new JLabel();
			titleLabel.setForeground(Color.black);
			titlePanel.add(titleLabel);
			nextBtn = new JButton(">");
			setForeground(Color.black);	//글자색 지정
			setBorder(BorderFactory.createRaisedBevelBorder()); //버튼 테두리 추가
			titlePanel.add(nextBtn);
			
			weekPrevBtn = new JButton("<");
			setForeground(Color.black);	//글자색 지정
			setBorder(BorderFactory.createRaisedBevelBorder()); //버튼 테두리 추가
			titlePanel.add(weekPrevBtn);
			weekLabel = new JLabel();
			weekLabel.setForeground(Color.black);
			titlePanel.add(weekLabel);
			weekNextBtn = new JButton(">");
			setForeground(Color.black);	//글자색 지정
			setBorder(BorderFactory.createRaisedBevelBorder()); //버튼 테두리 추가
			titlePanel.add(weekNextBtn);
			
			upperPanel.add(titlePanel, BorderLayout.NORTH);


			
			// 상단의 요일 라벨 패널, 가운데 날짜 버튼 패널
			JPanel datePanel = new JPanel(new BorderLayout());
			JPanel weekPanel = new JPanel(new GridLayout(1, 7));
			weekPanel.setBorder(BorderFactory.createRaisedBevelBorder()); //테두리
			weekPanel.setOpaque(true); // 배경 투명하게
			weekPanel.setBackground(new Color(255, 242, 245)); // 배경 핑쿠
			JLabel label;
			weekPanel.add(label = new JLabel("         일요일"));
			// label.setFont(new Font("a옛날사진관2", Font.PLAIN, 13));
			label.setForeground(Color.red);
			weekPanel.add(label = new JLabel("         월요일"));
			weekPanel.add(label = new JLabel("         화요일"));
			weekPanel.add(label = new JLabel("         수요일"));
			weekPanel.add(label = new JLabel("         목요일"));
			weekPanel.add(label = new JLabel("         금요일"));
			weekPanel.add(label = new JLabel("        토요일")); 
			label.setForeground(Color.blue);
			
			listPanel = new JPanel(new GridLayout(1, 7));
			listPanel.setBorder(BorderFactory.createRaisedBevelBorder()); //테두리
			listPanel.setOpaque(true); // 배경 투명하게
			listPanel.setBackground(new Color(255, 242, 245)); // 배경 핑쿠
			panels = new JPanel[NUM_CELL];
			for( int i = 7*(weekNum-1); i < 7*weekNum; i++ )
			{
				if(i >= 37) { 
					JPanel pan = new JPanel(new GridLayout(LINE_NUMBER, 1));
					pan.setVisible(false);
					listPanel.add(pan); 
					continue; 
				} 
				panels[i] = new JPanel(new GridLayout(LINE_NUMBER, 1));
				listPanel.add(panels[i]);
			}
			
			
			datePanel.add(weekPanel, BorderLayout.NORTH);
			datePanel.add(listPanel, BorderLayout.CENTER);
			//Add top, center and bottom panel to content pane
			setLayout(new BorderLayout());
			add(upperPanel, BorderLayout.NORTH);
			add(datePanel , BorderLayout.CENTER);

			update();  // initialize the pieces with their numbers
	}
	
	public void update()
	{
		try {
			listPanel.removeAll();
			listPanel.repaint();
		  	for(int i = 7*(weekNum-1); i < 7*weekNum; i++)
		  	{
		  		if(i >= 37) { 
					JPanel pan = new JPanel(new GridLayout(LINE_NUMBER, 1));
					pan.setVisible(false);
					listPanel.add(pan); 
					continue; 
				} 
				panels[i] = new JPanel(new GridLayout(LINE_NUMBER, 1));
				listPanel.add(panels[i]);
		  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				FileInputStream fis = new FileInputStream("C:\\Dev50\\S_File\\manamCalendar.xlsm");
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				
				// 엑셀 파일의 첫번째 시트 가져오기
				XSSFSheet sheet = workbook.getSheetAt(1);
				// 한 줄씩 나누어진 List로 만들기
				Iterator<Row> iter = sheet.rowIterator();
				
		  		JLabel label = new JLabel();
		  		label.setText(cell[i].getValue());		//날짜버튼의 내용 지정
				label.setForeground(cell[i].getColor());//날짜버튼의 색 지정
				label.setBackground(new Color(240,240,240));
				JTextField tf;
				panels[i].add(label);
				
				if(cell[i].getValue() != "") {
					Date dayDate = Date.valueOf(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + cell[i].getValue());
					while(iter.hasNext()) {
						Row r = iter.next();
						
						if (r.getRowNum() < 9) continue;

						Cell date = r.getCell(1);
						Cell team = r.getCell(2);
						if(date.getDateCellValue() != null && sdf.format(date.getDateCellValue()).equals(sdf.format(dayDate)) && team.getStringCellValue().equals("안나팀")) {
							if(r.getCell(13).getStringCellValue().equals("비실질")) continue;
							String manamStr = "";
							for(int j = 3; j < r.getPhysicalNumberOfCells(); j++) {
								Cell c = r.getCell(j);
								if(c.getStringCellValue().equals("")) continue;
								if(j <= 5) manamStr += c.getStringCellValue().substring(0, 1) + "/";
								else if(j == 6) manamStr += c.getStringCellValue() + "-";
								else if(j == 9) manamStr += c.getStringCellValue() + ",";
								else manamStr += c.getStringCellValue() + "/";
							}
							tf = new JTextField(manamStr);
							if(r.getCell(13).getStringCellValue().equals("불발")) {
								tf.setForeground(Color.RED);
							} else if(r.getCell(13).getStringCellValue().equals("단향")) {
								tf.setBackground(Color.GREEN);
							} else if(r.getCell(13).getStringCellValue().equals("탈락")) {
								tf.setBackground(new Color(255, 120, 120));
							} else if(r.getCell(13).getStringCellValue().equals("변동없음")) {
								tf.setBackground(new Color(252, 228, 214));
							} 
							panels[i].add(tf);
						}
					}
				}
				
				if (label.getText().equals(""))
					panels[i].setVisible(false);	    //공백 날짜 버튼 안보이게
				else
					panels[i].setVisible(true);		//공백이 아닌 날짜 버튼 보이게
				

				workbook.close();
		  	}
			
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
	
			//상단패널의 년도와 달 갱신
			titleLabel.setText("     " + year + "  /  " + (month + 1) + "     ");
			weekLabel.setText(String.valueOf(weekNum));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("엑셀 파일을 읽지 못했습니다!");
		}
	}
	
	public void goMonth(int year, int month) 
	{
		// 해당 년,월의 1일로 세팅
		calendar.set(year, month, 1);
		
		int day = 1;
		for ( int i = 0; i < NUM_CELL; i++ )
	    { 
			// 1일 전 요일이 아니고, 해당 월의 day보다 크지 않아야 함
			if (i >= calendar.get(calendar.DAY_OF_WEEK)-1 && day <= calendar.getActualMaximum(calendar.DAY_OF_MONTH))
			{
				if(	today.get(today.MONTH) == calendar.get(calendar.MONTH) &&
					today.get(today.YEAR) == calendar.get(calendar.YEAR) &&
					today.get(today.DATE) == day )
				{	cell[i] = new CellFace(String.valueOf(day), Color.cyan); } //오늘
				else if (i % 7 == 0)
				{	cell[i] = new CellFace(String.valueOf(day).toString(), Color.red); }//일요일
					else if (i % 7 == 6)
					{	cell[i] = new CellFace(String.valueOf(day).toString(), Color.blue); }//토요일
						else 
						{	cell[i] = new CellFace(String.valueOf(day).toString(), Color.black); }//평일
				day++;
	
			}
			else {
				cell[i] = new CellFace("", Color.black); //날짜가 없는 cell
			}
	    }
		
		weekNum = 1;
	}
	
    // 전으로 버튼 클릭시 캘린더 초기화
	public void prevMonth()
	{
		goMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1);
	}

    // 다음으로 버튼 클릭시 캘린더 초기화
	public void nextMonth()
	{
		goMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
	}
	
	// 주차별 전으로 버튼 클릭시 캘린더 초기화
	public int prevWeek()
	{
		if(weekNum == 1) return 0;
		weekNum -= 1; return weekNum;
	}
	
	// 주차별 전으로 버튼 클릭시 캘린더 초기화
	public int nextWeek()
	{
		if(weekNum == 6) return 7;
		weekNum += 1; return weekNum;
	}

	
	// 날짜 가져오기(2022-10-13)
	public Date getDate(int date)
	{
		Date click_date = new Date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), date); 
		return click_date;
	}

	public JPanel[] getPanel() {
		return panels;
	}

	public JButton getPrevBtn() {
		return prevBtn;
	}

	public JButton getNextBtn() {
		return nextBtn;
	}
	
	public JButton getWeekPrevBtn() {
		return weekPrevBtn;
	}

	public JButton getWeekNextBtn() {
		return weekNextBtn;
	}

	public JButton getGoBtn() {
		return goBtn;
	}

	public JTextField getYearField() {
		return yearField;
	}

	public Choice getMonthChoice() {
		return monthChoice;
	}
	
	
}
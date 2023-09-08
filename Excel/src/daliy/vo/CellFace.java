package daliy.vo;

import java.awt.Color;

/* 구조체라 할 수 있는 각 셀에 해당하는 내용물(날짜 버튼에 필요한 것) */
public class CellFace
{ 
	private String face_value;  // the value written on the cell's face
	private Color face_color;	// the color of cell's value

	public CellFace(String value, Color color)
	{ 
		face_value = value;
		face_color = color;
	}

	public String getValue()
	{
		return face_value; 
	}

	public Color getColor() 
	{
		return face_color;
	}
}
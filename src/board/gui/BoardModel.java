package board.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import board.model.Notice;

public class BoardModel extends AbstractTableModel{
	String[] column = {"notice_id","작성자","제목","등록일","조회수"};
	ArrayList<Notice> list = new ArrayList<Notice>();
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return column.length;
	}
	@Override
	public String getColumnName(int col) {
		return column[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		Notice notice = list.get(row); // 각 방에 있는 VO추출!
		String obj=null;
		if (col == 0) {
			obj = Integer.toString(notice.getNotice_id());
		} else if (col == 1) {
			obj = notice.getAuthor();
		} else if (col == 1) {
			obj = notice.getTitle();
		} else if (col == 1) {
			obj = notice.getRegdate();
		} else if (col == 1) {
			obj = Integer.toString(notice.getHit());
		}
		return obj;
	}

}

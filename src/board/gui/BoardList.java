/*게시판 목록 페이지*/
package board.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import board.model.Notice;
import board.model.NoticeDAO;

public class BoardList extends Page{
	JTable table;
	JScrollPane scroll;
	JButton bt; //글쓰기 폼 이동버튼
	BoardModel model;
	NoticeDAO noticeDAO;
	ArrayList<Notice> boardList;
	
	public BoardList(BoardMain boardMain) {
		super(boardMain);
		//생성
		table = new JTable(model = new BoardModel());
		scroll = new JScrollPane(table);
		bt = new JButton("글등록");
		noticeDAO = new NoticeDAO();
		// 스타일
		scroll.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(), 600));
		// this.setBackground(Color.YELLOW);

		// 조립
		add(scroll);
		add(bt);

		getList();
		table.updateUI();

		bt.addActionListener((e) -> {
			boardMain.showPage(Pages.valueOf("BoardWrite").ordinal());
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int col = 0;
				int row = table.getSelectedRow();
				Notice notice =  boardList.get(row);
				BoardContent boardContent = (BoardContent)boardMain.pageList[Pages.valueOf("BoardContent").ordinal()];
				boardContent.setData(notice);
				//String notice_id = (String)table.getValueAt(row, col);
				boardMain.showPage(Pages.valueOf("BoardContent").ordinal());//화면전환
			}

		});
	}

	// DAO이용하여 데이터 가져오기
	public void getList() {
		
		boardList = noticeDAO.selectAll(); 
		model.list = boardList;
	}
	
}
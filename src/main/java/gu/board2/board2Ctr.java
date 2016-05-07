package gu.board2;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import gu.common.PageVO;

@Controller
public class board2Ctr {

    @Autowired
    private board2Svc boardSvc;
    
    // 리스트
    @RequestMapping(value = "/board2List")
   	public String boardList(PageVO pageVO, ModelMap modelMap) throws Exception {

    	pageVO.pageCalculate( boardSvc.selectBoardCount() ); // startRow, endRow

    	List<?> listview   = boardSvc.selectBoardList(pageVO);
        
    	modelMap.addAttribute("listview", listview);
		modelMap.addAttribute("pageVO", pageVO);
        return "board2/boardList";
    }
    
    // 글 쓰기 
    @RequestMapping(value = "/board2Form")
   	public String boardForm(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	String brdno = request.getParameter("brdno");
    	if (brdno!=null) {
    		boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        
    		modelMap.addAttribute("boardInfo", boardInfo);
    	}
    	
        return "board2/boardForm";
    }
    
    @RequestMapping(value = "/board2Save")
   	public String boardSave(boardVO boardInfo) throws Exception {
    	
    	if (boardInfo.getBrdno()==null || "".equals(boardInfo.getBrdno()))
    		 boardSvc.insertBoard(boardInfo);
    	else boardSvc.updateBoard(boardInfo);

        return "redirect:/board2List";
    }

    // 글 읽기
    @RequestMapping(value = "/board2Read")
   	public String boardSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	
    	boardSvc.updateBoard2Read(brdno);
    	boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        
    	modelMap.addAttribute("boardInfo", boardInfo);
    	
        return "board2/boardRead";
    }
    
    // 글 삭제
    @RequestMapping(value = "/board2Delete")
   	public String boardDelete(HttpServletRequest request) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board2List";
    }

}

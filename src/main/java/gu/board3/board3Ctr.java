package gu.board3;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import gu.common.SearchVO;

@Controller
public class board3Ctr {

    @Autowired
    private board3Svc boardSvc;
    
    // 리스트
    @RequestMapping(value = "/board3List")
   	public String boardList(SearchVO searchVO, ModelMap modelMap) throws Exception {

    	searchVO.pageCalculate( boardSvc.selectBoardCount(searchVO) ); 

    	List<?> listview   = boardSvc.selectBoardList(searchVO);
        
    	modelMap.addAttribute("listview", listview);
		modelMap.addAttribute("searchVO", searchVO);
        return "board3/boardList";
    }
    
    // 글 쓰기 
    @RequestMapping(value = "/board3Form")
   	public String boardForm(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	String brdno = request.getParameter("brdno");
    	if (brdno!=null) {
    		boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        
    		modelMap.addAttribute("boardInfo", boardInfo);
    	}
    	
        return "board3/boardForm";
    }
    
    @RequestMapping(value = "/board3Save")
   	public String boardSave(boardVO boardInfo) throws Exception {
    	
  		boardSvc.insertBoard(boardInfo);

        return "redirect:/board3List";
    }

    // 글 읽기
    @RequestMapping(value = "/board3Read")
   	public String boardSave(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	
    	boardSvc.updateBoard3Read(brdno);
    	boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        
    	modelMap.addAttribute("boardInfo", boardInfo);
    	
        return "board3/boardRead";
    }
    
    // 글 삭제
    @RequestMapping(value = "/board3Delete")
   	public String boardDelete(HttpServletRequest request) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board3List";
    }

}

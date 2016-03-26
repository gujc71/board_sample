package gu.board1;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class board1Ctr {

    @Autowired
    private board1Svc boardSvc;
    
    // 리스트
    @RequestMapping(value = "/board1List")
   	public String boardList(ModelMap modelMap) throws Exception {
    	List<?> listview   = boardSvc.selectBoardList();
        
    	modelMap.addAttribute("listview", listview);
        return "board1/boardList";
    }
    
    // 글 쓰기 
    @RequestMapping(value = "/board1Form")
   	public String boardForm() throws Exception {
        return "board1/boardForm";
    }
    
    @RequestMapping(value = "/board1Save")
   	public String boardSave(@ModelAttribute boardVO boardInfo) throws Exception {
    	
    	boardSvc.insertBoard(boardInfo);
    	
        return "redirect:/board1List";
    }

    // 글 수정
    @RequestMapping(value = "/board1Update")
   	public String boardUpdate(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        
    	modelMap.addAttribute("boardInfo", boardInfo);
    	
        return "board1/boardUpdate";
    }
    
    @RequestMapping(value = "/board1UpdateSave")
   	public String board1UpdateSave(@ModelAttribute boardVO boardInfo) throws Exception {
    	
    	boardSvc.updateBoard(boardInfo);
    	
        return "redirect:/board1List";
    }    

    // 글 읽기
    @RequestMapping(value = "/board1Read")
   	public String boardRead(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        
    	modelMap.addAttribute("boardInfo", boardInfo);
    	
        return "board1/boardRead";
    }
    
    // 글 삭제
    @RequestMapping(value = "/board1Delete")
   	public String boardDelete(HttpServletRequest request) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board1List";
    }

}

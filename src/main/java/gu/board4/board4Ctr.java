package gu.board4;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import gu.common.FileUtil;
import gu.common.FileVO;
import gu.common.SearchVO;

@Controller 
public class board4Ctr {

    @Autowired
    private board4Svc boardSvc;
    
    // 리스트
    @RequestMapping(value = "/board4List")
   	public String boardList(SearchVO searchVO, ModelMap modelMap) throws Exception {

    	searchVO.pageCalculate( boardSvc.selectBoardCount(searchVO) ); // startRow, endRow

    	List<?> listview   = boardSvc.selectBoardList(searchVO);
        
    	modelMap.addAttribute("listview", listview);
		modelMap.addAttribute("searchVO", searchVO);
        return "board4/boardList";
    }
    
    // 글 쓰기 
    @RequestMapping(value = "/board4Form")
   	public String boardForm(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	String brdno = request.getParameter("brdno");
    	if (brdno!=null) {
    		boardVO boardInfo = boardSvc.selectBoardOne(brdno);
        	List<?> listview = boardSvc.selectBoard4FileList(brdno);
        
    		modelMap.addAttribute("boardInfo", boardInfo);
        	modelMap.addAttribute("listview", listview);
    	}
    	
        return "board4/boardForm";
    }
    
    @RequestMapping(value = "/board4Save")
   	public String boardSave(HttpServletRequest request, boardVO boardInfo) throws Exception {
    	String[] fileno = request.getParameterValues("fileno");
    	
    	FileUtil fs = new FileUtil();
		List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());

   		boardSvc.insertBoard(boardInfo, filelist, fileno);

        return "redirect:/board4List";
    }

    // 글 읽기
    @RequestMapping(value = "/board4Read")
   	public String board4Read(HttpServletRequest request, ModelMap modelMap) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardSvc.updateBoard4Read(brdno);
    	boardVO boardInfo = boardSvc.selectBoardOne(brdno);
    	List<?> listview = boardSvc.selectBoard4FileList(brdno);
        
    	modelMap.addAttribute("boardInfo", boardInfo);
    	modelMap.addAttribute("listview", listview);
    	
        return "board4/boardRead";
    }
    
    // 글 삭제
    @RequestMapping(value = "/board4Delete")
   	public String boardDelete(HttpServletRequest request) throws Exception {
    	
    	String brdno = request.getParameter("brdno");
    	
    	boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board4List";
    }

}

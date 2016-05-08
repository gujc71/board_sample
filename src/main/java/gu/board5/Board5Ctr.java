package gu.board5;

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
public class Board5Ctr {

    @Autowired
    private Board5Svc boardSvc;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/board5List")
    public String boardList(SearchVO searchVO, ModelMap modelMap) {

        searchVO.pageCalculate( boardSvc.selectBoardCount(searchVO) ); // startRow, endRow

        List<?> listview  = boardSvc.selectBoardList(searchVO);
        
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("searchVO", searchVO);
        
        return "board5/BoardList";
    }
    
    /** 
     * 글 쓰기. 
     */
    @RequestMapping(value = "/board5Form")
    public String boardForm(HttpServletRequest request, ModelMap modelMap) {
        String brdno = request.getParameter("brdno");
        if (brdno != null) {
            BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
            List<?> listview = boardSvc.selectBoard5FileList(brdno);
        
            modelMap.addAttribute("boardInfo", boardInfo);
            modelMap.addAttribute("listview", listview);
        }
        
        return "board5/BoardForm";
    }
    
    /**
     * 글 저장.
     */
    @RequestMapping(value = "/board5Save")
    public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
        String[] fileno = request.getParameterValues("fileno");
        
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());

        boardSvc.insertBoard(boardInfo, filelist, fileno);

        return "redirect:/board5List";
    }

    /**
     * 글 읽기.
     */
    @RequestMapping(value = "/board5Read")
    public String board5Read(HttpServletRequest request, ModelMap modelMap) {
        
        String brdno = request.getParameter("brdno");
        
        boardSvc.updateBoard5Read(brdno);
        BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
        List<?> listview = boardSvc.selectBoard5FileList(brdno);
        List<?> replylist = boardSvc.selectBoard5ReplyList(brdno);
        
        modelMap.addAttribute("boardInfo", boardInfo);
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("replylist", replylist);
        
        return "board5/BoardRead";
    }
    
    /**
     * 글 삭제.
     */
    @RequestMapping(value = "/board5Delete")
    public String boardDelete(HttpServletRequest request) {
        
        String brdno = request.getParameter("brdno");
        
        boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board5List";
    }

    /* ===================================================================== */
    
    /**
     * 댓글 저장.
     */
    @RequestMapping(value = "/board5ReplySave")
    public String board5ReplySave(HttpServletRequest request, BoardReplyVO boardReplyInfo) {
        
        boardSvc.insertBoardReply(boardReplyInfo);

        return "redirect:/board5Read?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * 댓글 삭제.
     */
    @RequestMapping(value = "/board5ReplyDelete")
    public String board5ReplyDelete(HttpServletRequest request, BoardReplyVO boardReplyInfo) {
        
        boardSvc.deleteBoard5Reply(boardReplyInfo.getReno());

        return "redirect:/board5Read?brdno=" + boardReplyInfo.getBrdno();
    }      
}

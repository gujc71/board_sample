package gu.board7;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import gu.board6.Board6Svc;
import gu.board6.BoardReplyVO;
import gu.board6.BoardVO;
import gu.common.FileUtil;
import gu.common.FileVO;
import gu.common.SearchVO;

@Controller 
public class Board7Ctr {

    @Autowired
    private Board6Svc boardSvc;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/board7List")
    public String boardList(SearchVO searchVO, ModelMap modelMap) {

        searchVO.pageCalculate( boardSvc.selectBoardCount(searchVO) ); // startRow, endRow

        List<?> listview  = boardSvc.selectBoardList(searchVO);
        
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("searchVO", searchVO);
        
        return "board7/BoardList";
    }
    
    /** 
     * 글 쓰기. 
     */
    @RequestMapping(value = "/board7Form")
    public String boardForm(HttpServletRequest request, ModelMap modelMap) {
        String brdno = request.getParameter("brdno");
        if (brdno != null) {
            BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
            List<?> listview = boardSvc.selectBoard6FileList(brdno);
        
            modelMap.addAttribute("boardInfo", boardInfo);
            modelMap.addAttribute("listview", listview);
        }
        
        return "board7/BoardForm";
    }
    
    /**
     * 글 저장.
     */
    @RequestMapping(value = "/board7Save")
    public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
        String[] fileno = request.getParameterValues("fileno");
        
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());

        boardSvc.insertBoard(boardInfo, filelist, fileno);

        return "redirect:/board7List";
    }

    /**
     * 글 읽기.
     */
    @RequestMapping(value = "/board7Read")
    public String board7Read(HttpServletRequest request, ModelMap modelMap) {
        
        String brdno = request.getParameter("brdno");
        
        boardSvc.updateBoard6Read(brdno);
        BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
        List<?> listview = boardSvc.selectBoard6FileList(brdno);
        List<?> replylist = boardSvc.selectBoard6ReplyList(brdno);
        
        modelMap.addAttribute("boardInfo", boardInfo);
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("replylist", replylist);
        
        return "board7/BoardRead";
        //return "board7/BoardReadAjax";
    }
    
    /**
     * 글 삭제.
     */
    @RequestMapping(value = "/board7Delete")
    public String boardDelete(HttpServletRequest request) {
        
        String brdno = request.getParameter("brdno");
        
        boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board7List";
    }

    /* ===================================================================== */
    
    /**
     * 댓글 저장.
     */
    @RequestMapping(value = "/board7ReplySave")
    public String board7ReplySave(HttpServletRequest request, BoardReplyVO boardReplyInfo) {
        
        boardSvc.insertBoardReply(boardReplyInfo);

        return "redirect:/board7Read?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * 댓글 저장 with Ajax.
     */
    @RequestMapping(value = "/board7ReplySaveAjax")
    public void board7ReplySaveAjax(HttpServletResponse response, BoardReplyVO boardReplyInfo) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        boardSvc.insertBoardReply(boardReplyInfo);
        
        try {
            response.getWriter().print( mapper.writeValueAsString(boardReplyInfo.getReno()));
        } catch (IOException ex) {
            System.out.println("오류: 댓글 저장에 문제가 발생했습니다.");
        }
    }

    /**
     * 댓글 저장  with Ajax2.
     */
    @RequestMapping(value = "/board7ReplySaveAjax4Reply")
    public String board7ReplySaveAjax4Reply(BoardReplyVO boardReplyInfo, ModelMap modelMap) {
        
        boardSvc.insertBoardReply(boardReplyInfo);

        modelMap.addAttribute("replyInfo", boardReplyInfo);
        
        return "board7/BoardReadAjax4Reply";
    }
    
    /**
     * 댓글 삭제.
     */
    @RequestMapping(value = "/board7ReplyDelete")
    public String board7ReplyDelete(BoardReplyVO boardReplyInfo) {
        
        if (!boardSvc.deleteBoard6Reply(boardReplyInfo.getReno()) ) {
            return "board7/BoardFailure";
        }
        return "redirect:/board7Read?brdno=" + boardReplyInfo.getBrdno();
    }
    
    /**
     * 댓글 삭제 with Ajax.
     */
    @RequestMapping(value = "/board7ReplyDeleteAjax")
    public void board7ReplyDeleteAjax(HttpServletResponse response, BoardReplyVO boardReplyInfo) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            if (!boardSvc.deleteBoard6Reply(boardReplyInfo.getReno()) ) {
                response.getWriter().print(mapper.writeValueAsString("Fail"));
            } else {
                response.getWriter().print(mapper.writeValueAsString("OK"));
            }
        } catch (IOException ex) {
            System.out.println("오류: 댓글 삭제에 문제가 발생했습니다.");
        }
    }
    
}

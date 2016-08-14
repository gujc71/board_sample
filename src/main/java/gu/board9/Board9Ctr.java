package gu.board9;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import gu.board8.Board8Svc;
import gu.board8.BoardReplyVO;
import gu.board8.BoardVO;
import gu.common.FileUtil;
import gu.common.FileVO;
import gu.common.SearchVO;

@Controller 
public class Board9Ctr {

    @Autowired
    private Board8Svc boardSvc;
    @Autowired
    private BoardGroupSvc boardGroupSvc;
    
    /**
     * 리스트.
     */
    @RequestMapping(value = "/board9List")
    public String boardList(SearchVO searchVO, ModelMap modelMap) {
        
        BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOne4Used(searchVO.getBgno());
        if (bgInfo == null) {
            return "board9/BoardGroupFail";
        }
        
        searchVO.pageCalculate( boardSvc.selectBoardCount(searchVO) ); // startRow, endRow

        List<?> listview  = boardSvc.selectBoardList(searchVO);
        
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("searchVO", searchVO);
        modelMap.addAttribute("bgInfo", bgInfo);
        
        return "board9/BoardList";
    }
    
    /** 
     * 글 쓰기. 
     */
    @RequestMapping(value = "/board9Form")
    public String boardForm(HttpServletRequest request, ModelMap modelMap) {
        String bgno = request.getParameter("bgno");
        String brdno = request.getParameter("brdno");
        
        if (brdno != null) {
            BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
            List<?> listview = boardSvc.selectBoard8FileList(brdno);
        
            modelMap.addAttribute("boardInfo", boardInfo);
            modelMap.addAttribute("listview", listview);
            bgno = boardInfo.getBgno();
        }
        BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOne4Used(bgno);
        if (bgInfo == null) {
            return "board9/BoardGroupFail";
        }
        
        modelMap.addAttribute("bgno", bgno);
        modelMap.addAttribute("bgInfo", bgInfo);
        
        return "board9/BoardForm";
    }
    
    /**
     * 글 저장.
     */
    @RequestMapping(value = "/board9Save")
    public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
        String[] fileno = request.getParameterValues("fileno");
        
        FileUtil fs = new FileUtil();
        List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());

        boardSvc.insertBoard(boardInfo, filelist, fileno);

        return "redirect:/board9List?bgno=" + boardInfo.getBgno();
    }

    /**
     * 글 읽기.
     */
    @RequestMapping(value = "/board9Read")
    public String board8Read(HttpServletRequest request, ModelMap modelMap) {
        String brdno = request.getParameter("brdno");
        
        boardSvc.updateBoard8Read(brdno);
        BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
        List<?> listview = boardSvc.selectBoard8FileList(brdno);
        List<?> replylist = boardSvc.selectBoard8ReplyList(brdno);

        BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOne4Used(boardInfo.getBgno());
        if (bgInfo == null) {
            return "board9/BoardGroupFail";
        }
        
        modelMap.addAttribute("boardInfo", boardInfo);
        modelMap.addAttribute("listview", listview);
        modelMap.addAttribute("replylist", replylist);
        modelMap.addAttribute("bgInfo", bgInfo);
        
        return "board9/BoardRead";
    }
    
    /**
     * 글 삭제.
     */
    @RequestMapping(value = "/board9Delete")
    public String boardDelete(HttpServletRequest request) {
        String brdno = request.getParameter("brdno");
        String bgno = request.getParameter("bgno");
        
        boardSvc.deleteBoardOne(brdno);
        
        return "redirect:/board9List?bgno=" + bgno;
    }

    /* ===================================================================== */
    
    /**
     * 댓글 저장.
     */
    @RequestMapping(value = "/board9ReplySave")
    public String board8ReplySave(HttpServletRequest request, BoardReplyVO boardReplyInfo, ModelMap modelMap) {
        
        boardSvc.insertBoardReply(boardReplyInfo);

        modelMap.addAttribute("replyInfo", boardReplyInfo);
        
        return "board8/BoardReadAjax4Reply";        
    }
    
    /**
     * 댓글 삭제.
     */
    @RequestMapping(value = "/board9ReplyDelete")
    public void board8ReplyDelete(HttpServletResponse response, BoardReplyVO boardReplyInfo) {
        
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            if (!boardSvc.deleteBoard8Reply(boardReplyInfo.getReno()) ) {
                response.getWriter().print(mapper.writeValueAsString("Fail"));
            } else {
                response.getWriter().print(mapper.writeValueAsString("OK"));
            }
        } catch (IOException ex) {
            System.out.println("오류: 댓글 삭제에 문제가 발생했습니다.");
        }
    }
   
}

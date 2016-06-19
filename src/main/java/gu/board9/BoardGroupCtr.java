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

import gu.common.TreeMaker;

@Controller
public class BoardGroupCtr {

    @Autowired
    private BoardGroupSvc boardSvc;
    
    /**
     *  리스트.
     */
    @RequestMapping(value = "/boardGroupList")
    public String boardList(ModelMap modelMap) {
        List<?> listview   = boardSvc.selectBoardGroupList();

        TreeMaker tm = new TreeMaker();
        String treeStr = tm.makeTreeByHierarchy(listview);
        
        modelMap.addAttribute("treeStr", treeStr);
        
        return "board9/BoardGroupList";
    }
    
    /**
     *  게시판 그룹 쓰기.
     */
    @RequestMapping(value = "/boardGroupSave")
    public void boardSave(HttpServletResponse response, BoardGroupVO bgInfo) {
        
        boardSvc.insertBoard(bgInfo);
        
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            response.getWriter().print( mapper.writeValueAsString(bgInfo));
        } catch (IOException ex) {
             System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
        }
    }

    /**
     *  게시판 그룹 읽기.
     */
    @RequestMapping(value = "/boardGroupRead")
    public void boardRead(HttpServletRequest request, HttpServletResponse response) {
        
        String bgno = request.getParameter("bgno");
        
        BoardGroupVO bgInfo = boardSvc.selectBoardGroupOne(bgno);
        
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            response.getWriter().print( mapper.writeValueAsString(bgInfo));
        } catch (IOException ex) {
             System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
        }
    }
    
    /**
     *  게시판 그룹 삭제.
     */
    @RequestMapping(value = "/boardGroupDelete")
    public void boardDelete(HttpServletRequest request, HttpServletResponse response) {
         
        String bgno = request.getParameter("bgno");
        
        boardSvc.deleteBoardGroup(bgno);
        
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            response.getWriter().print( mapper.writeValueAsString("OK"));
        } catch (IOException ex) {
             System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
        }
    }

}

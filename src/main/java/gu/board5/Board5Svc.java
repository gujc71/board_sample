package gu.board5;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gu.common.FileVO;
import gu.common.SearchVO;

@Service
public class Board5Svc {

    @Autowired
    private SqlSessionTemplate sqlSession;    
    @Autowired
    private DataSourceTransactionManager txManager;
        
    public Integer selectBoardCount(SearchVO param) {
        return sqlSession.selectOne("selectBoard5Count", param);
    }
    
    public List<?> selectBoardList(SearchVO param) {
        return sqlSession.selectList("selectBoard5List", param);
    }
    
    /**
     * 글 저장.
     */
    public void insertBoard(BoardVO param, List<FileVO> filelist, String[] fileno) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
            if (param.getBrdno() == null || "".equals(param.getBrdno())) {
                 sqlSession.insert("insertBoard5", param);
            } else {
                sqlSession.update("updateBoard5", param);
            }
            
            if (fileno != null) {
                HashMap<String, String[]> fparam = new HashMap<String, String[]>();
                fparam.put("fileno", fileno);
                sqlSession.insert("deleteBoard5File", fparam);
            }
            
            for (FileVO f : filelist) {
                f.setParentPK(param.getBrdno());
                sqlSession.insert("insertBoard5File", f);
            }
            txManager.commit(status);
        } catch (TransactionException ex) {
            txManager.rollback(status);
            System.out.println("데이터 저장 오류: " + ex.toString());
        }            
    }
 
    public BoardVO selectBoardOne(String param) {
        return sqlSession.selectOne("selectBoard5One", param);
    }
    
    public void updateBoard5Read(String param) {
        sqlSession.insert("updateBoard5Read", param);
    }
    
    public void deleteBoardOne(String param) {
        sqlSession.delete("deleteBoard5One", param);
    }
    
    public List<?> selectBoard5FileList(String param) {
        return sqlSession.selectList("selectBoard5FileList", param);
    }
    
    
    /**
     * 댓글. 
     */
    public List<?> selectBoard5ReplyList(String param) {
        return sqlSession.selectList("selectBoard5ReplyList", param);
    }
    
    public void insertBoardReply(BoardReplyVO param) {
        if (param.getReno()==null || "".equals(param.getReno())) {
            sqlSession.insert("insertBoard5Reply", param);
        } else {
            sqlSession.insert("updateBoard5Reply", param);
        }
    }   
    
    public void deleteBoard5Reply(String param) {
        sqlSession.delete("deleteBoard5Reply", param);
    }    
}

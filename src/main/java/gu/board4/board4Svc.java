package gu.board4;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import gu.common.FileVO;
import gu.common.SearchVO;

@Service
public class board4Svc {

	@Autowired
	private SqlSessionTemplate sqlSession;	
	@Autowired
	private DataSourceTransactionManager txManager;
		
    public Integer selectBoardCount(SearchVO param) throws Exception {
		return sqlSession.selectOne("selectBoard4Count", param);
    }
    public List<?> selectBoardList(SearchVO param) throws Exception {
		return sqlSession.selectList("selectBoard4List", param);
    }
    
    public void insertBoard(boardVO param, List<FileVO> filelist, String[] fileno) throws Exception {
    	
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		
		try{
	    	if (param.getBrdno()==null || "".equals(param.getBrdno()))
	    		 sqlSession.insert("insertBoard4", param);
	    	else sqlSession.update("updateBoard4", param);
	
	    	if (fileno != null) {
	    	    HashMap p = new HashMap();
	    	    p.put("fileno", fileno) ;
	    	    sqlSession.insert("deleteBoard4File", p);
	    	}
	    	
	    	for (FileVO f : filelist) {
	    		f.setParentPK(param.getBrdno());
	   		 	sqlSession.insert("insertBoard4File", f);
	    	}
			txManager.commit(status);
		} catch (Exception ex) {
			txManager.rollback(status);
			throw ex;
		}	    	
    }
 
    public boardVO selectBoardOne(String param) throws Exception {
		return sqlSession.selectOne("selectBoard4One", param);
    }
    
    public void updateBoard4Read(String param) throws Exception {
		sqlSession.insert("updateBoard4Read", param);
    }
    
    public void deleteBoardOne(String param) throws Exception {
		sqlSession.delete("deleteBoard4One", param);
    }
    
    public List<?> selectBoard4FileList(String param) throws Exception {
		return sqlSession.selectList("selectBoard4FileList", param);
    }
    
}

package gu.board3;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gu.common.SearchVO;

@Service
public class board3Svc {

	@Autowired
	private SqlSessionTemplate sqlSession;	
		
    public Integer selectBoardCount(SearchVO param) throws Exception {
		return sqlSession.selectOne("selectBoard3Count", param);
    }
    public List<?> selectBoardList(SearchVO param) throws Exception { 
		return sqlSession.selectList("selectBoard3List", param);
    }
    
    public void insertBoard(boardVO param) throws Exception {
    	if (param.getBrdno()==null || "".equals(param.getBrdno()))
    		 sqlSession.insert("insertBoard3", param);
    	else sqlSession.update("updateBoard3", param);
    }
 
    public boardVO selectBoardOne(String param) throws Exception {
		return sqlSession.selectOne("selectBoard3One", param);
    }
    
    public void updateBoard3Read(String param) throws Exception {
		sqlSession.insert("updateBoard3Read", param);
    }
    
    public void deleteBoardOne(String param) throws Exception {
		sqlSession.delete("deleteBoard3One", param);
    }
    
}

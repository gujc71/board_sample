package gu.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class TreeMaker {

    /**
     * Data Ltst(TreeVO) > Tree Data(Json).
     */
    public String makeTreeByHierarchy(List<?> listview) {
        List<TreeVO> rootlist = new ArrayList<TreeVO>();
        
        for (Integer i = 0; i < listview.size(); i++) {            
            TreeVO mtDO = (TreeVO)listview.get(i);            
            
            if (mtDO.getParent() == null) {
                rootlist.add(mtDO);
                continue;
            }    
            for (Integer j = 0; j < listview.size(); j++) {
                 TreeVO ptDO = (TreeVO) listview.get(j);
                 if (mtDO.getParent().equals(ptDO.getKey())) {
                     if (ptDO.getChildren() == null) {
                         ptDO.setChildren(new ArrayList<Object>() );
                     }
                     List<TreeVO> node = ptDO.getChildren();
                     node.add(mtDO);
                     ptDO.setIsFolder(true);
                     break;
                 }
             }     
         }

        ObjectMapper mapper = new ObjectMapper();
        String str = "";
        try {
            str = mapper.writeValueAsString(rootlist);
        } catch (IOException ex) {
            System.out.println("오류: 트리 데이터 생성에 오류가 발생했습니다.");
        }
        return str;
    }
}

package gu.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	/**
	 * 파일 업로드.
	 */
	public List<FileVO> SaveAllFiles(List<MultipartFile> upfiles) throws Exception {
		String filePath = "d:\\workspace\\fileupload\\"; 
		List<FileVO> filelist = new ArrayList<FileVO>();

		for(MultipartFile uploadfile : upfiles ){
			if (uploadfile.getSize()==0) continue;
			
			String newName = getNewName();
			
			saveFile(uploadfile, filePath+"/"+newName.substring(0,4)+"/", newName);
			
			FileVO filedo = new FileVO();
			filedo.setFilename(uploadfile.getOriginalFilename());
			filedo.setRealname(newName);
			filedo.setFilesize(uploadfile.getSize());
			filelist.add(filedo);
        }
		return filelist;
	}	
 
	public void makeBasePath(String n) {
		File dir = new File(n); 
		if(!dir.exists()) dir.mkdirs();
	}

	/**
	 * 실제 파일 저장
	 */
	public String saveFile(MultipartFile file, String basePath, String fileName) throws Exception {
		if(file == null || file.getName().equals("") || file.getSize() < 1) {
			return null;
		}
     
		makeBasePath(basePath);
		String serverFullPath = basePath + fileName;
  
		File file1 = new File(serverFullPath);
		file.transferTo(file1);
        
		return serverFullPath;
	}
	/* ----------------------------------- */
	// 날짜로 새로운 파일명 부여
	public String getNewName() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return ft.format(new Date()) + (int) (Math.random()*10);
	}
	public String getFileExtension(String Filename) {
		  Integer mid= Filename.lastIndexOf(".");
		  return Filename.substring(mid, Filename.length());
 	}

	public String getRealPath(String path, String Filename) {
		return path + Filename.substring(0,4) + "/";
	}
}

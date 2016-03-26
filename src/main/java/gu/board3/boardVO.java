package gu.board3;

import gu.common.utiletc;

public class boardVO {

    private String brdno, brdtitle, brdwriter, brdmemo, brddate, brdhit, brddeleteflag;

	/**
	 * 게시물 제목을 글자수에 맞추어 자르기
	 */
	public String getShortTitle(Integer len){
		return utiletc.getShortString(brdtitle, len);
	}
	
	public String getBrdno() {
		return brdno;
	}

	public void setBrdno(String brdno) {
		this.brdno = brdno;
	}

	public String getBrdtitle() {
		return brdtitle; 
	}

	public void setBrdtitle(String brdtitle) {
		this.brdtitle = brdtitle;
	}

	public String getBrdwriter() {
		return brdwriter;
	}

	public void setBrdwriter(String brdwriter) {
		this.brdwriter = brdwriter;
	}

	public String getBrdmemo() {
		return brdmemo.replaceAll("(?i)<script", "&lt;script");
	}

	public void setBrdmemo(String brdmemo) {
		this.brdmemo = brdmemo;
	}

	public String getBrddate() {
		return brddate;
	}

	public void setBrddate(String brddate) {
		this.brddate = brddate;
	}

	public String getBrdhit() {
		return brdhit;
	}

	public void setBrdhit(String brdhit) {
		this.brdhit = brdhit;
	}

	public String getBrddeleteflag() {
		return brddeleteflag;
	}

	public void setBrddeleteflag(String brddeleteflag) {
		this.brddeleteflag = brddeleteflag;
	}

}

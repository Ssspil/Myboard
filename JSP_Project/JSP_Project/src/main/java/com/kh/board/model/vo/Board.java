package com.kh.board.model.vo;

import java.sql.Date;

public class Board {
    private int boardNo;
    private int boardType;
    private String category;    // 실제로 board 테이블에 저장된 데이터는 category_no지만, 조회시 카테고리명을 조회할것 (문자열)
    private String boardTitle;
    private String boardContent;
    private String boardWriter;     // 실제로 board테이블에 저장된 데이터는 user_no지만, 조회시 회원아이디로 조회할것(문자열)
    private int count;
    private Date createDate;
    private String status;
    
    private String titleImg;    // 썸내일 파일명.
    
    public Board() {
        super();
    }

    public Board(int boardNo, int boardType, String category, String boardTitle, String boardContent,
            String boardWriter, int count, Date createDate, String status, String titleImg) {
        super();
        this.boardNo = boardNo;
        this.boardType = boardType;
        this.category = category;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriter = boardWriter;
        this.count = count;
        this.createDate = createDate;
        this.status = status;
        this.titleImg = titleImg;
    }

    /**
     * 게시물 목록보기할 VO
     */
    public Board(int boardNo, String category, String boardTitle, String boardWriter, int count, Date createDate) {
        super();
        this.boardNo = boardNo;
        this.category = category;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.count = count;
        this.createDate = createDate;
    }

    /**
     * 상세보기할 VO
     */
    public Board(int boardNo, String category, String boardTitle, String boardContent, String boardWriter,
            Date createDate) {
        super();
        this.boardNo = boardNo;
        this.category = category;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardWriter = boardWriter;
        this.createDate = createDate;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    @Override
    public String toString() {
        return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", category=" + category + ", boardTitle="
                + boardTitle + ", boardContent=" + boardContent + ", boardWriter=" + boardWriter + ", count=" + count
                + ", createDate=" + createDate + ", status=" + status + ", titleImg=" + titleImg + "]";
    }
    
    
}

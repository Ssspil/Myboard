package com.kh.member.model.vo;

public class Reply {

    private int replyNo;
    private String replyContent;
    private int refBoardNo;
    private String boardWriter;
    private String createDate;
    private String status;
    
    public Reply() {
    }

    public Reply(int replyNo, String replyContent, int refBoardNo, String boardWriter, String createDate,
            String status) {
        super();
        this.replyNo = replyNo;
        this.replyContent = replyContent;
        this.refBoardNo = refBoardNo;
        this.boardWriter = boardWriter;
        this.createDate = createDate;
        this.status = status;
    }

    public Reply(int replyNo, String replyContent, String boardWriter, String createDate) {
        super();
        this.replyNo = replyNo;
        this.replyContent = replyContent;
        this.boardWriter = boardWriter;
        this.createDate = createDate;
    }



    public int getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(int replyNo) {
        this.replyNo = replyNo;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getRefBoardNo() {
        return refBoardNo;
    }

    public void setRefBoardNo(int refBoardNo) {
        this.refBoardNo = refBoardNo;
    }

    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", refBoardNo=" + refBoardNo
                + ", boardWriter=" + boardWriter + ", createDate=" + createDate + ", status=" + status + "]";
    }

    

}

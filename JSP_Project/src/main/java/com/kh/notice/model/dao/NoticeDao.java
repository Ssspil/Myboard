package com.kh.notice.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.notice.model.vo.Notice;
import static com.kh.common.JDBCTemplate.*;

public class NoticeDao {

    private Properties prop = new Properties();
    
    public NoticeDao() {
        // 1) NoticeDao.class = WEB-INF/classes/com/kh/notice/model/dao.NoticeDao.class를 나타내고
        // 2) NoticeDao.class.getResource = classes를 나타내고
        // 3) ~~.getPath()는 문자열로바꾸어주는 메소드이다.
        String fileName = NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
        
        try {
            prop.loadFromXML(new FileInputStream(fileName));
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
               
    }
    
    public ArrayList<Notice> selectNoticeList(Connection conn){
        // Select문 => ResultSet객체(여러행)
        ArrayList<Notice> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectNoticeList");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                list.add(new Notice(rset.getInt("NOTICE_NO"),
                                    rset.getString("NOTICE_TITLE"),
                                    rset.getString("USER_ID"),
                                    rset.getInt("COUNT"),
                                    rset.getDate("CREATE_DATE")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return list;
    }

    public int increaseCount(int noticeNo, Connection conn) {
        // update 문 ->  처리된행의 갯수를 반환
        int result = 0;
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("increaseCount");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, noticeNo);
            
            result = psmt.executeUpdate();
                
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public Notice selectNotice(int noticeNo, Connection conn) {
        
        // select문은 반환형이 ResultSet
        ResultSet rset = null;
        
        Notice n = null;
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("selectNotice");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, noticeNo);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                n = new Notice( rset.getInt("NOTICE_NO"),
                                rset.getString("NOTICE_TITLE"),
                                rset.getString("NOTICE_CONTENT"),
                                rset.getString("USER_ID"),
                                rset.getDate("CREATE_DATE"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return n;
    }

    public int inserNotice(Notice n, Connection conn) {

        int result = 0;
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertNotice");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, n.getNoticeTitle());
            psmt.setString(2, n.getNoticeContent());
            psmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        
        return result;
    }

    public int updateNotice(Notice n, Connection conn) {
        int result = 0;
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("updateNotice");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, n.getNoticeTitle());
            psmt.setString(2, n.getNoticeContent());
            psmt.setInt(3, n.getNoticeNo());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }        
        
        return result;
    }

    public int deleteNotice(int noticeNo, Connection conn) {
        int result = 0;
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("deleteNotice");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, noticeNo);
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }  
        
        return result;
    }
    
    
    
    
    
    
    
    
    
    
}

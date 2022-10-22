package com.kh.board.model.dao;

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

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardDao {


    private Properties prop = new Properties();
    
    public BoardDao() {
        try {
            prop.loadFromXML(new FileInputStream(BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath()));
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int selectListCount(Connection conn) {
        // select문 => ResultSet
        int listCount = 0;
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectListCount");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                listCount = rset.getInt("COUNT");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return listCount;
        
    }
    
    
    public ArrayList<Board> selectList(PageInfo pi, Connection conn){
        // select문 => ResultSet
        ArrayList<Board> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectList");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            /*
             * boardLimit 이 10이라고 가정
             * 
             * currentPage = 1 => 시작값 1, 끝값 10
             * currentPage = 2 => 시작값이 11 , 끝값 20
             * currentPage = 3 => 시작값이 21 , 끝값 30
             * 
             * 시작값 = (currentPage - 1) * boardLimit + 1;
             * 끝값 = 시작값 + boardLimit - 1;
             */
            int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
            int endRow = startRow + pi.getBoardLimit() - 1;
            
            psmt.setInt(1, startRow);
            psmt.setInt(2, endRow);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                list.add(new Board(rset.getInt("BOARD_NO"),
                          rset.getString("CATEGORY_NAME"),
                          rset.getString("BOARD_TITLE"),
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

    public int increaseCount(int boardNo, Connection conn) {

        int result = 0;

        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("increaseCount");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        
        return result;
    }

    public Board selectBoard(int boardNo, Connection conn) {
        
        // select => ResultSet
        Board b = null;
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                b = new Board(rset.getInt("BOARD_NO"),
                              rset.getString("CATEGORY_NAME"),
                              rset.getString("BOARD_TITLE"),
                              rset.getString("BOARD_CONTENT"),
                              rset.getString("USER_ID"),
                              rset.getDate("CREATE_DATE"));
                
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return b;
    }

    public Attachment selectAttachment(int boardNo, Connection conn) {
        
        Attachment at = null;
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                at = new Attachment();
                
                at.setFileNo(rset.getInt("FILE_NO"));
                at.setOriginName(rset.getString("ORIGIN_NAME"));
                at.setChangeName(rset.getString("CHANGE_NAME"));
                at.setFilePath(rset.getString("FILE_PATH"));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return at;
    }

    public ArrayList<Category> selectCategoryList(Connection conn) {
        ArrayList<Category> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectCategoryList");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                Category c = new Category();
                c.setCategoryName(rset.getString("CATEGORY_NAME"));
                c.setCategoryNo(rset.getInt(1));
                list.add(c);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return list;
        

    }

    public int insertBoard(Board b, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, Integer.parseInt(b.getCategory()));
            psmt.setString(2, b.getBoardTitle());
            psmt.setString(3, b.getBoardContent());
            psmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public int insertAttachment(Attachment at, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, at.getOriginName());
            psmt.setString(2, at.getChangeName());
            psmt.setString(3, at.getFilePath());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public int updateBoard(Board b, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("updateBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, Integer.parseInt(b.getCategory()));
            psmt.setString(2, b.getBoardTitle());
            psmt.setString(3, b.getBoardContent());
            psmt.setInt(4, b.getBoardNo());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        
        return result;
    }

    public int updateAttachment(Attachment at, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("updateAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, at.getOriginName());
            psmt.setString(2, at.getChangeName());
            psmt.setString(3, at.getFilePath());
            psmt.setInt(4, at.getFileNo());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public int insertNewAttachment(Attachment at, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertNewAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, at.getRefNo());
            psmt.setString(2, at.getOriginName());
            psmt.setString(3, at.getChangeName());
            psmt.setString(4, at.getFilePath());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public int deleteBoard(int boardNo, Connection conn) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("deleteBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public void deleteAttachment(int boardNo, Connection conn) {
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("deleteAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
    }

    public ArrayList<Board> selectThumbnailList(Connection conn) {
        
        ArrayList<Board> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectThumbnailList");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                Board b = new Board();
                b.setBoardNo(rset.getInt(1));
                b.setBoardTitle(rset.getString(2));
                b.setCount(rset.getInt(3));
                b.setTitleImg(rset.getString(4));
                
                list.add(b);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return list;
    }

    public int insertThumbnailBoard(Connection conn, Board b) {
        
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertThumbnailBoard");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, b.getBoardTitle());
            psmt.setString(2, b.getBoardContent());
            psmt.setInt(3, Integer.parseInt(b.getBoardWriter()));
            
            result = psmt.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public int insertAttachmentList(Connection conn, ArrayList<Attachment> list) {

        int result = 1;
        int result2 = 1;
        
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("insertAttachmentList");
        
        try {
            
            psmt = conn.prepareStatement(sql);
            
            for(Attachment at : list) {
                // 반복문이 돌아갈때마다, 미완성된 sql문을 담은 psmt객체 생성.
//                psmt = conn.prepareStatement(sql); 그냥 밖에 꺼내도 1번만 사용해서 가능
                
                psmt.setString(1, at.getOriginName());
                psmt.setString(2, at.getChangeName());
                psmt.setString(3, at.getFilePath());
                psmt.setInt(4, at.getFileLevel());
                
                // 실행
                result2 = psmt.executeUpdate();   
                
                if(result2 == 0) {
                    result = 0;
                }
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(psmt);
        }
        
        return result;
    }

    public ArrayList<Attachment> selectAttachmentList(int boardNo, Connection conn) {
        
        ArrayList<Attachment> list = new ArrayList<>();
        
        PreparedStatement psmt = null;
        ResultSet rset = null;
        
        String sql = prop.getProperty("selectAttachment");
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setInt(1, boardNo);
            
            rset = psmt.executeQuery();
            
            while(rset.next()) {
                
                Attachment at = new Attachment();
                
                at.setChangeName(rset.getString("CHANGE_NAME"));
                at.setFilePath(rset.getString("FILE_PATH"));
                at.setFileLevel(rset.getInt("FILE_LEVEL"));
                
                list.add(at);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(psmt);
        }
        
        return list;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

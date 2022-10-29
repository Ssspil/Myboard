package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
	    // 1) MemberDao.class = WEB-INF/classes/com/kh/member/model/dao.MemberDao.class를 나타내고
	    // 2) MemberDao.class.getResource = classes를 나타내고
	    // 3) ~~.getPath()는 문자열로바꾸어주는 메소드이다.
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		
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
	
	/**
	 * 
	 * @param userId
	 * @param userPwd
	 * @param conn
	 * @return
	 */
	public Member loginMember(String userId, String userPwd, Connection conn) {
		// select문 => ResultSet객체 => Member객체.
		Member m = null;
		
		PreparedStatement psmt = null;
		ResultSet rset = null;
		
		// member-mapper.xml 에서 sql문을 얻어온다. 
		String sql = prop.getProperty("loginMember");
		// sql = SELECT * FROM MEMBER WHERE USER_ID = ? AND USER_PWD = ? AND STATUS = 'Y'

		
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, userId);
			psmt.setString(2, userPwd);
			
			rset = psmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("USER_NO"),
						rset.getString("USER_ID"),
						rset.getString("USER_PWD"),
						rset.getString("USER_NAME"),
						rset.getString("PHONE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("INTEREST"),
						rset.getDate("ENROLL_DATE"),
						rset.getDate("MODIFY_DATE"),
						rset.getString("STATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(psmt);
		}
		
		
		return m;
		
	}

	public int insertMember(Member m, Connection conn) {
	    
	    // insert문 처리된 행의 갯수를 반환하여 result에 저장시킬것.
	    int result = 0;
	    
	    PreparedStatement psmt = null;
	    String sql = prop.getProperty("insertMember");
	    
	    try {
            psmt = conn.prepareStatement(sql);
            /*
             * INSERT INTO MEMBER VALUUS(.NEXTVAL, ?,?,?,?,?,?,?,SYSDATE, NULL, DEFAULT='Y')
             */
            
            psmt.setString(1, m.getUserId());
            psmt.setString(2, m.getUserPwd());
            psmt.setString(3, m.getUserName());
            psmt.setString(4, m.getPhone());
            psmt.setString(5, m.getEmail());
            psmt.setString(6, m.getAddress());
            psmt.setString(7, m.getInterest());
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
	    
	    return result;
	}
	
	public int updateMember(Member m, Connection conn) {
	    int result = 0;
	    
	    PreparedStatement psmt = null;
	    
	    String sql = prop.getProperty("updateMember");
	    
	    try {
	        /*
	         * UPDATE MEMBER 
	         * SET USER_NAME = ?,
	         *     PHONE = ? ,
	         *     EMAIL = ? ,
	         * WHERE USER_ID = ?
	         */
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, m.getUserName());
            psmt.setString(2, m.getPhone());
            psmt.setString(3, m.getEmail());
            psmt.setString(4, m.getAddress());
            psmt.setString(5, m.getInterest());
            
            psmt.setString(6, m.getUserId());
            
            result = psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
	    
	    return result;
	}
	
	public Member selectMember(String userId, Connection conn) {
	    Member m = null;
	
	    PreparedStatement psmt = null;
	
	    ResultSet rset = null;
	
	    String sql = prop.getProperty("selectMember");
	    
	    /*
	     * SELECT * FROM MEMBER WHERE USER_ID = ?
	     */
	
	    try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, userId);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                m = new Member(rset.getInt("USER_NO"),
                        rset.getString("USER_ID"),
                        rset.getString("USER_PWD"),
                        rset.getString("USER_NAME"),
                        rset.getString("PHONE"),
                        rset.getString("EMAIL"),
                        rset.getString("ADDRESS"),
                        rset.getString("INTEREST"),
                        rset.getDate("ENROLL_DATE"),
                        rset.getDate("MODIFY_DATE"),
                        rset.getString("STATUS"));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(psmt);
        }
	    
	    return m;
	}

  
	public int updatePwdMember(String userId, String userPwd, String updatePwd, Connection conn) {
	    int result = 0;    // 쿼리문을 실행한 행의 개수
	    
	    PreparedStatement psmt = null;
	    
	    String sql = prop.getProperty("updatePwdMember");
	    
	    
	    
	    try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, updatePwd);
            psmt.setString(2, userId);
            psmt.setString(3, userPwd);
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
	    
        return result;
    }

    public int deleteMember(String userId, String userPwd, Connection conn) {
        int result = 0;
        PreparedStatement psmt = null;
        
        String sql = prop.getProperty("deleteMember");
        /*
         * UPDATE MEMBER
         * SET STATuS = 'N'
         * WHERE USER_ID - ? AND USER_PWD = ?
         */
        
        try {
            psmt = conn.prepareStatement(sql);
            
            psmt.setString(1, userId);
            psmt.setString(2, userPwd);
            
            result = psmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(psmt);
        }
        
        return result;
    }

    public int idCeck(String checkId, Connection conn) {

        // select -> ResultSet (숫자 하나)
        int count = 0;
        
        PreparedStatement psmt = null;
        
        ResultSet rset = null;
        
        String sql = prop.getProperty("idCheck");
        
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, checkId);
            
            rset = psmt.executeQuery();
            
            if(rset.next()) {
                count = rset.getInt(1);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(psmt);
        }

        return count;
    }
	
	
	
	
	
	
	
	
	
	
}

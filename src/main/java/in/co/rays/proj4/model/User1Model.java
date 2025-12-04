package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

import in.co.rays.proj4.bean.User1Bean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class User1Model {

	public Integer nextPk() throws DatabaseException {
		int pk = 0;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from users");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}
			pstmt.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exeption getting in nextPk");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}

		return pk + 1;
	}

	public void add(User1Bean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		User1Bean existBean = findByLogin(bean.getEmail());

		if (existBean != null) {
			throw new DuplicateFormatFlagsException("email is all ready exists");

		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement(" insert into users values( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getPhoneno());
			pstmt.setString(4, bean.getEmail());
			pstmt.setString(5, bean.getAddress());
			pstmt.setInt(6, bean.getStatus());
			pstmt.setString(7, bean.getGender());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());
			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ApplicationException("Exception getting in rollback");
			}
			e.printStackTrace();
			throw new ApplicationException("Exception getting in add");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}

	}

	public void update(User1Bean bean) throws ApplicationException {

		Connection conn = null;

		User1Bean existBean = findByLogin(bean.getEmail());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateFormatFlagsException("email is all ready exists");

		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					" update users set  name = ? , phoneno = ? , email = ? , address = ? , status = ? , gender = ? , createdBy = ? , modifiedBy = ? , createdDateTime = ? , modifiedDateTime = ? where id = ? ");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getPhoneno());
			pstmt.setString(3, bean.getEmail());
			pstmt.setString(4, bean.getAddress());
			pstmt.setInt(5, bean.getStatus());
			pstmt.setString(6, bean.getGender());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ApplicationException("Exception getting in rollback");
			}
			e.printStackTrace();
			throw new ApplicationException("Exception getting in update");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}

	}

	public void delete(User1Bean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from users where id = ?");
			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(" Exception getting in delete");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public User1Bean findByLogin(String email) throws ApplicationException {

		Connection conn = null;
		User1Bean bean = null;

		StringBuffer sql = new StringBuffer("select * from users where email = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new User1Bean();

				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPhoneno(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setAddress(rs.getString(5));
				bean.setStatus(rs.getInt(6));
				bean.setGender(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));

			}
			pstmt.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception getting in findByLogin");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public User1Bean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		User1Bean bean = null;

		StringBuffer sql = new StringBuffer("select * from users where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new User1Bean();

				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPhoneno(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setAddress(rs.getString(5));
				bean.setStatus(rs.getInt(6));
				bean.setGender(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));

			}
			pstmt.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception getting in findByPk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public List<User1Bean> search(User1Bean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList<User1Bean> list = new ArrayList<User1Bean>();

		StringBuffer sql = new StringBuffer("select * from users where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}

			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" and login like '" + bean.getEmail() + "%'");
			}

			if (bean.getPhoneno() != null && bean.getPhoneno().length() > 0) {
				sql.append(" and phoneno = " + bean.getPhoneno());
			}

			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" and gender like '" + bean.getGender() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				bean=new User1Bean();
				
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setPhoneno(rs.getString(3));
				bean.setEmail(rs.getString(4));
				bean.setAddress(rs.getString(5));
				bean.setStatus(rs.getInt(6));
				bean.setGender(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);

			}
			pstmt.close();
			rs.close();
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception getting in search");
		} finally {
			JDBCDataSource.closeConnection(conn);
			
		}

		return list;

	}

}

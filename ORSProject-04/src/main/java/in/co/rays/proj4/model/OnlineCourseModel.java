package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.OnlineCourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class OnlineCourseModel {

	private static Logger log = Logger.getLogger(OnlineCourseModel.class);

	// ================= NEXT PK =================

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_online_course");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);
			throw new DatabaseException("Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// ================= ADD =================

	public void add(OnlineCourseBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		OnlineCourseBean existBean = findByCourseTitle(bean.getCourseTitle());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course Title already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			int pk = nextPk();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_online_course VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getCourseTitle());
			pstmt.setString(3, bean.getModuleName());
			pstmt.setString(4, bean.getDuration());
			pstmt.setString(5, bean.getInstructorName());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in add()", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception");
			}

			throw new ApplicationException("Exception in adding Online Course");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================

	public void update(OnlineCourseBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		OnlineCourseBean existBean = findByCourseTitle(bean.getCourseTitle());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course Title already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_online_course SET course_title=?, module_name=?, duration=?, instructor_name=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setString(1, bean.getCourseTitle());
			pstmt.setString(2, bean.getModuleName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getInstructorName());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in update()", e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception");
			}

			throw new ApplicationException("Exception in updating Online Course");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= DELETE =================

	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_online_course WHERE id=?");

			pstmt.setLong(1, id);

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in delete()", e);
			throw new ApplicationException("Exception in deleting Online Course");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================

	public OnlineCourseBean findByPk(long id) throws ApplicationException {

		OnlineCourseBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_online_course WHERE id=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Course by PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY COURSE TITLE =================

	public OnlineCourseBean findByCourseTitle(String title) throws ApplicationException {

		OnlineCourseBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_online_course WHERE course_title=?");

			pstmt.setString(1, title);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Course by Title");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= LIST =================

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ================= SEARCH =================

	public List search(OnlineCourseBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM st_online_course WHERE 1=1 ");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=" + bean.getId());
			}

			if (bean.getCourseTitle() != null && bean.getCourseTitle().length() > 0) {
				sql.append(" AND course_title LIKE '" + bean.getCourseTitle() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List list = new ArrayList();

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(mapResultSetToBean(rs));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in search OnlineCourse");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ================= MAP RESULTSET =================

	private OnlineCourseBean mapResultSetToBean(ResultSet rs) throws Exception {

		OnlineCourseBean bean = new OnlineCourseBean();

		bean.setId(rs.getLong("id"));
		bean.setCourseTitle(rs.getString("course_title"));
		bean.setModuleName(rs.getString("module_name"));
		bean.setDuration(rs.getString("duration"));
		bean.setInstructorName(rs.getString("instructor_name"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;
	}
}
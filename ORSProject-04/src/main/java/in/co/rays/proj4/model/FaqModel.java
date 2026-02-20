package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.FaqBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * FaqModel handles CRUD operations for FAQ entity
 */
public class FaqModel {

	private static Logger log = Logger.getLogger(FaqModel.class);

	// ================= NEXT PK =================
	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_faq");
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
	public void add(FaqBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		FaqBean existBean = findByFaqCode(bean.getFaqCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("FAQ Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			int pk = nextPk();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_faq VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getFaqCode());
			pstmt.setString(3, bean.getQuestion());
			pstmt.setString(4, bean.getAnswer());
			pstmt.setString(5, bean.getFaqStatus());
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
			throw new ApplicationException("Exception in adding FAQ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(FaqBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		FaqBean existBean = findByFaqCode(bean.getFaqCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("FAQ Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_faq SET faq_code=?, question=?, answer=?, faq_status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setString(1, bean.getFaqCode());
			pstmt.setString(2, bean.getQuestion());
			pstmt.setString(3, bean.getAnswer());
			pstmt.setString(4, bean.getFaqStatus());
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
			throw new ApplicationException("Exception in updating FAQ");
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

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_faq WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Exception in delete()", e);
			throw new ApplicationException("Exception in deleting FAQ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public FaqBean findByPk(long id) throws ApplicationException {

		FaqBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_faq WHERE id=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting FAQ by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY CODE =================
	public FaqBean findByFaqCode(String code) throws ApplicationException {

		FaqBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_faq WHERE faq_code=?");
			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting FAQ by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= LIST =================
	public List<FaqBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ================= SEARCH =================
	public List<FaqBean> search(FaqBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM st_faq WHERE 1=1 ");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=" + bean.getId());
			}
			if (bean.getFaqCode() != null && bean.getFaqCode().length() > 0) {
				sql.append(" AND faq_code LIKE '" + bean.getFaqCode() + "%'");
			}
			if (bean.getQuestion() != null && bean.getQuestion().length() > 0) {
				sql.append(" AND question LIKE '" + bean.getQuestion() + "%'");
			}
			if (bean.getFaqStatus() != null && bean.getFaqStatus().length() > 0) {
				sql.append(" AND faq_status LIKE '" + bean.getFaqStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<FaqBean> list = new ArrayList<>();

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
			throw new ApplicationException("Exception in search FAQ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ================= MAP RESULTSET =================
	private FaqBean mapResultSetToBean(ResultSet rs) throws Exception {

		FaqBean bean = new FaqBean();

		bean.setId(rs.getLong("id"));
		bean.setFaqCode(rs.getString("faq_code"));
		bean.setQuestion(rs.getString("question"));
		bean.setAnswer(rs.getString("answer"));
		bean.setFaqStatus(rs.getString("faq_status"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;
	}
}
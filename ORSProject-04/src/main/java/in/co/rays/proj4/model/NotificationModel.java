package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.NotificationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * NotificationModel : Handles CRUD operations for Notification
 */

public class NotificationModel {

	private static Logger log = Logger.getLogger(NotificationModel.class);

	/**
	 * Get Next PK
	 */
	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_notification");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			log.error("Exception in nextPk()", e);
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * Add Notification
	 */
	public void add(NotificationBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		log.info("add() called");

		NotificationBean exist = findByCode(bean.getNotificationCode());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Notification Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			int pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_notification VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getNotificationCode());
			pstmt.setString(3, bean.getMessage());
			pstmt.setString(4, bean.getSentTo());
			pstmt.setTimestamp(5, new java.sql.Timestamp(bean.getSentTime().getTime()));
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
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in adding Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Update Notification
	 */
	public void update(NotificationBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		NotificationBean exist = findByCode(bean.getNotificationCode());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Notification Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_notification SET notification_code=?, message=?, sent_to=?, sent_time=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setString(1, bean.getNotificationCode());
			pstmt.setString(2, bean.getMessage());
			pstmt.setString(3, bean.getSentTo());
			pstmt.setTimestamp(4, new java.sql.Timestamp(bean.getSentTime().getTime()));
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
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Delete Notification
	 */
	public void delete(long id) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_notification WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			log.error("Exception in delete()", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in deleting Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find By PK
	 */
	public NotificationBean findByPk(long id) throws ApplicationException {

		NotificationBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_notification WHERE id=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in get Notification by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * Find By Notification Code
	 */
	public NotificationBean findByCode(String code) throws ApplicationException {

		NotificationBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_notification WHERE notification_code=?");
			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in get Notification by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * List
	 */
	public List<NotificationBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Search
	 */
	public List<NotificationBean> search(NotificationBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM st_notification WHERE 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=").append(bean.getId());
			}
			if (bean.getNotificationCode() != null && bean.getNotificationCode().length() > 0) {
				sql.append(" AND notification_code LIKE '").append(bean.getNotificationCode()).append("%'");
			}
			if (bean.getSentTo() != null && bean.getSentTo().length() > 0) {
				sql.append(" AND sent_to LIKE '").append(bean.getSentTo()).append("%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT ").append(pageNo).append(",").append(pageSize);
		}

		List<NotificationBean> list = new ArrayList<>();

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
			throw new ApplicationException("Exception in search Notification");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	/**
	 * Map ResultSet → Bean
	 */
	private NotificationBean mapResultSetToBean(ResultSet rs) throws Exception {

		NotificationBean bean = new NotificationBean();

		bean.setId(rs.getLong("id"));
		bean.setNotificationCode(rs.getString("notification_code"));
		bean.setMessage(rs.getString("message"));
		bean.setSentTo(rs.getString("sent_to"));
		bean.setSentTime(rs.getTimestamp("sent_time"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;
	}
}
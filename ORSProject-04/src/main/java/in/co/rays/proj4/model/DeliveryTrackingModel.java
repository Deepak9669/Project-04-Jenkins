package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.DeliveryTrackingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class DeliveryTrackingModel {

	private static Logger log = Logger.getLogger(DeliveryTrackingModel.class);

	// ================= NEXT PK =================
	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_delivery_tracking");
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
	public void add(DeliveryTrackingBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			int pk = nextPk();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_delivery_tracking VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setInt(2, bean.getOrderNumber());
			pstmt.setString(3, bean.getCustomerName());
			pstmt.setString(4, bean.getDeliveryStatus());
			pstmt.setDate(5, new java.sql.Date(bean.getDeliveryDate().getTime()));
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
			throw new ApplicationException("Exception in adding Delivery Tracking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(DeliveryTrackingBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_delivery_tracking SET order_number=?, customer_name=?, delivery_status=?, delivery_date=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setInt(1, bean.getOrderNumber());
			pstmt.setString(2, bean.getCustomerName());
			pstmt.setString(3, bean.getDeliveryStatus());
			pstmt.setDate(4, new java.sql.Date(bean.getDeliveryDate().getTime()));
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
			throw new ApplicationException("Exception in updating Delivery Tracking");
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

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_delivery_tracking WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Exception in delete()", e);
			throw new ApplicationException("Exception in deleting Delivery Tracking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public DeliveryTrackingBean findByPk(long id) throws ApplicationException {

		DeliveryTrackingBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_delivery_tracking WHERE id=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting DeliveryTracking by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= LIST =================
	public List<DeliveryTrackingBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ================= SEARCH =================
	public List<DeliveryTrackingBean> search(DeliveryTrackingBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM st_delivery_tracking WHERE 1=1");

		if (bean != null) {

			if (bean.getOrderNumber() > 0) {
				sql.append(" AND order_number=" + bean.getOrderNumber());
			}

			if (bean.getCustomerName() != null && bean.getCustomerName().length() > 0) {
				sql.append(" AND customer_name LIKE '" + bean.getCustomerName() + "%'");
			}

			if (bean.getDeliveryStatus() != null && bean.getDeliveryStatus().length() > 0) {
				sql.append(" AND delivery_status LIKE '" + bean.getDeliveryStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<DeliveryTrackingBean> list = new ArrayList<>();

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
			throw new ApplicationException("Exception in search DeliveryTracking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ================= MAP RESULTSET =================
	private DeliveryTrackingBean mapResultSetToBean(ResultSet rs) throws Exception {

		DeliveryTrackingBean bean = new DeliveryTrackingBean();

		bean.setId(rs.getLong("id"));
		bean.setOrderNumber(rs.getInt("order_number"));
		bean.setCustomerName(rs.getString("customer_name"));
		bean.setDeliveryStatus(rs.getString("delivery_status"));
		bean.setDeliveryDate(rs.getDate("delivery_date"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;
	}
}
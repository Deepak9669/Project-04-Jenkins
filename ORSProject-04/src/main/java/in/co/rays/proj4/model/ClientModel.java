package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.ClientBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ClientModel {

	private static Logger log = Logger.getLogger(ClientModel.class);

	// ================= NEXT PK =================
	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_client");
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
	public void add(ClientBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ClientBean existBean = findByName(bean.getClientName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Client Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			int pk = nextPk();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_client VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getClientName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getPhone());
			pstmt.setString(5, bean.getPriortiy());
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
			throw new ApplicationException("Exception in adding Client");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(ClientBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ClientBean existBean = findByName(bean.getClientName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Client Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_client SET client_name=?, address=?, phone=?, priortiy=?, "
					+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setString(1, bean.getClientName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getPhone());
			pstmt.setString(4, bean.getPriortiy());
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
			throw new ApplicationException("Exception in updating Client");
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

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_client WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Exception in delete()", e);
			throw new ApplicationException("Exception in deleting Client");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public ClientBean findByPk(long id) throws ApplicationException {

		ClientBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_client WHERE id=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Client by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY NAME =================
	public ClientBean findByName(String name) throws ApplicationException {

		ClientBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_client WHERE client_name=?");
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Client by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= LIST =================
	public List<ClientBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ================= SEARCH =================
	public List<ClientBean> search(ClientBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM st_client WHERE 1=1 ");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=" + bean.getId());
			}
			if (bean.getClientName() != null && bean.getClientName().length() > 0) {
				sql.append(" AND client_name LIKE '" + bean.getClientName() + "%'");
			}
			if (bean.getPriortiy() != null && bean.getPriortiy().length() > 0) {
				sql.append(" AND priortiy LIKE '" + bean.getPriortiy() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<ClientBean> list = new ArrayList<>();

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
			throw new ApplicationException("Exception in search Client");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ================= MAP RESULTSET =================
	private ClientBean mapResultSetToBean(ResultSet rs) throws Exception {

		ClientBean bean = new ClientBean();

		bean.setId(rs.getLong("id"));
		bean.setClientName(rs.getString("client_name"));
		bean.setAddress(rs.getString("address"));
		bean.setPhone(rs.getString("phone"));
		bean.setPriortiy(rs.getString("priortiy"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;
	}
}
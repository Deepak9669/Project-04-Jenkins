package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.StockBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class StockModel {

	private static Logger log = Logger.getLogger(StockModel.class);

	// ================= NEXT PK =================

	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_stock");
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

	public void add(StockBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		StockBean existBean = findByStockName(bean.getStockName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Stock Name already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			int pk = nextPk();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_stock VALUES(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setLong(2, bean.getStockId());
			pstmt.setString(3, bean.getStockName());
			pstmt.setDouble(4, bean.getPrice());
			pstmt.setInt(5, bean.getQuantity());
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

			throw new ApplicationException("Exception in adding Stock");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	// ================= UPDATE =================

	public void update(StockBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		StockBean existBean = findByStockName(bean.getStockName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Stock Name already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_stock SET stock_id=?, stock_name=?, price=?, quantity=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setLong(1, bean.getStockId());
			pstmt.setString(2, bean.getStockName());
			pstmt.setDouble(3, bean.getPrice());
			pstmt.setInt(4, bean.getQuantity());
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

			throw new ApplicationException("Exception in updating Stock");

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

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_stock WHERE id=?");

			pstmt.setLong(1, id);

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			log.error("Exception in delete()", e);

			throw new ApplicationException("Exception in deleting Stock");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	// ================= FIND BY PK =================

	public StockBean findByPk(long id) throws ApplicationException {

		StockBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_stock WHERE id=?");

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = mapResultSetToBean(rs);

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Stock by PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY NAME =================

	public StockBean findByStockName(String name) throws ApplicationException {

		StockBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_stock WHERE stock_name=?");

			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = mapResultSetToBean(rs);

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Stock by Name");

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

	public List search(StockBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM st_stock WHERE 1=1 ");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=" + bean.getId());
			}

			if (bean.getStockName() != null && bean.getStockName().length() > 0) {
				sql.append(" AND stock_name LIKE '" + bean.getStockName() + "%'");
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

			throw new ApplicationException("Exception in search Stock");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	// ================= MAP RESULTSET =================

	private StockBean mapResultSetToBean(ResultSet rs) throws Exception {

		StockBean bean = new StockBean();

		bean.setId(rs.getLong("id"));
		bean.setStockId(rs.getLong("stock_id"));
		bean.setStockName(rs.getString("stock_name"));
		bean.setPrice(rs.getDouble("price"));
		bean.setQuantity(rs.getInt("quantity"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;

	}

}
package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.AssetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AssetModel {

	private static Logger log = Logger.getLogger(AssetModel.class);

	// ================= NEXT PK =================
	public static Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_asset");
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
	public void add(AssetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		AssetBean existBean = findByAssetCode(bean.getAssetCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Asset Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			int pk = nextPk();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO st_asset VALUES(?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getAssetCode());
			pstmt.setString(3, bean.getAssetName());
			pstmt.setString(4, bean.getAssignedTo());
			pstmt.setDate(5, new java.sql.Date(bean.getIssueDate().getTime()));
			pstmt.setString(6, bean.getAssetStatus());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception in adding Asset");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(AssetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		AssetBean existBean = findByAssetCode(bean.getAssetCode());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Asset Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE st_asset SET asset_code=?, asset_name=?, assigned_to=?, issue_date=?, asset_status=?, "
							+ "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

			pstmt.setString(1, bean.getAssetCode());
			pstmt.setString(2, bean.getAssetName());
			pstmt.setString(3, bean.getAssignedTo());
			pstmt.setDate(4, new java.sql.Date(bean.getIssueDate().getTime()));
			pstmt.setString(5, bean.getAssetStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());

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
			throw new ApplicationException("Exception in updating Asset");
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

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_asset WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Exception in delete()", e);
			throw new ApplicationException("Exception in deleting Asset");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public AssetBean findByPk(long id) throws ApplicationException {

		AssetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_asset WHERE id=?");
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Asset by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY CODE =================
	public AssetBean findByAssetCode(String code) throws ApplicationException {

		AssetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_asset WHERE asset_code=?");
			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = mapResultSetToBean(rs);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Asset by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= LIST =================
	public List<AssetBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	// ================= SEARCH =================
	public List<AssetBean> search(AssetBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM st_asset WHERE 1=1 ");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=" + bean.getId());
			}
			if (bean.getAssetCode() != null && bean.getAssetCode().length() > 0) {
				sql.append(" AND asset_code LIKE '" + bean.getAssetCode() + "%'");
			}
			if (bean.getAssetName() != null && bean.getAssetName().length() > 0) {
				sql.append(" AND asset_name LIKE '" + bean.getAssetName() + "%'");
			}
			if (bean.getAssetStatus() != null && bean.getAssetStatus().length() > 0) {
				sql.append(" AND asset_status LIKE '" + bean.getAssetStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		List<AssetBean> list = new ArrayList<>();

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
			throw new ApplicationException("Exception in search Asset");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// ================= MAP RESULTSET =================
	private AssetBean mapResultSetToBean(ResultSet rs) throws Exception {

		AssetBean bean = new AssetBean();

		bean.setId(rs.getLong("id"));
		bean.setAssetCode(rs.getString("asset_code"));
		bean.setAssetName(rs.getString("asset_name"));
		bean.setAssignedTo(rs.getString("assigned_to"));
		bean.setIssueDate(rs.getDate("issue_date"));
		bean.setAssetStatus(rs.getString("asset_status"));
		bean.setCreatedBy(rs.getString("created_by"));
		bean.setModifiedBy(rs.getString("modified_by"));
		bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
		bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

		return bean;
	}
}
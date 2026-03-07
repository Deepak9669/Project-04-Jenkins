package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.TripBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TripModel {

    private static Logger log = Logger.getLogger(TripModel.class);

    // ================= NEXT PK =================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_trip");
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
    public void add(TripBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        TripBean existBean = findByTripCode(bean.getTripCode());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Trip Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            int pk = nextPk();

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO st_trip VALUES(?,?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getTripCode());
            pstmt.setString(3, bean.getDriverName());
			pstmt.setDate(4, new java.sql.Date(bean.getTripDate().getTime()));
            pstmt.setString(5, bean.getTripStatus());
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
            throw new ApplicationException("Exception in adding Trip");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= UPDATE =================
    public void update(TripBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        TripBean existBean = findByTripCode(bean.getTripCode());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Trip Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE st_trip SET trip_code=?, driver_name=?, trip_date=?, trip_status=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? WHERE id=?");

            pstmt.setString(1, bean.getTripCode());
            pstmt.setString(2, bean.getDriverName());
			pstmt.setDate(3, new java.sql.Date(bean.getTripDate().getTime()));
            pstmt.setString(4, bean.getTripStatus());
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
            throw new ApplicationException("Exception in updating Trip");
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

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_trip WHERE id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            log.error("Exception in delete()", e);
            throw new ApplicationException("Exception in deleting Trip");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public TripBean findByPk(long id) throws ApplicationException {

        TripBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_trip WHERE id=?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = mapResultSetToBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Trip by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= FIND BY TRIP CODE =================
    public TripBean findByTripCode(String code) throws ApplicationException {

        TripBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_trip WHERE trip_code=?");
            pstmt.setString(1, code);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = mapResultSetToBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Trip by Code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= LIST =================
    public List<TripBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ================= SEARCH =================
    public List<TripBean> search(TripBean bean, int pageNo, int pageSize) throws ApplicationException {

        Connection conn = null;
        StringBuffer sql = new StringBuffer("SELECT * FROM st_trip WHERE 1=1 ");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" AND id=" + bean.getId());
            }
            if (bean.getTripCode() != null && bean.getTripCode().length() > 0) {
                sql.append(" AND trip_code LIKE '" + bean.getTripCode() + "%'");
            }
            if (bean.getDriverName() != null && bean.getDriverName().length() > 0) {
                sql.append(" AND driver_name LIKE '" + bean.getDriverName() + "%'");
            }
            if (bean.getTripStatus() != null && bean.getTripStatus().length() > 0) {
                sql.append(" AND trip_status LIKE '" + bean.getTripStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        List<TripBean> list = new ArrayList<>();

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
            throw new ApplicationException("Exception in search Trip");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ================= MAP RESULTSET =================
    private TripBean mapResultSetToBean(ResultSet rs) throws Exception {

        TripBean bean = new TripBean();

        bean.setId(rs.getLong("id"));
        bean.setTripCode(rs.getString("trip_code"));
        bean.setDriverName(rs.getString("driver_name"));
        bean.setTripDate(rs.getDate("trip_date"));
        bean.setTripStatus(rs.getString("trip_status"));
        bean.setCreatedBy(rs.getString("created_by"));
        bean.setModifiedBy(rs.getString("modified_by"));
        bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
        bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

        return bean;
    }
}
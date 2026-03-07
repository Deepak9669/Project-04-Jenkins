package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BrokerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BrokerModel {

    private static Logger log = Logger.getLogger(BrokerModel.class);

    // ================= NEXT PK =================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_broker");
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
    public void add(BrokerBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        BrokerBean existBean = findByBrokerName(bean.getBrokerName());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Broker Name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            int pk = nextPk();

            // Set current timestamp
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            bean.setCreatedDatetime(currentTime);
            bean.setModifiedDatetime(currentTime);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO st_broker (id, broker_id, broker_name, contact_number, company, "
                    + "created_by, modified_by, created_datetime, modified_datetime) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setLong(2, bean.getBrokerId());
            pstmt.setString(3, bean.getBrokerName());
            pstmt.setString(4, bean.getContactNumber());
            pstmt.setString(5, bean.getCompany());
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
            throw new ApplicationException("Exception in adding Broker");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= UPDATE =================
    public void update(BrokerBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        BrokerBean existBean = findByBrokerName(bean.getBrokerName());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Broker Name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            // Update modified time
            bean.setModifiedDatetime(new Timestamp(System.currentTimeMillis()));

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE st_broker SET broker_id=?, broker_name=?, contact_number=?, "
                    + "company=?, created_by=?, modified_by=?, modified_datetime=? "
                    + "WHERE id=?");

            pstmt.setLong(1, bean.getBrokerId());
            pstmt.setString(2, bean.getBrokerName());
            pstmt.setString(3, bean.getContactNumber());
            pstmt.setString(4, bean.getCompany());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());
            pstmt.setLong(8, bean.getId());

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
            throw new ApplicationException("Exception in updating Broker");
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

            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM st_broker WHERE id=?");

            pstmt.setLong(1, id);

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            log.error("Exception in delete()", e);
            throw new ApplicationException("Exception in deleting Broker");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public BrokerBean findByPk(long id) throws ApplicationException {

        BrokerBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM st_broker WHERE id=?");

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = mapResultSetToBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Broker by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= FIND BY NAME =================
    public BrokerBean findByBrokerName(String name)
            throws ApplicationException {

        BrokerBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM st_broker WHERE broker_name=?");

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = mapResultSetToBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Broker by Name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= LIST =================
    public List<BrokerBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ================= SEARCH =================
    public List<BrokerBean> search(BrokerBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        StringBuffer sql = new StringBuffer("SELECT * FROM st_broker WHERE 1=1 ");

        if (bean != null) {

            if (bean.getBrokerName() != null && bean.getBrokerName().length() > 0) {
                sql.append(" AND broker_name LIKE '" + bean.getBrokerName() + "%'");
            }

            if (bean.getCompany() != null && bean.getCompany().length() > 0) {
                sql.append(" AND company LIKE '" + bean.getCompany() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        List<BrokerBean> list = new ArrayList<>();

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
            throw new ApplicationException("Exception in search Broker");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ================= MAP RESULTSET =================
    private BrokerBean mapResultSetToBean(ResultSet rs) throws Exception {

        BrokerBean bean = new BrokerBean();

        bean.setId(rs.getLong("id"));
        bean.setBrokerId(rs.getLong("broker_id"));
        bean.setBrokerName(rs.getString("broker_name"));
        bean.setContactNumber(rs.getString("contact_number"));
        bean.setCompany(rs.getString("company"));
        bean.setCreatedBy(rs.getString("created_by"));
        bean.setModifiedBy(rs.getString("modified_by"));
        bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
        bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

        return bean;
    }
}
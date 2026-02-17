package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InventoryModel {

    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_inventory");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    public long add(InventoryBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_inventory values(?,?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getSupplierName());
            pstmt.setString(3, bean.getProduct());
            pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setLong(5, bean.getQuantity());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback exception");
            }

            throw new ApplicationException("Exception : Exception in add Inventory");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(InventoryBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_inventory set supplier_name=?, product=?, dob=?, quantity=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getSupplierName());
            pstmt.setString(2, bean.getProduct());
            pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setLong(4, bean.getQuantity());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception");
            }

            throw new ApplicationException("Exception : Exception in updating Inventory");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(InventoryBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_inventory where id=?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception");
            }

            throw new ApplicationException("Exception : Exception in delete Inventory");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public InventoryBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_inventory where id=?");

        InventoryBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new InventoryBean();

                bean.setId(rs.getLong(1));
                bean.setSupplierName(rs.getString(2));
                bean.setProduct(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setQuantity(rs.getLong(5));

                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in get Inventory by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<InventoryBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<InventoryBean> search(InventoryBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        ArrayList<InventoryBean> list = new ArrayList<>();

        StringBuffer sql = new StringBuffer("select * from st_inventory where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getSupplierName() != null && bean.getSupplierName().length() > 0) {
                sql.append(" and supplier_name like '" + bean.getSupplierName() + "%'");
            }

            if (bean.getProduct() != null && bean.getProduct().length() > 0) {
                sql.append(" and product like '" + bean.getProduct() + "%'");
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

                bean = new InventoryBean();

                bean.setId(rs.getLong(1));
                bean.setSupplierName(rs.getString(2));
                bean.setProduct(rs.getString(3));
                bean.setDob(rs.getDate(4));
                bean.setQuantity(rs.getLong(5));

                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Inventory");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}

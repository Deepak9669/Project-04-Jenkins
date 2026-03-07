package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.HotelBookingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class HotelBookingModel {

    private static Logger log = Logger.getLogger(HotelBookingModel.class);

    // ================= NEXT PK =================
    public static Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_hotel_booking");
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
    public void add(HotelBookingBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            int pk = nextPk();

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO st_hotel_booking VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getGuestName());
            pstmt.setString(3, bean.getRoomNb());
            pstmt.setDate(4, new java.sql.Date(bean.getCheckinDate().getTime()));
            pstmt.setDate(5, new java.sql.Date(bean.getCheckOutDate().getTime()));
            pstmt.setString(6, bean.getNumberOfGuest());
            pstmt.setString(7, bean.getBookingAmount());
            pstmt.setString(8, bean.getBookingStatus());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDatetime());
            pstmt.setTimestamp(12, bean.getModifiedDatetime());

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
            throw new ApplicationException("Exception in adding Hotel Booking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= UPDATE =================
    public void update(HotelBookingBean bean)
            throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE st_hotel_booking SET guest_name=?, room_nb=?, "
                            + "checkin_date=?, checkout_date=?, number_of_guest=?, "
                            + "booking_amount=?, booking_status=?, created_by=?, "
                            + "modified_by=?, created_datetime=?, modified_datetime=? "
                            + "WHERE id=?");

            pstmt.setString(1, bean.getGuestName());
            pstmt.setString(2, bean.getRoomNb());
            pstmt.setDate(3, new java.sql.Date(bean.getCheckinDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(bean.getCheckOutDate().getTime()));
            pstmt.setString(5, bean.getNumberOfGuest());
            pstmt.setString(6, bean.getBookingAmount());
            pstmt.setString(7, bean.getBookingStatus());
            pstmt.setString(8, bean.getCreatedBy());
            pstmt.setString(9, bean.getModifiedBy());
            pstmt.setTimestamp(10, bean.getCreatedDatetime());
            pstmt.setTimestamp(11, bean.getModifiedDatetime());
            pstmt.setLong(12, bean.getId());

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
            throw new ApplicationException("Exception in updating Hotel Booking");
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
                    "DELETE FROM st_hotel_booking WHERE id=?");
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            log.error("Exception in delete()", e);
            throw new ApplicationException("Exception in deleting Hotel Booking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public HotelBookingBean findByPk(long id)
            throws ApplicationException {

        HotelBookingBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM st_hotel_booking WHERE id=?");
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = mapResultSetToBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Hotel Booking by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= LIST =================
    public List<HotelBookingBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ================= SEARCH =================
    public List<HotelBookingBean> search(HotelBookingBean bean,
            int pageNo, int pageSize)
            throws ApplicationException {

        Connection conn = null;
        StringBuffer sql = new StringBuffer("SELECT * FROM st_hotel_booking WHERE 1=1 ");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" AND id=" + bean.getId());
            }
            if (bean.getGuestName() != null
                    && bean.getGuestName().length() > 0) {
                sql.append(" AND guest_name LIKE '" + bean.getGuestName() + "%'");
            }
            if (bean.getBookingStatus() != null
                    && bean.getBookingStatus().length() > 0) {
                sql.append(" AND booking_status LIKE '" + bean.getBookingStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        List<HotelBookingBean> list = new ArrayList<>();

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
            throw new ApplicationException("Exception in search Hotel Booking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ================= MAP RESULTSET =================
    private HotelBookingBean mapResultSetToBean(ResultSet rs)
            throws Exception {

        HotelBookingBean bean = new HotelBookingBean();

        bean.setId(rs.getLong("id"));
        bean.setGuestName(rs.getString("guest_name"));
        bean.setRoomNb(rs.getString("room_nb"));
        bean.setCheckinDate(rs.getDate("checkin_date"));
        bean.setCheckOutDate(rs.getDate("checkout_date"));
        bean.setNumberOfGuest(rs.getString("number_of_guest"));
        bean.setBookingAmount(rs.getString("booking_amount"));
        bean.setBookingStatus(rs.getString("booking_status"));
        bean.setCreatedBy(rs.getString("created_by"));
        bean.setModifiedBy(rs.getString("modified_by"));
        bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
        bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));

        return bean;
    }
}
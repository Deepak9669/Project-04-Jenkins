package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * Base bean class that contains common audit fields for all entities.
 * <p>
 * All specific bean classes should extend this class to inherit:
 * <ul>
 *   <li>Primary key ID</li>
 *   <li>Created by / Modified by</li>
 *   <li>Created datetime / Modified datetime</li>
 * </ul>
 *
 * @author Deepak Verma
 * @version 1.0
 */
public abstract class BaseBean implements DropdownListBean {

    /**
     * Primary key ID.
     */
    protected long id;

    /**
     * User who created the record.
     */
    protected String createdBy;

    /**
     * User who last modified the record.
     */
    protected String modifiedBy;

    /**
     * Timestamp when the record was created.
     */
    protected Timestamp createdDatetime;

    /**
     * Timestamp when the record was last modified.
     */
    protected Timestamp modifiedDatetime;

    /**
     * Gets the primary key ID.
     *
     * @return the ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the primary key ID.
     *
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the creator user.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the creator user.
     *
     * @param createdBy the creator user to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last modifier user.
     *
     * @return modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the last modifier user.
     *
     * @param modifiedBy the modifier user to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Gets the creation timestamp.
     *
     * @return createdDatetime
     */
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdDatetime the creation timestamp to set
     */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * Gets the last modified timestamp.
     *
     * @return modifiedDatetime
     */
    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    /**
     * Sets the last modified timestamp.
     *
     * @param modifiedDatetime the modified timestamp to set
     */
    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

}

package com.app.common.dal.auto.dataobject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * A data object class directly models database table <tt>simple_task_repo</tt>.
 *
 * This file is generated by <tt>dalgen-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>dalgen</tt> project.
 *
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to directory <tt>dalgen</tt>,
 * and find the corresponding configuration file (<tt>tables/simple_task_repo.xml</tt>).
 * Modify the configuration file according to your needs, then run <tt>dalgen-dalgen</tt> to generate this file.
 * @author dalgen(Cheng Li)
 */
public class SimpleTaskRepoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    //========== properties ==========

    /**
     * This property corresponds to db column <tt>id</tt>.
     */
    private Long id;

    /**
     * This property corresponds to db column <tt>type</tt>.
     */
    private Integer type;

    /**
     * This property corresponds to db column <tt>config</tt>.
     */
    private String config;

    /**
     * This property corresponds to db column <tt>machine</tt>.
     */
    private String machine;

    /**
     * This property corresponds to db column <tt>status</tt>.
     */
    private Integer status;

    /**
     * This property corresponds to db column <tt>gmt_create</tt>.
     */
    private Date gmtCreate;

    /**
     * This property corresponds to db column <tt>gmt_modified</tt>.
     */
    private Date gmtModified;

    /**
     * This property corresponds to db column <tt>retry_count</tt>.
     */
    private Integer retryCount;

    /**
     * This property corresponds to db column <tt>trigger_time</tt>.
     */
    private Date triggerTime;

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public Integer getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>config</tt>.
     *
     * @return property value of config
     */
    public String getConfig() {
        return config;
    }

    /**
     * Setter method for property <tt>config</tt>.
     *
     * @param config value to be assigned to property config
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * Getter method for property <tt>machine</tt>.
     *
     * @return property value of machine
     */
    public String getMachine() {
        return machine;
    }

    /**
     * Setter method for property <tt>machine</tt>.
     *
     * @param machine value to be assigned to property machine
     */
    public void setMachine(String machine) {
        this.machine = machine;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>gmtCreate</tt>.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * Setter method for property <tt>gmtModified</tt>.
     *
     * @param gmtModified value to be assigned to property gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * Getter method for property <tt>retryCount</tt>.
     *
     * @return property value of retryCount
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * Setter method for property <tt>retryCount</tt>.
     *
     * @param retryCount value to be assigned to property retryCount
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * Getter method for property <tt>triggerTime</tt>.
     *
     * @return property value of triggerTime
     */
    public Date getTriggerTime() {
        return triggerTime;
    }

    /**
     * Setter method for property <tt>triggerTime</tt>.
     *
     * @param triggerTime value to be assigned to property triggerTime
     */
    public void setTriggerTime(Date triggerTime) {
        this.triggerTime = triggerTime;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

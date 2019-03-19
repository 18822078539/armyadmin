package com.army.armyadmin.web.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_test_project")
public class TestProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String code;

    private Integer typeid;

    private Date testtime;
    @Transient
    private String testtimestr;

    private String person;

    private String des;
    @Transient
    private String typename;

    public String getTesttimestr() {
        return testtimestr;
    }

    public void setTesttimestr(String testtimestr) {
        this.testtimestr = testtimestr;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * @return typeid
     */
    public Integer getTypeid() {
        return typeid;
    }

    /**
     * @param typeid
     */
    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    /**
     * @return testtime
     */
    public Date getTesttime() {
        return testtime;
    }

    /**
     * @param testtime
     */
    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }

    /**
     * @return person
     */
    public String getPerson() {
        return person;
    }

    /**
     * @param person
     */
    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }

    /**
     * @return des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}
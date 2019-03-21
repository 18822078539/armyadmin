package com.army.armyadmin.web.domain;

import javax.persistence.*;

@Table(name = "tb_test_score")
public class TestScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userid;

    private String username;

    private String score;

    private String score1;

    private String evalute;
    private Integer projectid;

    @Transient
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
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
     * @return userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return testscore
     */
    public String getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    /**
     * @return score1
     */
    public String getScore1() {
        return score1;
    }

    /**
     * @param score1
     */
    public void setScore1(String score1) {
        this.score1 = score1 == null ? null : score1.trim();
    }

    /**
     * @return evalute
     */
    public String getEvalute() {
        return evalute;
    }

    /**
     * @param evalute
     */
    public void setEvalute(String evalute) {
        this.evalute = evalute == null ? null : evalute.trim();
    }
}
package com.zhixing.work.zhixin.bean;

/**
 * Created by lhj on 2018/7/26.
 * Description:
 */

public class AbilityBean {



    public static final String[] abilitys = {"领导力", "人脉", "职业素养", "执行力", "心态" };

    public int[] getAllAbility() {
        int[] allAbility = {psychology, integrity, agreement, compliance, connection};
        return allAbility;
    }

    public static String[] getAbilitys() {
        return abilitys;
    }

    private int psychology;
    private int integrity;
    private int agreement;
    private int compliance;
    private int connection;

    public AbilityBean(int psychology, int integrity, int agreement, int compliance, int connection) {
        this.psychology = psychology;
        this.integrity = integrity;
        this.agreement = agreement;
        this.compliance = compliance;
        this.connection = connection;
    }



    public int getPsychology() {
        return psychology;
    }

    public void setPsychology(int psychology) {
        this.psychology = psychology;
    }

    public int getIntegrity() {
        return integrity;
    }

    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    }

    public int getAgreement() {
        return agreement;
    }

    public void setAgreement(int agreement) {
        this.agreement = agreement;
    }

    public int getCompliance() {
        return compliance;
    }

    public void setCompliance(int compliance) {
        this.compliance = compliance;
    }

    public int getConnection() {
        return connection;
    }

    public void setConnection(int connection) {
        this.connection = connection;
    }



}

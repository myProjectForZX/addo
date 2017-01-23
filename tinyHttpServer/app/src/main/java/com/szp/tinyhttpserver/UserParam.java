package com.szp.tinyhttpserver;

public class UserParam {
    private String id;
    private String password;
    private String vmPassword;
    private String tollAllow;
    private String accountCode;
    private String userContext;
    private String effectiveCallerIdName;
    private String effectiveCallerIdNumber;
    private String outboundCallerIdName;
    private String outboundCallerIdNumber;
    private String callgroup;

    public UserParam() {
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getVmPassword() {
        return vmPassword;
    }
    public void setVmPassword(String vmPassword) {
        this.vmPassword = vmPassword;
    }
    public String getTollAllow() {
        return tollAllow;
    }
    public void setTollAllow(String tollAllow) {
        this.tollAllow = tollAllow;
    }
    public String getAccountCode() {
        return accountCode;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
    public String getUserContext() {
        return userContext;
    }
    public void setUserContext(String userContext) {
        this.userContext = userContext;
    }
    public String getEffectiveCallerIdName() {
        return effectiveCallerIdName;
    }
    public void setEffectiveCallerIdName(String effectiveCallerIdName) {
        this.effectiveCallerIdName = effectiveCallerIdName;
    }
    public String getEffectiveCallerIdNumber() {
        return effectiveCallerIdNumber;
    }
    public void setEffectiveCallerIdNumber(String effectiveCallerIdNumber) {
        this.effectiveCallerIdNumber = effectiveCallerIdNumber;
    }
    public String getOutboundCallerIdName() {
        return outboundCallerIdName;
    }
    public void setOutboundCallerIdName(String outboundCallerIdName) {
        this.outboundCallerIdName = outboundCallerIdName;
    }
    public String getOutboundCallerIdNumber() {
        return outboundCallerIdNumber;
    }
    public void setOutboundCallerIdNumber(String outboundCallerIdNumber) {
        this.outboundCallerIdNumber = outboundCallerIdNumber;
    }
    public String getCallgroup() {
        return callgroup;
    }
    public void setCallgroup(String callgroup) {
        this.callgroup = callgroup;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", password=" + password + ", vmPassword="
                + vmPassword + ", tollAllow=" + tollAllow + ", accountCode="
                + accountCode + ", userContext=" + userContext
                + ", effectiveCallerIdName=" + effectiveCallerIdName
                + ", effectiveCallerIdNumber=" + effectiveCallerIdNumber
                + ", outboundCallerIdName=" + outboundCallerIdName
                + ", outboundCallerIdNumber=" + outboundCallerIdNumber
                + ", callgroup=" + callgroup + "]";
    }
}

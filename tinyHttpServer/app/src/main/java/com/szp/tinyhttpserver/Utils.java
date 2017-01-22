package com.szp.tinyhttpserver;

/**
 * Created by zcily on 2017/1/22.
 */

public class Utils {
    public static final int HTTP_PORT = 8088;

    public static final String UserName = "addo";
    public static final String UserPassword = "8888";
    public static final String HTTP_LOGIN  = "login.html";
    public static final String HTTP_CONFIG = "Config.html";
    public static final String HTTP_GET_XML_CONFIG = "getConfigXml";
    public static final String CONFIG_XML = "config.xml";

    //login.html用来验证账号密码的input节点的id
    public static final String HTTP_ADDO_USER = "user";
    public static final String HTTP_ADDO_PASSWORD = "passwd";

    //用于标示是否进入config.html
    public static final String HTTP_ADDO_USERID = "userid";

    //config.html中input 节点的id
    public static final String ADDO_USER_ID = "userid";
    public static final String ADDO_PASSWORD = "password";
    public static final String ADDO_VM_PASSWORD = "vmpassword";
    public static final String ADDO_TOLL_ALLOW = "tollallow";
    public static final String ADDO_ACCOUNTCODE = "accountcode";
    public static final String ADDO_USER_CONTEXT = "context";
    public static final String ADDO_EFFECTIVE_CALLER_ID_NAME = "caleridnumber";
    public static final String ADDO_EFFECTIVE_CALLER_ID_NUMBER = "caleridnumber";
    public static final String ADDO_OUTBOUND_CALLER_ID_NAME = "outboundname";
    public static final String ADDO_OUTBOUND_CALLER_ID_NUMBER = "outboundnumber";
    public static final String ADDO_CALLGROUP = "group";

    public static final int HTTP_OK = 1;
    public static final int HTTP_FAIL = 0;

    //for handle
    public static final int MESSAGE_HTTP_STATUS = 1;

}

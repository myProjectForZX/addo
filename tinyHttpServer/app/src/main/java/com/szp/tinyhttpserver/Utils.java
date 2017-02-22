package com.szp.tinyhttpserver;

/**
 * Created by zcily on 2017/1/22.
 */

public class Utils {
    public static final int HTTP_PORT = 8088;

    //0 for listview show
    //1 for xml node name
    //2 for default value
    public static final String[][] configPara =  new String[][]{
            { Utils.ADDO_USER_ID, Utils.XML_ID, Utils.DEFAULT_ID},
            { Utils.ADDO_PASSWORD, Utils.XML_PASSWORD, Utils.DEFAULT_PASSWORD },
            { Utils.ADDO_VM_PASSWORD, Utils.XML_VMPASSWORD, Utils.DEFAULT_VMPASSWORD },
            { Utils.ADDO_TOLL_ALLOW, Utils.XML_TOLLALLOW, Utils.DEFAULT_TOLLALLOW },
            { Utils.ADDO_ACCOUNTCODE, Utils.XML_ACCOUNTCODE, Utils.DEFAULT_ACCOUNTCODE },
            { Utils.ADDO_USER_CONTEXT, Utils.XML_USERCONTEXT, Utils.DEFAULT_USERCONTEXT },
            { Utils.ADDO_EFFECTIVE_CALLER_ID_NAME, Utils.XML_EFFECTIVE_CALLER_ID_NAME, Utils.DEFAULT_EFFECTIVE_CALLER_ID_NAME },
            { Utils.ADDO_EFFECTIVE_CALLER_ID_NUMBER, Utils.XML_EFFECTIVE_CALLER_ID_NUMBER, Utils.DEFAULT_EFFECTIVE_CALLER_ID_NUMBER },
            { Utils.ADDO_OUTBOUND_CALLER_ID_NAME, Utils.XML_OUTBOUND_CALLER_ID_NAME, Utils.DEFAULT_OUTBOUND_CALLER_ID_NAME },
            { Utils.ADDO_OUTBOUND_CALLER_ID_NUMBER, Utils.XML_OUTBOUND_CALLER_ID_NUMBER, Utils.DEFAULT_OUTBOUND_CALLER_ID_NUMBER },
            { Utils.ADDO_CALLGROUP, Utils.XML_CALL_GROUP, Utils.DEFAULT_CALL_GROUP }
    };

    public static final String UserName = "addo";
    public static final String UserPassword = "8888";
    public static final String HTTP_LOGIN  = "login.html";
    public static final String HTTP_CONFIG = "Config.html";
    public static final String HTTP_GET_XML_CONFIG = "getConfigXml.html";
    public static final String CONFIG_XML = "config.xml";

    //login.html用来验证账号密码的input节点的id
    public static final String HTTP_ADDO_USER = "user";
    public static final String HTTP_ADDO_PASSWORD = "passwd";

    //用于标示是否进入config.html
    public static final String HTTP_ADDO_USERID = "id";

    //for addo list view show
    public static final String ADDO_USER_ID = "user id";
    public static final String ADDO_PASSWORD = "password";
    public static final String ADDO_VM_PASSWORD = "vm password";
    public static final String ADDO_TOLL_ALLOW = "toll allow";
    public static final String ADDO_ACCOUNTCODE = "account code";
    public static final String ADDO_USER_CONTEXT = "user context";
    public static final String ADDO_EFFECTIVE_CALLER_ID_NAME = "effective caller id number";
    public static final String ADDO_EFFECTIVE_CALLER_ID_NUMBER = "effective caller id number";
    public static final String ADDO_OUTBOUND_CALLER_ID_NAME = "outbound caller id name";
    public static final String ADDO_OUTBOUND_CALLER_ID_NUMBER = "outbound caller id number";
    public static final String ADDO_CALLGROUP = "group";

    //1.xml 中input 节点的id and  sharepreference
    public static final String XML_ID="id";
    public static final String XML_PASSWORD="password";
    public static final String XML_VMPASSWORD="vm-password";
    public static final String XML_TOLLALLOW="toll_allow";
    public static final String XML_ACCOUNTCODE="accountcode";
    public static final String XML_USERCONTEXT="user_context";
    public static final String XML_EFFECTIVE_CALLER_ID_NAME="effective_caller_id_name";
    public static final String XML_EFFECTIVE_CALLER_ID_NUMBER="effective_caller_id_number";
    public static final String XML_OUTBOUND_CALLER_ID_NAME="outbound_caller_id_name";
    public static final String XML_OUTBOUND_CALLER_ID_NUMBER="outbound_caller_id_number";
    public static final String XML_CALL_GROUP="callgroup";

    //for default value
    public static final String DEFAULT_ID = "1";
    public static final String DEFAULT_PASSWORD = "xxxx";
    public static final String DEFAULT_VMPASSWORD = "1";
    public static final String DEFAULT_TOLLALLOW = "international";
    public static final String DEFAULT_ACCOUNTCODE = "1";
    public static final String DEFAULT_USERCONTEXT = "default";
    public static final String DEFAULT_EFFECTIVE_CALLER_ID_NAME = "1";
    public static final String DEFAULT_EFFECTIVE_CALLER_ID_NUMBER = "1";
    public static final String DEFAULT_OUTBOUND_CALLER_ID_NAME = "1";
    public static final String DEFAULT_OUTBOUND_CALLER_ID_NUMBER = "1";
    public static final String DEFAULT_CALL_GROUP = "default";


    public static final int HTTP_OK = 1;
    public static final int HTTP_FAIL = 0;

    //for handle
    public static final int MESSAGE_HTTP_STATUS = 1;
    public static final int MESSAGE_REFRESH_DATA = 2;
    public static final int MESSAGE_DOWNLOAD_CONFIG_STATUS = 3;

    //for list view
    public static final String ITEM_NAME = "name";
    public static final String ITEM_VALUE = "value";

    //for xml node
    public static final String XML_NAME = "name";
    public static final String XML_VALUE = "value";

    //for parser xml
    public static final String TAG_USER = "user";
    public static final String TAG_PARAM = "param";
    public static final String TAG_PARAMS = "params";
    public static final String TAG_VARIABLES = "variables";
    public static final String TAG_VARIABLE = "variable";

    public static final int MAX_FILE_SIZE = 2 * 1024 * 1024; //2M;

    public static final String BASE_CONFIG_XML_URL = "http://192.168.0.106/directory.php";
    public static final String POST_PARAM_DOMAIN = "default";
    public static final String POST_PARAM_USERID = "1000";
}

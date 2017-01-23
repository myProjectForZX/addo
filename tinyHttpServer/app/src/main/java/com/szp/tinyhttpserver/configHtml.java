package com.szp.tinyhttpserver;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zcily on 2017/1/23.
 */

public class configHtml {
    public static String getConfigHtml(Context context) {
        StringBuffer sb = new StringBuffer();

        sb.append("<html><head>");
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=gbk\">");
        sb.append("<meta http-equiv=\"pragma\" content=\"no-cache\">");
        sb.append("<meta http-equiv=\"expire\" content=\"now\">");
        sb.append("<title>SIP Video Intercom Config</title>");
       // sb.append("<link rel=\"stylesheet\" href=\"SIP%20Video%20Intercom%20Config_files/style.css\" media=\"all\">");
        sb.append("</head><style>");
        sb.append("* {margin:0 auto;}#maincontainer h3{background:#fff;margin-top:20px}h3{background:#c2c2c2;display:block;color:#000;text-align:left;padding-top:5px;padding-bottom:5px;font-size: 16px;}");
        sb.append("#maincontainer{width:760px;\theight:800px;}");
        sb.append("#contentcontainer{float:left;padding:5px;margin:5x;\t}");
        sb.append(".confirm{margin-top:10px;margin-left:10px;}");
        sb.append("#access td{\tpadding-top:10px;}");
        sb.append("ul,li{margin:0;\tpadding:0;list-style:none;background:transparent;}");
        sb.append("#topcontainer{background:black;}");
        sb.append("#online{font-size:11;display:block;\tbackground:#c2c2c2;\tcolor:#fff;\ttext-decoration:none;margin-top:1px;margin-bottom:1px;}");
        sb.append("#footercontainer{height:60px;background:black;);}");
        sb.append("#contentcontainer td{font-size:13px;}");
        sb.append("#contentcontainer input{font-size:12px;}");
        sb.append("#topbanner h1{display:block;font-size:24px;\tcolor:#fff;\tbackground:transparent;\tmargin-left:10px;\tmargin-top:20px;float:left;text-decoration:blink;}");
        sb.append("#footercontainer h3{background:transparent;font-size:18px;}");
        sb.append("#topbanner{background:black; width:760px;height:60px;margin-top:2px;\tmargin-bottom:2px;}");
        sb.append("td{width:380px; padding:10px;}");
        sb.append("td.seperate{height:1px;padding:0px; width:380px;}");
        sb.append("</style><body><style>#nav1{background:#fff; color:#404343;}</style>");
        sb.append("<div id=\"topcontainer\">");
        sb.append("<div id=\"topbanner\">");
        sb.append("<h1>SIP VIDEO INTERCOM</h1></div></div>");
        sb.append("<div id=\"maincontainer\">");
        sb.append("<form method=\"get\" action=\"?\">");
        sb.append("<div id=\"content\" align=\"center\"><h3>&nbsp;&nbsp;&nbsp;Intercom Setting</h3><br>");
        sb.append("<HR style=\"FILTER: progid:DXImageTransform.Microsoft.Shadow(color:darkgray,direction:145,strength:15)\" width=\"100%\" color=darkgray SIZE=1>");
        sb.append("<br><table cellspacing=\"0\" cellpadding=\"0\">");
        sb.append("<input name=\"flag\" value=\"51\" type=\"hidden\">");
        sb.append("<input name=\"token\" value=\"72728\" type=\"hidden\">");
        sb.append("<tbody><tr><td>USER ID:&nbsp;</td><td>");
        sb.append("<input name=\"id\" size=\"30\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_ID, Utils.DEFAULT_ID));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>PASSWORD:&nbsp;</td><td>");
        sb.append("<input size=\"30\" name=\"password\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_PASSWORD, Utils.DEFAULT_PASSWORD));
        sb.append("\" type=\"password\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>VM-PASSWORD:&nbsp;</td><td>");
        sb.append("<input size=\"30\" name=\"vm-password\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_VMPASSWORD, Utils.DEFAULT_VMPASSWORD));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>TOLL ALLOW:&nbsp;</td><td>");
        sb.append("<input size=\"30\" name=\"toll_allow\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_TOLLALLOW, Utils.DEFAULT_TOLLALLOW));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>ACCOUNT CODE:&nbsp;</td><td>");
        sb.append("<input size=\"30\" name=\"accountcode\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_ACCOUNTCODE, Utils.DEFAULT_ACCOUNTCODE));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>USER CONTEXT:&nbsp;</td><td>");
        sb.append("<input name=\"user_context\" size=\"30\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_USERCONTEXT, Utils.DEFAULT_USERCONTEXT));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>EFFECTIVE CALLER ID NAME:&nbsp</td><td>");
        sb.append("<input size=\"30\" name=\"effective_caller_id_name\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_EFFECTIVE_CALLER_ID_NAME, Utils.DEFAULT_EFFECTIVE_CALLER_ID_NAME));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>EFFECTIVE CALLER ID NUMBER:&nbsp</td><td>");
        sb.append("<input size=\"30\" name=\"effective_caller_id_number\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_EFFECTIVE_CALLER_ID_NUMBER, Utils.DEFAULT_EFFECTIVE_CALLER_ID_NUMBER));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>OUTBOUND CALLER ID NAME:&nbsp</td><td>");
        sb.append("<input size=\"30\" name=\"outbound_caller_id_name\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_OUTBOUND_CALLER_ID_NAME, Utils.DEFAULT_OUTBOUND_CALLER_ID_NAME));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>OUTBOUND CALLER ID NUMBER:&nbsp</td><td>");
        sb.append("<input size=\"30\" name=\"outbound_caller_id_number\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_OUTBOUND_CALLER_ID_NUMBER, Utils.DEFAULT_OUTBOUND_CALLER_ID_NUMBER));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr><tr><td>CALL GROUP:&nbsp</td><td>");
        sb.append("<input size=\"30\" name=\"callgroup\" value=\"");
        sb.append((String)SharePreferenceUtils.get(context, Utils.XML_CALL_GROUP, Utils.DEFAULT_CALL_GROUP));
        sb.append("\" type=\"text\" style=\"float:right\">");
        sb.append("</td></tr></tbody></table><br>");
        sb.append("<HR style=\"FILTER: progid:DXImageTransform.Microsoft.Shadow(color:darkgray,direction:145,strength:15)\" width=\"100%\" color=darkgray SIZE=1>");
        sb.append("<br></div><div>");
        sb.append("<input value=\"Confirm\" class=\"confirm\" type=\"submit\">");
        sb.append("</div></form></div>");
        sb.append("<div id=\"footercontainer\">");
        sb.append("<div id=\"foottitle\">");
        sb.append("</div></div></body></html>");
        return sb.toString();
    }
}

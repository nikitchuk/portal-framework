package com.automation.portal.framework.utilz;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.security.MessageDigest;


public class TempMailUtil {

    private static HttpClient client = HttpClientBuilder.create().build();
    private final static String USER_AGENT = "Mozilla/5.0";
    private static String responseString;
    private JSONArray jSonArray;

    private final static String tempMailUrl = "http://api.temp-mail.ru/request";
    private final String outFormat = "/format/json/";
    private String mailFrom;
    private String mailSubj;
    private String mailText;
    private String mailTimeStamp;
    private String mailUniqueId;

    public TempMailUtil(String s) {
        getEmailList(s);
    }

    private void getEmailList(String s) {

        try {
            String url = tempMailUrl + "/mail/id/" + md5Hash(s) + outFormat;

            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", USER_AGENT);
            HttpResponse response = client.execute(request);

            responseString = EntityUtils.toString(response.getEntity());

            if (new JSONTokener(responseString).nextValue() instanceof JSONArray) {
                jSonArray = new JSONArray(responseString);
                int jSonArrayLenght = jSonArray.length() - 1;

                saveMailData((JSONObject) jSonArray.get(jSonArrayLenght));
                deleteEmail();
            }
        } catch (Exception e) {
            new Exception(e);
        }


    }

    private void deleteEmail() throws Exception {

        String url = tempMailUrl + "/delete/id/" + mailUniqueId;

        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);

        EntityUtils.toString(response.getEntity());

    }

    private String md5Hash(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(s.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private void saveMailData(JSONObject jsonObj) {
        this.mailFrom = jsonObj.getString("mail_from");
        this.mailSubj = jsonObj.getString("mail_subject");
        this.mailText = jsonObj.getString("mail_text_only");
        mailTimeStamp = jsonObj.getString("mail_timestamp");
        this.mailUniqueId = jsonObj.getString("mail_unique_id");
    }

    public String getFrom() {
        return mailFrom;
    }

    public String getSubj() {
        return mailSubj;
    }

    public String getText() {
        return mailText;
    }

    public String getTime() {
        return mailTimeStamp;
    }

}

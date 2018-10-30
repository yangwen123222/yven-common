package com.yven.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yven.domain.Imuser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 统计imuser中手机号码是否在集团注册过
 *
 */
public class CountAccountType {
    /**
     * 测试环境
     */
    private static final String TEST_ENV = "";

    /**
     * 正式环境
     */
    private static final String FORMAL_ENV = "";

    private static final String REQ_IP = FORMAL_ENV;


    public static void main(String[] args){
        // 从imuser中查询出userid和phonenum
        String[] phoneNumArr = new String[]{"12", "1562775","12546764"};
        for (String p : phoneNumArr) {
            System.out.println(isTclAccount(p));

        }
    }

    /**
     * 查看手机号是否是集团账号
     * @param phoneNum
     * @return
     */
    public static boolean isTclAccount(String phoneNum){
        String loginResult = getUserInfo(phoneNum);
        JSONObject jsonObject = JSON.parseObject(loginResult);
         System.out.println(jsonObject);
         if(jsonObject == null){
             System.out.println("====================== phoneNum:"+phoneNum);
             return false;

        }
        return jsonObject.getString("status").equals("104") ? false : true;
    }

    /**
     * 获取用户信息
     *
     * 返回状态 ：
     * 101 - 用户名为空
     * 102 - token为空
     * 103 - token错误
     * 104 - 用户不存在
     * 1401 - 用户名被冻结
     *
     * @param phoneNum
     * @return
     */
    public static String getUserInfo(String phoneNum){
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("username",phoneNum);
        reqMap.put("token","testCN_3c3ac07ceb");
        String res = HttpUtil.doPost(REQ_IP+"/account/getuserinfo",reqMap,"utf-8",true);
        return  res;
    }

    /**
     * 导出excel
     * @param imusers
     * @return
     * @throws Exception
     */
    public static void exportExcel(List<Imuser> imusers,String path) throws Exception {
        List<Imuser> li = new ArrayList<Imuser>();
        li.addAll(imusers);
        OutputStream out = new FileOutputStream(path);
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("userId", "智迅号");
        fields.put("mobilePhone", "手机号");
        fields.put("isTclAccount", "手机号是否为tcl账号");
        ExeclUtil.ListtoExecl(li, out, fields);
        out.close();
    }

    /**
     *
     * 导入
     * @return
     * @throws Exception
     */
    public static List<Imuser> ImportExcel(String path) throws Exception {
        InputStream in = new FileInputStream(path);
        Map<String, String> fieldd = new HashMap<String, String>();
        fieldd.put("智讯号", "userId");
        fieldd.put("手机号", "mobilePhone");
        List<Imuser> resultList = new ArrayList<Imuser>();
        resultList = ExeclUtil.ExecltoList(in, Imuser.class, fieldd);
        return resultList;

    }


}

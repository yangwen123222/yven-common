package com.yven.service;

import com.yven.dao.ControllerDaoJdbc;
import com.yven.domain.Imuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ControlService {

    @Autowired
    private ControllerDaoJdbc controllerDaoJdbc;

    public List<String> getThirdCtrlorList(Long devTid){
       List<String> result = controllerDaoJdbc.getThirdCtrlorList(devTid);
       return result;
    }

    public List<String> getThirdCtrlorList2(Long devTid){

        List<String> result = controllerDaoJdbc.getThirdCtrlorList2(devTid);
        return result;
    }

    public List<Imuser> getInfoFromImuser(){
        List<Imuser> imusers = controllerDaoJdbc.getInfoFromImuser();
        return  imusers;
    }


}

package com.yven.mongodb.service;

import com.yven.mongodb.entity.ClientInfoEntity;
import com.yven.mongodb.dao.ClientInfoMongoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoMongoService {

    @Autowired
    ClientInfoMongoDAO<ClientInfoEntity, String> clientInfoMongoDAO;


    public List<ClientInfoEntity> getByUserId(String userId) {

        return clientInfoMongoDAO.getByUserId(userId,null);
    }

}

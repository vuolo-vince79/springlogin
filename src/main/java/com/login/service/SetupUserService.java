package com.login.service;

import com.login.enum_message.ResponseMessage;
import com.login.exception.UserNotFoundException;
import com.login.model.SetupUser;
import com.login.repository.SetupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetupUserService {

    private final SetupUserRepository repository;

    @Autowired
    public SetupUserService(SetupUserRepository repository){
        this.repository = repository;
    }

    public SetupUser getSetupUser(long idUser){
        return repository.findByIdUser(idUser)
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
    }

    public void updateSetupUser(SetupUser setupUser){
        repository.save(setupUser);
    }
}

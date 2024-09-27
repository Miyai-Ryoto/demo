package com.example.demo.service;

import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserInfoRepository repository;

    private final Mapper mapper;

    private final PasswordEncoder passwordEncoder;

    public Optional<UserInfo> resistUserInfo(SignupForm form) {
        var userInfoExisteOpt = repository.findById(form.getLoginId());
        if (userInfoExisteOpt.isPresent()){
            return Optional.empty();
        }

        var userInfo = mapper.map(form, UserInfo.class);
        var encodedPassword = passwordEncoder.encode(form.getPassword());
        userInfo.setPassword(encodedPassword);

        return Optional.of(repository.save(userInfo));
    }

    

}

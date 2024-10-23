package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DepartmentInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserInfoRepository repository;

    private final DepartmentInfoRepository departmentInfoRepository;

    private final PasswordEncoder passwordEncoder;


    public Optional<UserInfo> resistUserInfo(SignupForm form) {
        var userInfoExisteOpt = repository.findByLoginId(form.getLoginId());
        if (userInfoExisteOpt.isPresent()){
            return Optional.empty();
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId(form.getLoginId());
        DepartmentInfo departmentInfo = departmentInfoRepository.findById(form.getDepartmentId()).orElse(null);
        userInfo.setDepartmentInfo(departmentInfo);
        var encodedPassword = passwordEncoder.encode(form.getPassword());
        userInfo.setPassword(encodedPassword);

        return Optional.of(repository.save(userInfo));
    }

    

}

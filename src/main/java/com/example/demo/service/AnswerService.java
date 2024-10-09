package com.example.demo.service;

import java.util.Optional;
import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AnswerInfo;
import com.example.demo.entity.PostInfo;
import com.example.demo.entity.PostsInfo;
import com.example.demo.entity.ResponsibleInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.AnswerForm;
import com.example.demo.form.ResponsibleForm;
import com.example.demo.repository.AnwerInfoRepository;
import com.example.demo.repository.PostInfoRepository;
import com.example.demo.repository.PostsInfoRepository;
import com.example.demo.repository.ResponsibleInfoRepository;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnwerInfoRepository anwerInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final PostInfoRepository postInfoRepository;

    private final PostsInfoRepository postsInfoRepository;

    private final ResponsibleInfoRepository responsibleInfoRepository;

    public void resistAnswerInfo(AnswerForm form, User user, Long id){

        AnswerInfo answerInfo = new AnswerInfo();
        answerInfo.setContent(form.getContent());
        UserInfo userInfo = userInfoRepository.findByLoginId(user.getUsername()).orElse(null);
        answerInfo.setUserInfo(userInfo);
        PostInfo postInfo = postInfoRepository.findById(id).orElse(null);
        answerInfo.setPostInfo(postInfo);
        anwerInfoRepository.save(answerInfo);
    }

    public void resistResponsibleInfo(ResponsibleForm form, Long id){

        ResponsibleInfo responsibleInfo = new ResponsibleInfo();
        UserInfo userInfo = userInfoRepository.findById(form.getUserId()).get();
        responsibleInfo.setUserInfo(userInfo);
        List<PostsInfo> postsInfo = postsInfoRepository.findByDepartmentInfo(userInfo.getDepartmentInfo());
        Optional<PostsInfo> foundInfo = postsInfo.stream()
                       .filter( info -> info.getPostInfo().equals(postInfoRepository.findById(id).orElse(null)))
                       .findFirst();
        responsibleInfo.setPostsInfo(foundInfo.get());
        responsibleInfoRepository.save(responsibleInfo);
    }

}

package com.example.demo.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.SignupMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.DepartmentInfoRepository;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController {

	private final SignupService service;

	private final MessageSource messageSource;

    private final DepartmentInfoRepository departmentInfoRepository;

	
	@GetMapping(UrlConst.SIGNUP)
	public String signupView(Model model, SignupForm form) {
        model.addAttribute("departments", departmentInfoRepository.findAll());
    		return "signup";
	}

	@PostMapping(UrlConst.SIGNUP)
	public void signup(Model model, @Validated SignupForm form, BindingResult bdResult) {
		if (bdResult.hasErrors()) {
			editGuideMessage(model, MessageConst.FORM_ERROR, true);
			return;
		}

		var userInfoOpt = service.resistUserInfo(form);
		var signupMessage = judgeMessageKey(userInfoOpt);
		editGuideMessage(model, signupMessage.getMessageId(), signupMessage.isError());
	} 

	private void editGuideMessage(Model model, String messageId, boolean isError) {
		var message = AppUtil.getMessage(messageSource, messageId);
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
		model.addAttribute("departments", departmentInfoRepository.findAll());
	}

	private SignupMessage judgeMessageKey(Optional<UserInfo> userInfoOpt) {
		if (userInfoOpt.isEmpty()) {
			return SignupMessage.EXISTED_LOGIN_ID;
		} else {
			return SignupMessage.SUCCEED;
		}
	}

}

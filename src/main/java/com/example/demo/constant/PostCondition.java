package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostCondition {

    ANSWERED("回答済"),

    UNANSWERED("未回答");

    private String  postMessage;

    public static PostCondition fromBoolean(boolean condition){
        return condition ? ANSWERED : UNANSWERED;
    }

    public static boolean toBoolean(PostCondition condition) {
        return condition == ANSWERED;
    }

}

package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskCondition {

    ANSWERED("回答済"),

    UNANSWERED("未回答");

    private String  postMessage;

    public static TaskCondition fromBoolean(boolean condition){
        return condition ? ANSWERED : UNANSWERED;
    }

    public static boolean toBoolean(TaskCondition condition) {
        return condition == ANSWERED;
    }

}

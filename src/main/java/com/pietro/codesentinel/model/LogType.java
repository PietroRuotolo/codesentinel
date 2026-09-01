package com.pietro.codesentinel.model;

import java.util.Optional;


public enum LogType {
    ERROR,
    INFO,
    DEBUG,
    WARN;

    public static Optional<LogType> parse(String str){
        for(var log : LogType.values()){
            if(str.equalsIgnoreCase(log.toString())){
                return Optional.of(log);
            }
        }
        return Optional.empty();
    }
}

package com.pietro.codesentinel.model;

import java.util.Optional;

public enum LogTypes {
    ERRO,
    INFO,
    DEBUG;

    public static Optional<LogTypes> parse(String str){
        for(var log : LogTypes.values()){
            if(str.equalsIgnoreCase(log.toString())){
                return Optional.of(log);
            }
        }
        return Optional.empty();
    }
}

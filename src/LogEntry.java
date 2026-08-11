import java.util.Optional;

enum LogTypes{
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

public record LogEntry(LogTypes level, String message) {

    public static Optional<LogEntry> from(String line) {
        if (line.startsWith("[") && line.contains("]")) {
            int index = line.indexOf("]");
            String levelName = line.substring(line.indexOf("[") + 1, index);
            String message = line.substring(index + 1);
            return LogTypes.parse(levelName)
                    .map(l -> new LogEntry(l, message));
        }
        return Optional.empty();
    }

    public Optional<String> getExceptionType(){
        if(!isError()) return Optional.empty();

        if(message.contains(":")){
            String exceptionLine = message.substring(0, message.indexOf(":"));
            if(exceptionLine.contains(".")){
                return Optional.of(exceptionLine.substring(exceptionLine.lastIndexOf(".") + 1));
            }
        }
        System.err.println("Cannot catch exception from: " + message);
        return Optional.empty();
    }


    public boolean isError() {
        return LogTypes.ERRO.equals(level);
    }

    @Override
    public String toString() {
        return "[%s] %s".formatted(level, message);
    }
}

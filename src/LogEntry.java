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
            String levelName = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            int index = line.indexOf("]") + 1;
            String message = line.substring(index);

            return LogTypes.parse(levelName)
                    .map(l -> new LogEntry(l, message));
        }
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

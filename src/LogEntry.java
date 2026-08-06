import java.util.Optional;

enum LogTypes{
    ERRO,
    INFO,
    DEBUG
}

public class LogEntry {
    private String level;
    private String message;

    public LogEntry(String level, String message) {
        this.level = level;
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public static Optional<LogEntry> from(String line){
        int index = line.indexOf("]") + 1;
        LogEntry entry = null;
        if(line.startsWith("[") && line.contains("]")){
            String levelName = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
            for(var type : LogTypes.values()){
                if(levelName.equalsIgnoreCase(type.toString())){
                    entry = new LogEntry(line.substring(0, index), line.substring(index));
                    break;
                }
            }
        }
        return Optional.ofNullable(entry);
    }

    public boolean isError(){
        return "[ERRO]".equals(level);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(level, message);
    }
}

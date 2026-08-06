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

    public static LogEntry from(String line){
        int index = line.indexOf("]") + 1;
        return new LogEntry(line.substring(0, index), line.substring(index));
    }

    public boolean isError(){
        return "[ERRO]".equals(level);
    }

    @Override
    public String toString() {
        return "%s %s".formatted(level, message);
    }
}

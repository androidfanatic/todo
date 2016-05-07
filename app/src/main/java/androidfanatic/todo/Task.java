package androidfanatic.todo;


import com.orm.SugarRecord;

public class Task extends SugarRecord {
    private String name;
    private boolean done = false;

    public Task() {

    }

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDone() {
        return done;
    }

    public String getWidgetString() {
        return String.format(" %s %s\r\n", done ? "\u2714" : "\u2718", name);
    }

    public void toggleDone() {
        done = !done;
    }
}
package pwr.karmil.lab14;

public class Bilet {
    private int queueNum;
    private String category;
    private String state;
    private String priority;

    public Bilet(int queueNum, String category, String state, String priority) {
        this.queueNum = queueNum;
        this.category = category;
        this.state = state;
        this.priority = priority;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getQueueNum() {
        return queueNum;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Bilet nr: " + queueNum + " (" + category + ") - Priorytet: " + priority;
    }
}
package deco2800.skyfall.managers;

public class FeedbackManager extends AbstractManager {

    //Text update boolean
    private boolean update;

    //Text to be updated
    private String text;

    public FeedbackManager() {
        update = false;
        text = "";
    }

    public void setFeedbackBarUpdate(boolean updated) {
        update = updated;
    }

    public boolean getFeedbackBarUpdate() {
        return update;
    }

    public void setFeedbackText(String text) {
        this.text = text;
    }

    public String getFeedbackText() {
        return text;
    }
}

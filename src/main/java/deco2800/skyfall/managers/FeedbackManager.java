package deco2800.skyfall.managers;

/**
 * Manager for text in FeedbackBar
 */
public class FeedbackManager extends AbstractManager {

    //Text update boolean
    private boolean update;

    //Text to be updated
    private String text;

    /**
     * Constructor
     */
    public FeedbackManager() {
        update = false;
        text = "";
    }

    /**
     * Sets update to let FeedbackBar know if text update is needed
     * @param updated True if text needs updating, False if not
     */
    public void setFeedbackBarUpdate(boolean updated) {
        update = updated;
    }

    /**
     * Gets whether update needed or not
     * @return Boolean update
     */
    public boolean getFeedbackBarUpdate() {
        return update;
    }

    /**
     * Sets text to be updated in FeedbackBar
     * @param text The text to be updated
     */
    public void setFeedbackText(String text) {
        this.text = text;
    }

    /**
     * Gets new text for FeedbackBar
     * @return the updated text
     */
    public String getFeedbackText() {
        return text;
    }
}

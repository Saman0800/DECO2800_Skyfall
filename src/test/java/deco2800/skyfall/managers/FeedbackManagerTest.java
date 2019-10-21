package deco2800.skyfall.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class FeedbackManagerTest {


    private FeedbackManager fm;
    @Before
    public void setUp() {
        fm = new FeedbackManager();
    }

  @Test
  public void setFeedbackBarUpdate() {
    assertFalse(fm.getFeedbackBarUpdate());
    fm.setFeedbackBarUpdate(true);
    assertTrue(fm.getFeedbackBarUpdate());
  }


  @Test
  public void setFeedbackText() {
      assertEquals("", fm.getFeedbackText());
      fm.setFeedbackText("test");
      assertEquals("test", fm.getFeedbackText());
  }


}
package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */

public class TweetLab
{
    private static twitter4j.Twitter twitterInstance = new TwitterFactory().getInstance();

    public static Twitter getTwitterInstance()
    {
        return twitterInstance;
    }

    public static void search(String query)
    {

    }
}

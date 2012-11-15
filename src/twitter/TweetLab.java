package twitter;

import twitter4j.*;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */

public class TweetLab
{
    private twitter4j.Twitter twitterInstance = new TwitterFactory().getInstance();


    public Twitter getTwitterInstance()
    {
        return twitterInstance;
    }

    public void searchAndSave(String query)
    {
        if (query != null && !query.isEmpty())
        {
            Twitter instance = getTwitterInstance();
            Query toSearch = new Query(query);
            try
            {
                QueryResult found = instance.search(toSearch);
                List<Status> tweets = found.getTweets();
                saveTweets(tweets);

            }
            catch (TwitterException e)
            {
                e.printStackTrace();
                System.out.println("Falha na busca: " + e.getMessage());
                System.exit(-1);
            }
        }
    }

    private boolean saveTweet(Status tweet)
    {
        if (tweet != null )
        {
            long tweetID = tweet.getId();
            String id = String.valueOf(tweetID);
            String title = "";
            String content = tweet.getText();
            User user = tweet.getUser();
            String author = user.getName();
            String date = tweet.getCreatedAt().toString();
            String link = mountTweetURL(user, id);
            String source = "TWITTER";

            System.out.println(id + title + content + author + date + link + source);
            return true;
        }
        else
        {
            return false;
        }
    }

    private String mountTweetURL(User user, String tweetId)
    {
        return user.getURL() + "/status/" + tweetId;
    }

    private void saveTweets(List<Status> tweets)
    {
        int fails = 0;
        int sucess = 0;

        for (Status tweet : tweets)
        {
            if (saveTweet(tweet))
                sucess += 1;
            else
                fails += 1;
        }

        System.out.println( sucess + "Tweets salvos e " + fails + " Tweets falharam." );
    }
}

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

    public static TweetLab getLabInstance()
    {
        return new TweetLab();
    }

    private Twitter getTwitterInstance()
    {
        return twitterInstance;
    }

    public void searchAndSave(String query)
    {
        saveTweets(search(query));
    }

    public List<Status> search(String query)
    {
        List<Status> result = null;

        if (query != null && !query.isEmpty())
        {
            Twitter instance = getTwitterInstance();
            Query toSearch = new Query(query);
            try
            {
                QueryResult found = instance.search(toSearch);
                result = found.getTweets();

            }
            catch (TwitterException e)
            {
                e.printStackTrace();
                System.out.println("Falha na busca: " + e.getMessage());
                System.exit(-1);
            }
        }
        return result;
    }

    public boolean saveTweet(Status tweet)
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

    public void saveTweets(List<Status> tweets)
    {
        if (tweets != null || tweets.size() <= 0)
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
        else
        {
            System.out.println( "não foram passados tweets" );
        }
    }
}

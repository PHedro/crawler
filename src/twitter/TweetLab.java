package twitter;

import java.sql.Connection;
import java.util.List;

import dbhandler.DBHandler;
import dbhandler.DBInsertableObject;
import twitter4j.*;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */

public class TweetLab
{
    private static final String TABLE = "document";
    private Twitter twitterInstance = new TwitterFactory().getInstance();

    private static DBHandler handler = new DBHandler();
    private Connection connection = null;

    public TweetLab(String url, String dbname, String user, String password)
    {
        if(connection == null)
        {
            connection = handler.getConnection(url, dbname, user, password);
        }
    }

    private Twitter getTwitterInstance()
    {
        return twitterInstance;
    }

    public void searchAndSave(String query)
    {
        saveTweets(search(query));
    }

    public List<Tweet> search(String query)
    {
        List<Tweet> result = null;

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

    private DBInsertableObject tweetToDBInsertableObject(Tweet tweet)
    {
        long tweetID = tweet.getId();
        String userName = tweet.getToUser();
        DBInsertableObject obj = new DBInsertableObject(TABLE);

        String id = String.valueOf(tweetID);
        obj.setValue("id", id);
        obj.setValue("title", "");
        obj.setValue("author", tweet.getFromUserName());
        obj.setValue("content", tweet.getText());
        obj.setValue("date", tweet.getCreatedAt());
        obj.setValue("link", mountTweetURL(tweet.getToUser(), id));
        obj.setValue("source", "TWITTER");

        return  obj;
    }

    public boolean saveTweet(Tweet tweet)
    {
        if (tweet != null )
        {
            long tweetID = tweet.getId();
            String userName = tweet.getToUser();

            String id = String.valueOf(tweetID);
            String title = "";
            String author = tweet.getFromUserName();
            String content = tweet.getText();
            String date = tweet.getCreatedAt().toString();
            String link = mountTweetURL(userName, id);
            String source = "TWITTER";

            System.out.println(source + id + title + content + author + date + link );
//            DBInsertableObject obj = tweetToDBInsertableObject(tweet);
//            obj.saveInDatabase(this.connection);

            return true;

        }
        else
        {
            return false;
        }
    }

    private String mountTweetURL(String userName, String tweetId)
    {
        return "https://twitter.com/"+ userName + "/status/" + tweetId;
    }

    public void saveTweets(List<Tweet> tweets)
    {
        if (tweets != null || tweets.size() <= 0)
        {
            int fails = 0;
            int sucess = 0;

            for (Tweet tweet : tweets)
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

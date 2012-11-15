package twitter;

import java.util.List;

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
    private Twitter twitterInstance = new TwitterFactory().getInstance();

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

    public boolean saveTweet(Tweet tweet)
    {
        if (tweet != null )
        {
            long tweetID = tweet.getId();
            String userName = tweet.getToUser();
            if (userName != null)
            {
                String id = String.valueOf(tweetID);
                String title = "";
                String author = tweet.getFromUserName();
                String content = tweet.getText();
                String date = tweet.getCreatedAt().toString();
                String link = mountTweetURL(tweet.getToUser(), id);
                String source = "TWITTER";

                System.out.println(id + title + content + author + date + link + source);
                return true;
            }
            else
            {
                System.out.println("O tweet " + tweetID + " não possui autor válido.");
                return false;
            }

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

    public static void main(String[] args)
    {
        TweetLab instance = TweetLab.getLabInstance();

        String query = "PHedro";

        instance.searchAndSave(query);
    }
}

import gmail.GmailLab;
import rss.RSSLab;
import twitter.TweetLab;

/**
 * Created with IntelliJ IDEA.
 * User: phedro
 * Date: 11/15/12
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main
{

    public static void main(String args[]) {

        RSSLab labr = new RSSLab();
        labr.getEntriesFromUrlAndSave("http://feeds.reuters.com/news/artsculture");
        labr.close();

        TweetLab instance = new TweetLab("localhost", "crawler", "root", "");

        String query = "futebol";

        instance.searchAndSave(query);


        GmailLab lab = new GmailLab();
        lab.connect("pedrohenriquereis@id.uff.br", "fredmarykika");
        lab.getMessagesFromFolderAndSave("Inbox");
    }
}

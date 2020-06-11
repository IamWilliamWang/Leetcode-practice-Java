import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Twitter {
    public static int clockTime = 0;
    private ArrayList<TwitterUser> users = new ArrayList<>();

    private class Tweet {
        public int tweetId, time;

        public Tweet(int tweetId) {
            this.tweetId = tweetId;
            this.time = clockTime++;
        }
    }

    private class TwitterUser {
        private int id;
        private ArrayList<Tweet> tweets = new ArrayList<>();
        private ArrayList<Integer> followeeIds = new ArrayList<>();

        public TwitterUser(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public ArrayList<Integer> getFolloweeIds() {
            return (ArrayList<Integer>) this.followeeIds.clone();
        }

        public void postTweet(int tweetId) {
            this.tweets.add(0, new Tweet(tweetId));
        }

        public List<Tweet> getTweets() {
            if (this.tweets.size() < 10)
                return (ArrayList<Tweet>) this.tweets.clone();
            return this.tweets.subList(0, 10);
        }

        public void follow(int followeeId) {
            if (this.id != followeeId && !this.followeeIds.contains(followeeId))
                this.followeeIds.add(followeeId);
        }

        public void unfollow(int followeeId) {
            if (this.followeeIds.contains(followeeId))
                this.followeeIds.remove((Integer) followeeId);
        }
    }

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
    }

    private void signUpNewUser(int userId) {
        this.users.add(new TwitterUser(userId));
    }

    private TwitterUser findUser(int userId) {
        List<TwitterUser> userList = this.users.stream().filter(user -> user.id == userId).collect(Collectors.toList());
        TwitterUser nowUser;
        if (userList.isEmpty()) {
            this.signUpNewUser(userId);
            nowUser = this.users.get(this.users.size() - 1);
        } else
            nowUser = userList.get(0);
        return nowUser;
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        TwitterUser nowUser = this.findUser(userId);
        nowUser.postTweet(tweetId);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        TwitterUser nowUser = this.findUser(userId);
        List<Tweet> feedTweets = nowUser.getTweets();
        for (int followId : nowUser.getFolloweeIds())
            feedTweets.addAll(this.findUser(followId).getTweets());
        feedTweets.sort(new Comparator<Tweet>() {
            @Override
            public int compare(Tweet t1, Tweet t2) {
                return t2.time - t1.time;
            }
        });
        List<Integer> feedTweetIds = new ArrayList<>();
        for (Tweet feedTweet : feedTweets) {
            if (!feedTweetIds.contains(feedTweet.tweetId))
                feedTweetIds.add(feedTweet.tweetId);
        }
        if (feedTweetIds.size() > 10)
            return feedTweetIds.subList(0, 10);
        return feedTweetIds;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        this.findUser(followerId).follow(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        this.findUser(followerId).unfollow(followeeId);
    }

    public static void main(String []args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1,5);
        twitter.postTweet(1,3);
        twitter.postTweet(1,101);
        twitter.postTweet(1,13);
        twitter.postTweet(1,10);
        twitter.postTweet(1,2);
        twitter.postTweet(1,94);
        twitter.postTweet(1,505);
        twitter.postTweet(1,333);
        twitter.postTweet(1,22);
        System.out.println(twitter.getNewsFeed(1));
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
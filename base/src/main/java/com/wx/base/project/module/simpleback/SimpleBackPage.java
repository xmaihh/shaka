package com.wx.base.project.module.simpleback;

public class SimpleBackPage {
//	ABOUT(1, R.string.actionbar_title_about, AboutFragment.class),
//	PROFILE(2, R.string.actionbar_title_profile, UserProfileFragment.class),
//	FRIENDS(3, R.string.actionbar_title_friends, FriendViewPagerFragment.class),
//	FAVORITES(4, R.string.actionbar_title_favorites, FavoriteViewPagerFragment.class),
//	SOFTEARE(5, R.string.actionbar_title_ossoftware, SoftwareViewPagerFragment.class),
//	COMMENT(6, R.string.actionbar_title_comment, CommentFragment.class),
//	QUESTION_TAG(7, R.string.actionbar_title_question, QuestionTagFragment.class),
//	USER_CENTER(8, R.string.actionbar_title_user_center, UserCenterFragment.class),
//	QUESTION_PUBLIC(9, R.string.actionbar_title_question_public, QuestionPublicFragment.class),
//	TWEET_PUBLIC(10, R.string.actionbar_title_tweet_public, TweetPublicFragment.class),
//	REPLY_COMMENT(11, R.string.actionbar_title_reply_comment, CommentReplyFragment.class),
//	MESSAGE_PUBLIC(12, R.string.actionbar_title_message_public, MessagePublicFragment.class),
//	MESSAGE_DETAIL(13, R.string.actionbar_title_message_detail, MessageDetailFragment.class),
//	SEARCH(14, R.string.actionbar_title_search, SearchViewPagerFragment.class),
//	LISENCE(16, R.string.actionbar_title_lisence, LisenceFragment.class),
//	MESSAGE_FORWARD(17, R.string.actionbar_title_message_forward, MessageForwardFragment.class),
//	NOTIFICATION_SETTINGS(18, R.string.actionbar_title_notification_settings, NotifIcationSettingsFragment.class),
//	DAILY_ENGLISH(19, R.string.actionbar_title_daily_english, DailyEnglishFragment.class),
//	EVENTS(20, R.string.actionbar_title_event, EventViewPagerFragment.class),
//	EVENT_APPLIES(21, R.string.actionbar_title_event_applies, EventAppliesFragment.class),
//	SHAKE(22, R.string.actionbar_title_shake, ShakeFragment.class);//,
//	QRCODE_SCAN(23, R.string.actionbar_title_qrcode_scan, QrCodeScanFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    public SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //public static SimpleBackPage getPageByValue(int val) {
    //	for (SimpleBackPage p : values()) {
    //		if (p.getValue() == val)
    //			return p;
    //	}
    //	return null;
    //}
}

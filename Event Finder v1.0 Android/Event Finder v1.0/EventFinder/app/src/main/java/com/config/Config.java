package com.config;

public class Config {

	// GOOGLE ANALYTICS TRACKING ID
	public static final String TRACKING_ID = "UA-54702380-3";

	// Enable Google Analytics
	public static final boolean ENABLE_GOOGLE_ANALYTICS = true;

	// Server API KEY
    // Do not change this. If you wish to change it, make sure
    // you change also the API_KEY in iOS (Config.h) and
    // PHP (Config.php)
    public static final String API_KEY = "ZZa4FYAH3ao0K";

	// Change this on your own consumer key
	public static final String TWITTER_CONSUMER_KEY = "R0u1hsVNb1lOEMzbxW6Ju1jcm";
	
	// Change this on your own consumer secret
	public static final String TWITTER_CONSUMER_SECRET = "CGIZRrb7mabu16g89U1Uz1oDKor7qxWkqpQy4KLfmWzA9VsQBd";
	
	// Set to true if you want to display test ads in emulator
	public static final boolean TEST_ADS_USING_EMULATOR = false;
	
	// Set to true if you want to display test ads on your testing device
	public static final boolean TEST_ADS_USING_TESTING_DEVICE = false;
	
	// Add testing device hash
	// It is displayed upon running the app, please check logcat.
	public static final String TESTING_DEVICE_HASH = "962DF709776186FBDC976A72D1FA61BD";
	
	// Set to true if you want to display ads in all views.
	public static final boolean WILL_SHOW_ADS = true;
	
	// You AdMob Banner Unit ID
	public static final String BANNER_UNIT_ID = "ca-app-pub-2715300260198944/9530294111";

	// Change this url depending on the name of your web hosting.
	public static String BASE_URL = "http://mangasaurgames.com/apps/eventfinder-v1.1/";

	// Facebook URL to be used when sharing
	public final static String SERVER_URL_DEFAULT_PAGE_FOR_GOOGLE_PLUS = "http://mangasaurgames.com";

	// Facebook URL to be used when sharing
	public final static String SERVER_URL_DEFAULT_PAGE_FOR_FACEBOOK = "http://mangasaurgames.com";

	// Twitter URL to be used when sharing
	public final static String SERVER_URL_DEFAULT_PAGE_FOR_TWITTER = "http://mangasaurgames.com";

	// Your email that you wish that users on your app will contact you.
	public static String ABOUT_US_EMAIL = "mangasaurgames@gmail.com";

    // Default filter distance in kilometers
    public final static int DEFAULT_FILTER_DISTANCE_IN_KM = 1000;

    // Max filter distance in kilometers
    public final static int MAX_RADIUS_STORE_VALUE_IN_KM = 5000;

	// Maximum value radius for search
	public static int SEARCH_RADIUS_MAX_VALUE = 1000;

	// Default value radius for search
	public static int SEARCH_RADIUS_DEFAULT_VALUE = 10;

	// Map zoom level
	public final static int MAP_ZOOM_LEVEL = 14;

	// adjust this depending on the offset of you map info window.
	public final static float MAP_INFO_WINDOW_X_OFFSET = 0.50f;

	// Number of characters allowed to type in the Event post.
	public static int MAX_CHARS_COMMENTS = 255;

	// Number of characters allowed to type in the Event description.
	public static int MAX_CHARS_DESC = 2500;

	// Max post per fetching count
	public static int MAX_POSTS_COUNT_PER_LISTING = 10;

	// Home slider minimum value
	public static int SLIDER_HOME_DEFAULT_MIN_VALUE_IN_KM = 200;

	// Home slider maximum value
	public static int SLIDER_HOME_NEARBY_MAX_VALUE_IN_KM = 500;

    // DO NOT EDIT THIS
    public final static String DELETE_EVENT_JSON_URL = BASE_URL + "rest/delete_event.php";

    // DO NOT EDIT THIS
	public final static String GET_FEATURED_EVENTS_JSON_URL = BASE_URL + "rest/get_featured_events.php";

    // DO NOT EDIT THIS
	public final static String INSERT_NEW_EVENT_URL = BASE_URL + "rest/insert_event.php";

    // DO NOT EDIT THIS
	public final static String GET_MY_EVENTS_JSON_URL = BASE_URL + "rest/get_my_events.php";

    // DO NOT EDIT THIS
    public final static String GET_EVENTS_JSON_URL = BASE_URL + "rest/get_events.php";

	// DO NOT EDIT THIS
	public final static String GET_POSTS_JSON_URL = BASE_URL + "rest/get_posts.php";

	// DO NOT EDIT THIS
	public final static String JOIN_EVENT_URL = BASE_URL + "rest/join_event.php";

	// DO NOT EDIT THIS
	public final static String POST_COMMENTS_URL = BASE_URL + "rest/post_comments.php";

	// DO NOT EDIT THIS
	public final static String REGISTER_URL = BASE_URL + "rest/register.php";

	// DO NOT EDIT THIS
	public final static String UPDATE_USER_PROFILE_URL = BASE_URL + "rest/update_user_profile.php";
	
	// DO NOT EDIT THIS
	public final static String SEARCH_EVENTS_URL = BASE_URL + "rest/search_events.php";

	// DO NOT EDIT THIS
	public final static String GET_NEWS_JSON_URL = BASE_URL + "rest/get_news.php";

	// DO NOT EDIT THIS
	public final static String GET_CATEGORIES_JSON_URL = BASE_URL + "rest/get_categories.php";

	// DO NOT EDIT THIS
	public final static int DELAY_SHOW_ANIMATION = 300;

    // DO NOT EDIT THIS
    public final static double DEBUG_LATITUDE = 37.68099454131698;

    // DO NOT EDIT THIS
    public final static double DEBUG_LONGITUDE= -122.41561878472567;

    // DO NOT EDIT THIS
    public final static boolean DEBUG_LOCATION = false;

	// DO NOT EDIT THIS
    public final static boolean AUTO_ADJUST_DISTANCE = true;

	// DO NOT EDIT THIS
	public final static int SPLASH_DELAY_IN_SECONDS = 3;

	// DO NOT EDIT THIS
	public final static boolean SHOW_LOCATION_COORDINATES_LOG = false;

	// DO NOT EDIT THIS
	public final static int PERMISSION_REQUEST_LOCATION_SETTINGS = 8882;

	public final static int REQUEST_CODE_PHONE_CALL = 8883;

	// DO NOT EDIT THIS
	public static int OFFSET_Y = 0;

	// DO NOT EDIT THIS
	public static String SD_CARD_PATH = "eventfinder_photos/";

	// DO NOT EDIT THIS
	public static float METERS_TO_KM = 0.001f;

	// DO NOT EDIT THIS
	public final static int RESULT_CODE_CATEGORY = 8888;

	// DO NOT EDIT THIS
	public final static int RESULT_CODE_NEW_EVENT = 8800;

	public final static int RESULT_CODE_UPDATE_EVENT = 8801;

	// DO NOT EDIT THIS
	public final static int RESULT_CODE_NEW_POST = 8802;

	// DO NOT EDIT THIS
	public final static int STATUS_SUCCESS = -1;

	// DO NOT EDIT THIS
	public static int RC_SIGN_IN = 8889;

    // DO NOT EDIT THIS
	public static float KM_TO_MILES = 0.621371f;

    // DO NOT EDIT THIS
	public static float DEFAULT_LATITUDE_TO_SHOW = 37.44477156495668f;

    // DO NOT EDIT THIS
	public static float DEFAULT_LONGITUDE_TO_SHOW = -122.23571774549782f;

    // DO NOT EDIT THIS
	public static String kGAIScreenNameCategories = "Categories Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameEventAdd = "Event Add Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameEventEdit = "Event Edit Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameEvents = "Events Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameEventDetail = "Event Detail Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameFavorites = "Favorites Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameFeatured = "Featured Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameHome = "Home Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameMyEvents = "My Events Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameSearch = "Search Screen";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameLabelDetailsEmail = "Email";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameLabelDetailsGooglePlus = "Share to GooglePlus";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameLabelDetailsTwitter = "Share to Twitter";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameLabelDetailsFacebook = "Share to Facebook";

    // DO NOT EDIT THIS
	public static String kGAIScreenNameLabelDetails = "Event Details View";
}

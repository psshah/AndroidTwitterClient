This is an Android application for searching images using Google search API using filters, and being able to zoom and share images. 

Time spent: 15 hours spent in total

Completed user stories:

Required:
- User can sign in to Twitter using OAuth login
- User can view the tweets from their home timeline. It displays username, body and relative timestamp for each tweet
- User can view more tweets as they scroll with infinite pagination

Optional: 
- Links in tweets are clickable and will launch the web browser 
- User can compose a new tweet and post it to twitter. Screen displays a count with total number of characters left for tweet.
- Once done composing, user is redirected back to home timeline with new tweet visible in timeline.
- User can refresh tweets timeline by pulling down to refresh 
- User can tap a tweet to display a "detailed" view of that tweet
- Changed the theme to look 'twitter' like.

Notes:
- Currently, app uses the naive approach of reloading all tweets when user tries pull-to-refrehs
- Started working on storing tweets offline in sqlite.

The following libraries are used to make this possible:

 * [scribe-java](https://github.com/fernandezpablo85/scribe-java) - Simple OAuth library for handling the authentication flow.
 * [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
 * [codepath-oauth](https://github.com/thecodepath/android-oauth-handler) - Custom-built library for managing OAuth authentication and signing of requests
 * [UniversalImageLoader](https://github.com/nostra13/Android-Universal-Image-Loader) - Used for async image loading and caching them in memory and on disk.
 * [ActiveAndroid](https://github.com/pardom/ActiveAndroid) - Simple ORM for persisting a local SQLite database on the Android device

 
 

Walkthrough of all user stories:

![Video Walkthrough](twitterclient.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).


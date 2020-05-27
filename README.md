# MovieManagementApplication


## 1. Introduction

  The problem that the Movie Management app wants to solve is to help a movie consumer organize their new movies and have a clear list of what they want to see.
The project, to solve the problem, presents the user with a list of movies that can be added to favorites to be saved locally, so that he can save time when he wants to watch a movie. At the same time, the user can filter the saved movies according to the minimum rating he wants, satisfying the desire to see a quality movie.




## 2. Describing the application

  The way the application was intended to be used is the following: a user who feels the need to organize the movies he wants to see creates an account and then adds to the list of favorites movies that interest him or that seem to be on his taste after seeing the poster, the rating on IMDB or reading the description of the movie. The user returns to a point when he wants to see a movie to select the movie to his liking from those saved in the application, limiting the risk that he forgets the movie that once aroused his interest.
  



## 3. Describing the functionalities

  The functionalities of the application are: registration / login to save movies, summary of newly released movies (Title, poster and IMDB rating), detailed presentation of movies (Title, poster, IMDB rating and movie description), adding movies in the favorites list, filtering saved movies by rating, recommending a cinema, changing the password and deleting the account and presenting a movie-based statistic and how many times one has been added to the favorites list.
  
## DEMO

![](demo.gif)

  
## 4. Implementation

  Application's stack: Java, SQLite, Firebase
  
  Also using: RecyclerView, CardView, ButterKnife, Picasso

  The application was developed using Android Studio and consumed the movie API provided by The Movie DB.
  
  The Movie DB API provides a JSON that contains movie information, from which I chose to use the ID, title, description, rating, release date, and movie poster.
  
  The movie poster is imported from JSON in the form of a string like "xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg" which I link to a standard link provided by TMDB ("https://image.tmdb.org/t/p/w500") which, accessed within the project he returns the poster.
Recording, logging and adding movies to the favorites list is done locally, using SQLite, the application having two tables: "user" (containing the ID which is the PK, username and password) and "favorites" (containing ID the record of the record which is PK, the username of the user who added the movie to the favorites as FK, the movie ID, title, rating, poster and movie description).

<div style="text-align:center"><img src="https://i.ibb.co/NWXM5w3/Picture-1.png" /></div>

  At the same time, each registered user is taken over by Firebase and then all users are displayed in the LiveUsersActivity activity.
  
  After registration / login the user is presented with the activity (MoviesActivity) which contains the list of newly released movies. The activity contains a Swipe Refresh Layout on which a RecyclerView is applied, which, horizontally, contains two Card Views in which the summary data of the film are loaded: Poster, Title and Rating.
  
  Data is retrieved from JSON using the GetMoviesAsync method, an asynchronous method that ParseJSON uses to retrieve information from the API and forward it to MoviesActivity.
  
  Once a film is found, the activity specific to the film opens, which, in addition to the title, poster and rating, also contains the presentation of the film. The information in the detailed activity is received by Intent from the Movie Class Adapter.
  
If you want to save the movie as a favorite, after pressing the button, a check is made in the database which returns whether the movie is already in the favorites or not and displays the corresponding message.

<div style="text-align:center"><img src="https://i.ibb.co/89Fss0N/Picture-2.png" /></div>
<div style="text-align:center"><img src="https://i.ibb.co/7GxNLKX/Picture-3.png" /></div>
<div style="text-align:center"><img src="https://i.ibb.co/f2B4XGx/Picture-4.png" /></div>


  In the Option Menu in the upper right of MoviesActivity there are three options, namely SettingsActivity, RateAppActivity and About Activity. RateAppActivity introduces SharedPrefferences to save the star rating in the preferences file,
SettingsActivity has various controls within the application:

  **FILTER YOUR MOVIES** is an option that offers the user the ability to filter their selected movies by rating.
  
  <div style="text-align:center"><img src="https://i.ibb.co/ypgQp5W/Picture-5.png" /></div>
  
  In the EditText that only accepts numeric characters, enter a number that will then be used in the Database Query based on the username of the logged in user and the rating that he enters: 
  ```db.rawQuery (" select * from favorite where username =? and rating> =? ", new String [] {username.toLowerCase (), value});```

  **CINEMA RECOMMANDATION** is an option that uses the Google Maps API to offer the recommendation of a cinema, namely the one in AFI Palace Cotroceni.

  **SHOW GRAPH** is an option that shows the user how many times a movie has been added by other users, through the local database:
```Cursor cursor = db.rawQuery ("SELECT movieid, COUNT (*) as nrids, title" + "FROM favorite" + "GROUP BY movieid", new String [] {});```

  **ACCOUNT SETTINGS** is an option that allows the user to either change their account password or delete their account.

  **SHOW FAVORITES** is an option that shows the user the report with the movies he added to favorites, in a MovieUserActivity activity that contains a ListView in which the movies in the database are loaded after the query: 
  ```Cursor cursor = db. rawQuery ("select * from favorite where username =?", new String [] {username.toLowerCase ()}); ```
    
  <div style="text-align:center"><img src="https://i.ibb.co/8dmdd1R/Picture-6.png" /></div>
  
  **SHOW LIVE USERS** is an option that presents to the user all the users who have registered in the application. The result is returned from Firebase and entered into a ListView.

  **DISCONNECT** takes the user back to the first page of the application.

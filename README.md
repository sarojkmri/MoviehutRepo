### Moviehut

Moviehut is collections of movies using TMDB api’s, where use can filter the movies based on 
- Top rated movies
- Most popular movies
- In Theaters/Latest movies
- Coming soon movies
 
- User can also see the movie details like rating, description and year of release onclick of any movie. 
- Users are also able to see a list of movies and details in offline mode.

#### Screenshots
<img width="385" alt="Screenshot 2021-05-15 at 10 05 46 PM" src="https://user-images.githubusercontent.com/7497436/118371470-6b1cc180-b5ca-11eb-9581-d5294d7b6f6d.png"> <img width="389" alt="Screenshot 2021-05-15 at 10 06 12 PM" src="https://user-images.githubusercontent.com/7497436/118371479-6fe17580-b5ca-11eb-98ea-e092b629bfee.png"> <img width="393" alt="Screenshot 2021-05-15 at 10 10 09 PM" src="https://user-images.githubusercontent.com/7497436/118371482-7112a280-b5ca-11eb-9550-f84e599e946e.png">


#### IMDB api’s used are 
- movie/top_rated
- movie/popular
- movie/now_playing
- movie/latest

#### Design used : 
- MVVM with Livedata is used to hold movie data which is observed by MovieListFragment and MovieDetailsFragment.
- Repository is holding all business logic like Api calling, saving data in Room and fetching data from Room.
- RxJava is used here with concat, filter and firstElement operators to check if data exists or not, if data exists it will not make an api call.
- Dagger is used for providing dependency of MovieRepository inside MovieViewModel.

<img width="422" alt="Screenshot 2021-05-15 at 9 10 07 PM" src="https://user-images.githubusercontent.com/7497436/118370248-7b31a280-b5c4-11eb-8075-4decf3b9ab9e.png">

#### Libraries used are :
- Retrofit - A type-safe HTTP client for Networking
- GsonConverterFactory - Converter for serialization to and from JSON
- RxJava - For asynchronous and executing event-based programs by using observable sequences.
- Dagger2 - For dependency injection framework
- Room - For database/offline caching
- Glide - For Image loading


#### For UI Handling : 
- ConstraintLayout - For designing layouts in a flexible way.
- RecyclerView - For showing the list of items in list/grid view.






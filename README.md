##### Api Key used for requesting list of movies
764f533c1885e0500d5d2b9da025ca8a

##### IMDB api’s used are 
- movie/top_rated
- movie/popular
- movie/now_playing
- movie/latest

##### Design used : 
- MVVM with Livedata is used to hold movie data which is observed by MovieListFragment and MovieDetailsFragment.
- Repository is holding all business logic like Api calling, saving data in Room and fetching data from Room.
- RxJava is used here with concat, filter and firstElement operators to check if data exists or not, if data exists it will not make an api call.
- Dagger is used for providing dependency of MovieRepository inside MovieViewModel.

<img width="422" alt="Screenshot 2021-05-15 at 9 10 07 PM" src="https://user-images.githubusercontent.com/7497436/118370248-7b31a280-b5c4-11eb-8075-4decf3b9ab9e.png">

##### Libraries used are :
- Retrofit - A type-safe HTTP client for Networking
- GsonConverterFactory - Converter for serialization to and from JSON
- RxJava - For asynchronous and executing event-based programs by using observable sequences.
- Dagger2 - For dependency injection framework
- Room - For database/offline caching
- Glide - For Image loading


##### For UI Handling : 
- ConstraintLayout - For designing layouts in a flexible way.
- RecyclerView - For showing the list of items in list/grid view.






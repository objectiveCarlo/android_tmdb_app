Basic Android TMDB App
===========================================
This is a basic app that uses paging 3 to handle pagination requirements.
The app need to connect to a remote service and cache the response via local db using Room.
So it is needed to implement [RemoteMediator](https://developer.android.com/reference/kotlin/androidx/paging/RemoteMediator).

![architecture](https://github.com/objectiveCarlo/android_tmdb_app/blob/main/gitassets/arch.png?raw=true) 

For image loading and caching this basic app heavily relies on [COIL-kt](https://github.com/coil-kt/coil)

Navigation Component is used to navigate from list to detail fragment.

<h2>Modern Android Components Used</h2>


1. MVVM architecture
1. Kotin
1. Kotlin Coroutines
1. Navigation Component
1. Paging 3
1. Dagger Hilt
1. Retrofit
1. Room DB
1. Image caching

<h3>App Features</h3>

1. [Get Upcoming Movies](https://github.com/objectiveCarlo/android_tmdb_app/tree/main/app/src/main/java/com/cxd/moviedbapp/features/upcoming)
1. [Get Popular Movies](https://github.com/objectiveCarlo/android_tmdb_app/tree/main/app/src/main/java/com/cxd/moviedbapp/features/popular)
1. [Add/Remove favorite movie](https://github.com/objectiveCarlo/android_tmdb_app/tree/main/app/src/main/java/com/cxd/moviedbapp/features/favorites)

<h2>Screenshots</h2>

<h4>Normal Mode<h4>
   
![normal mode](https://github.com/objectiveCarlo/android_tmdb_app/blob/main/gitassets/lightMode.jpg?raw=true) 

<h4>Dark Mode<h4>
   
![dark mode](https://github.com/objectiveCarlo/android_tmdb_app/blob/main/gitassets/darkMode.png?raw=true)
 

package studio.demo.subarashi_jpop

import android.app.Application
import studio.demo.subarashi_jpop.favouriteLocalService.favouriteRoomDatabase.FavouriteDatabase

class MainApplication : Application() {
    // lazily initialize the database using the FavouriteDatabase singleton
    val database by lazy { FavouriteDatabase.getDatabase(this) }
    // the database will be initialized only when actually needed, reducing the initial load at application startup.
}


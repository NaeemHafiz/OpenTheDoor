package com.example.openthedoor.data.local;//package com.mtech.travces.data.local;
//
//import android.content.Context;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//import io.reactivex.annotations.NonNull;
//
//@Database(entities = {}, version = 1, exportSchema = false)
//public abstract class AppRoomDatabase extends RoomDatabase {
//
//
//    private static volatile AppRoomDatabase INSTANCE;
//
//    public static AppRoomDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppRoomDatabase.class) {
//                if (INSTANCE == null) {
//                    // Create database here
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppRoomDatabase.class, "app_database")
//                            .addCallback(sRoomDatabaseCallback)
//                            .addMigrations()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            // Add any data in database here
//        }
//    };
//
//}

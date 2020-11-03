package com.bignerdranch.android.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bignerdranch.android.todo.database.TaskDao
import com.bignerdranch.android.todo.database.TaskTypeConverters
import com.bignerdranch.android.todo.Task

private const val DATABASE_NAME = "task-database"

@Database(entities = [ Task::class ], version=1)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Task ADD COLUMN description TEXT NOT NULL DEFAULT ''"
        )
    }
}
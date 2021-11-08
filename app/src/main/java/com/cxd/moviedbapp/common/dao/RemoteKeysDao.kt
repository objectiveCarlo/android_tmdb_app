package com.cxd.moviedbapp.common.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cxd.moviedbapp.common.models.paging.RemoteKeysEntity

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: RemoteKeysEntity)

    @Query("SELECT * FROM remote_keys WHERE id = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
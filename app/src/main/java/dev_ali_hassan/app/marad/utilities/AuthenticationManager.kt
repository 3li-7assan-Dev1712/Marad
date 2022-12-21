package dev_ali_hassan.app.marad.utilities

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore("user_auth")

@Singleton
class AuthenticationManager @Inject constructor(@ApplicationContext context: Context) {

    private val datastore: DataStore<Preferences> = context.dataStore



    val authenticationFlow = datastore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "error reading data from DataStore: ${it.message}")
            }
        }.map { preferences ->
            val userIsAuthenticated = preferences[DataStoreKeys.AUTH_KEY] ?: false
            userIsAuthenticated
        }

    val userAuthLiveData = authenticationFlow.asLiveData()

    suspend fun updateUserAuthState(userIsAuthenticated: Boolean) {
        datastore.edit { preferences ->
            preferences[DataStoreKeys.AUTH_KEY] = userIsAuthenticated
        }
    }


    private object DataStoreKeys {
        val AUTH_KEY = booleanPreferencesKey("user_is_authenticated")
    }

    companion object {
        const val TAG = "AuthenticationManager"
    }
}

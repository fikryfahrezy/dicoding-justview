package com.dicoding.justview.core.di

import androidx.room.Room
import com.dicoding.justview.core.BuildConfig
import com.dicoding.justview.core.data.ViewRepository
import com.dicoding.justview.core.data.source.local.LocalDataSource
import com.dicoding.justview.core.data.source.local.room.JvDatabase
import com.dicoding.justview.core.data.source.remote.RemoteDataSource
import com.dicoding.justview.core.data.source.remote.network.ApiService
import com.dicoding.justview.core.domain.repository.IViewRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val HOSTNAME = BuildConfig.API_HOSTNAME
private const val BASE_URL = "${BuildConfig.API_PROTOCOL}://$HOSTNAME${BuildConfig.API_ENDPOINT}"

val databaseModule = module {
    factory { get<JvDatabase>().jvDatabaseDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("justview".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            JvDatabase::class.java,
            "jvdb"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOSTNAME, "sha256/v/2C6MWlZC++lnd7MJn7vo47SSExqaF/sVzGUO8x8pM=")
            .add(HOSTNAME, "sha256/e0IRz5Tio3GA1Xs4fUVWmH1xHDiH2dMbVtCBSkOIdqM=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IViewRepository> {
        ViewRepository(get(), get())
    }
}
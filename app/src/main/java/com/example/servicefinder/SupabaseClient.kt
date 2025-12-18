package com.example.servicefinder

import io.github.jan-tennert.supabase.createSupabaseClient
import io.github.jan-tennert.supabase.SupabaseClient
import io.github.jan-tennert.supabase.auth.Auth
import io.github.jan-tennert.supabase.postgrest.Postgrest
import io.github.jan-tennert.supabase.postgrest.postgrest
import io.github.jan-tennert.supabase.postgrest.from

object SupabaseClientObj {  // Переименуй, чтобы не конфликтовало с классом SupabaseClient

    private const val SUPABASE_URL = "https://dkmdsogxvepkmmvrdokk.supabase.co"
    private const val SUPABASE_ANON_KEY = "sb_publishable_ZZdUPw-6SfaQ8QEf9JXySQ_fZx36zhS"

    val instance: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(Auth)
            install(Postgrest)
        }
    }

    val auth = instance.auth
    val postgrest = instance.postgrest

    suspend fun checkConnection(): Boolean {
        return try {
            postgrest.from("profiles").select().count() > 0 // Простая проверка
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

    private operator fun <T> Lazy<T>.getValue(supabaseClientObj: SupabaseClientObj, property: KProperty<T?>): T {

}

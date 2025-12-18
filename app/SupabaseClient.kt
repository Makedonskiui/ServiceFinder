package com.example.servicefinder

import io.github.jan.tenv.supabase.SupabaseClient
import io.github.jan.tenv.supabase.createSupabaseClient
import io.github.jan.tenv.supabase.gotrue.GoTrue
import io.github.jan.tenv.supabase.postgrest.Postgrest

object SupabaseClient {
    // ⚠️ ЗАМЕНИ НА СВОИ КЛЮЧИ ИЗ SUPABASE
    private const val SUPABASE_URL = "https://dkmdsogxvepkmmvrdokk.supabase.co"
    private const val SUPABASE_KEY = "sb_secret_DPjSIlvWgL7KNkrl9nGQrg_Ml1hz7uV"

    val instance: SupabaseClient = createSupabaseClient(SUPABASE_URL, SUPABASE_KEY) {
        install(GoTrue)
        install(Postgrest)
    }

    val auth = instance.auth
    val database = instance.postgrest
}
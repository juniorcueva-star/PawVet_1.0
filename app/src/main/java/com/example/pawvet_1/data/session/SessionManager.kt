package com.example.pawvet_1.data.session

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

data class SessionState(
    val isLoggedIn: Boolean = false,
    val userName: String = "",
    val userEmail: String = ""
)

data class AuthResult(
    val success: Boolean,
    val message: String
)

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _sessionState = MutableStateFlow(loadSession())
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    fun register(name: String, email: String, password: String): AuthResult {
        val cleanName = name.trim()
        val cleanEmail = email.trim().lowercase()
        val cleanPassword = password.trim()

        if (cleanName.isBlank() || cleanEmail.isBlank() || cleanPassword.isBlank()) {
            return AuthResult(false, "Completa todos los campos para crear tu cuenta.")
        }

        val accounts = loadAccounts()
        val alreadyExists = (0 until accounts.length()).any { index ->
            accounts.getJSONObject(index).optString(KEY_EMAIL) == cleanEmail
        }

        if (alreadyExists) {
            return AuthResult(false, "Ese correo ya está registrado. Inicia sesión.")
        }

        accounts.put(
            JSONObject()
                .put(KEY_NAME, cleanName)
                .put(KEY_EMAIL, cleanEmail)
                .put(KEY_PASSWORD, cleanPassword)
        )
        saveAccounts(accounts)
        saveSession(cleanName, cleanEmail)
        return AuthResult(true, "Cuenta creada correctamente.")
    }

    fun login(email: String, password: String): AuthResult {
        val cleanEmail = email.trim().lowercase()
        val cleanPassword = password.trim()

        if (cleanEmail.isBlank() || cleanPassword.isBlank()) {
            return AuthResult(false, "Ingresa tu correo y contraseña.")
        }

        val accounts = loadAccounts()
        val account = (0 until accounts.length())
            .map { accounts.getJSONObject(it) }
            .firstOrNull {
                it.optString(KEY_EMAIL) == cleanEmail &&
                    it.optString(KEY_PASSWORD) == cleanPassword
            }

        return if (account != null) {
            saveSession(
                account.optString(KEY_NAME),
                account.optString(KEY_EMAIL)
            )
            AuthResult(true, "Sesión iniciada.")
        } else {
            AuthResult(false, "Correo o contraseña incorrectos.")
        }
    }

    fun logout() {
        prefs.edit()
            .remove(KEY_CURRENT_NAME)
            .remove(KEY_CURRENT_EMAIL)
            .apply()
        _sessionState.value = SessionState()
    }

    private fun saveSession(name: String, email: String) {
        prefs.edit()
            .putString(KEY_CURRENT_NAME, name)
            .putString(KEY_CURRENT_EMAIL, email)
            .apply()
        _sessionState.value = SessionState(
            isLoggedIn = true,
            userName = name,
            userEmail = email
        )
    }

    private fun loadSession(): SessionState {
        val name = prefs.getString(KEY_CURRENT_NAME, "").orEmpty()
        val email = prefs.getString(KEY_CURRENT_EMAIL, "").orEmpty()
        return SessionState(
            isLoggedIn = name.isNotBlank() && email.isNotBlank(),
            userName = name,
            userEmail = email
        )
    }

    private fun loadAccounts(): JSONArray {
        val raw = prefs.getString(KEY_ACCOUNTS, null) ?: return JSONArray()
        return runCatching { JSONArray(raw) }.getOrElse { JSONArray() }
    }

    private fun saveAccounts(accounts: JSONArray) {
        prefs.edit().putString(KEY_ACCOUNTS, accounts.toString()).apply()
    }

    companion object {
        private const val PREFS_NAME = "pawvet_session"
        private const val KEY_ACCOUNTS = "accounts"
        private const val KEY_CURRENT_NAME = "current_name"
        private const val KEY_CURRENT_EMAIL = "current_email"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }
}

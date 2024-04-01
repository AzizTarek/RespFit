package com.example.respfitprototype

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class AuthTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var mockedUser: FirebaseUser
    private lateinit var authResultTask: Task<AuthResult>


    @Before
    fun setUp() {
        auth = mock(FirebaseAuth::class.java)
        mockedUser = mock(FirebaseUser::class.java)
        authResultTask = mock(Task::class.java) as Task<AuthResult>

        `when`(auth.currentUser).thenReturn(mockedUser)
        `when`(auth.signInWithEmailAndPassword("test@example.com", "password")).thenReturn(authResultTask)
        `when`(auth.createUserWithEmailAndPassword("test@example.com", "password")).thenReturn(authResultTask)
    }

    @Test
    fun testCurrentUser() {
        val user = auth.currentUser
        assert(user == mockedUser)
    }

    @Test
    fun testLogin() {
        // Mock the behavior of the Task<AuthResult> object
        `when`(authResultTask.isSuccessful).thenReturn(true)
        `when`(authResultTask.result).thenReturn(mock(AuthResult::class.java))

        val result = auth.signInWithEmailAndPassword("test@example.com", "password")
        assert(result == authResultTask)
    }

    @Test
    fun testSignUp() {
        // Mock the behavior of the Task<AuthResult> object
        `when`(authResultTask.isSuccessful).thenReturn(true)
        `when`(authResultTask.result).thenReturn(mock(AuthResult::class.java))

        val result = auth.createUserWithEmailAndPassword("test@example.com", "password")
        assert(result == authResultTask)
    }

    // Write more test methods for different scenarios
}

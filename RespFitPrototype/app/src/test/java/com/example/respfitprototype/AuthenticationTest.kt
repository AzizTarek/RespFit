package com.example.respfitprototype
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.mockito.Mockito.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class AuthenticationTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var mockedUser: FirebaseUser

    @Before
    fun setUp() {
        auth = mock(FirebaseAuth::class.java)
        mockedUser = mock(FirebaseUser::class.java)
        `when`(auth.currentUser).thenReturn(mockedUser)
    }

    @Test
    fun testCurrentUser() {
        val user = auth.currentUser
        assert(user == mockedUser)
    }

    // Write more test methods for different scenarios
}

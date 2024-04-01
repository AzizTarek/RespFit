package com.example.respfitprototype

import androidx.test.core.app.ApplicationProvider
import com.example.respfitprototype.data.AuthRepository
import com.example.respfitprototype.data.Resource
import com.example.respfitprototype.ui.auth.AuthViewModel
import com.example.respfitprototype.ui.UserDetails
import com.example.respfitprototype.ui.healthform.UserData
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nhaarman.mockitokotlin2.argumentCaptor
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Config.OLDEST_SDK])
class AuthTest2 {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var mockRepository: AuthRepository
    private lateinit var mockFirebaseAuth: FirebaseAuth
    private lateinit var mockCurrentUser: FirebaseUser
    private lateinit var mockDatabase: FirebaseDatabase
    @Before
    fun setUp() {
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
        mockRepository = mock(AuthRepository::class.java)
        mockFirebaseAuth = mock(FirebaseAuth::class.java)
        mockCurrentUser = mock(FirebaseUser::class.java)
        mockDatabase = mock(FirebaseDatabase::class.java)

        authViewModel = AuthViewModel(mockRepository)

        // Set up mocked dependencies
        `when`(mockRepository.currentUser).thenReturn(mockCurrentUser)
        doNothing().`when`(mockRepository).logout()
        runBlocking {
            `when`(mockRepository.login(anyString(), anyString())).thenReturn(Resource.Success(mockCurrentUser))

        }
        runBlocking {
            `when`(mockRepository.signup(anyString(), anyString(), anyString())).thenReturn(Resource.Success(mockCurrentUser))
        }
// Mock the behavior of mockDatabase.reference
        val mockDatabaseReference = mock(DatabaseReference::class.java)
        `when`(mockDatabase.reference).thenReturn(mockDatabaseReference)

        authViewModel.databaseRef = mockDatabaseReference

    }

    @Test
    fun testSubmitUserDetails() {
        val modExerciseMins = 60
        val vigExerciseMins = 30
        val age = 25
        val height = 170f
        val weight = 70f
        val respiratoryDisType = "Asthma"

        authViewModel.submitUserDetails(
            modExerciseMins,
            vigExerciseMins,
            age,
            height,
            weight,
            respiratoryDisType
        )

        // Verify that the submitted user details match the expected details
        val submittedDetails = authViewModel.userDetails.value
        val expectedUserDetails = UserDetails(
            userType = UserDetails.UserType.PATIENT,
            physicalActivityLevel = UserDetails.PhysicalActivityLevel.LOW,
            weightLevel = UserDetails.WeightLevel.NORMAL,
            modExerciseMins = modExerciseMins,
            vigExerciseMins = vigExerciseMins,
            age = age,
            height = height,
            weight = weight,
            respiratoryDisType = respiratoryDisType,
            physicalActivityProgress = 0f,
            weightProgress = 0f
        )

        assertEquals(expectedUserDetails.userType, submittedDetails?.userType)
        assertEquals(expectedUserDetails.physicalActivityLevel, submittedDetails?.physicalActivityLevel)
        assertEquals(expectedUserDetails.weightLevel, submittedDetails?.weightLevel)
        assertEquals(expectedUserDetails.modExerciseMins, submittedDetails?.modExerciseMins)
        assertEquals(expectedUserDetails.vigExerciseMins, submittedDetails?.vigExerciseMins)
        assertEquals(expectedUserDetails.age, submittedDetails?.age)
        assertEquals(expectedUserDetails.height, submittedDetails?.height)
        assertEquals(expectedUserDetails.weight, submittedDetails?.weight)
        assertEquals(expectedUserDetails.respiratoryDisType, submittedDetails?.respiratoryDisType)
        assertEquals(expectedUserDetails.physicalActivityProgress, submittedDetails?.physicalActivityProgress)
        assertEquals(expectedUserDetails.weightProgress, submittedDetails?.weightProgress)

    }



}

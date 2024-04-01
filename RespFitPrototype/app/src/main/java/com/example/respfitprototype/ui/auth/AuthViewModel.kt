package com.example.respfitprototype.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.respfitprototype.data.AuthRepository
import com.example.respfitprototype.data.Resource
import com.example.respfitprototype.ui.UserDetails
import com.example.respfitprototype.ui.healthform.UserData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {

    var databaseRef= FirebaseDatabase.getInstance("https://respfit-9fd8d-default-rtdb.europe-west1.firebasedatabase.app/").reference.child("users").child("USER"+ currentUser?.uid?:"")

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    // Private property to store the fetched user data
    private val _userDetails = MutableLiveData<UserDetails?>()
    val userDetails: LiveData<UserDetails?> = _userDetails
    val currentUser: FirebaseUser?
    get() = repository.currentUser
    init {
        if(repository.currentUser !=null ){ //user is already logged in
            _loginFlow.value = Resource.Success(repository.currentUser!!)
            fetchUserData()
        }
    }

    fun login(email:String,password:String) = viewModelScope.launch {
        if (email.isBlank() || password.isBlank()) {
            _loginFlow.value = Resource.Failure(IllegalArgumentException("Please enter both email and password."))
            return@launch
        }

        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        if (result is Resource.Success) {
            _loginFlow.value = result
        } else if (result is Resource.Failure) {
            _loginFlow.value = result
        }
    }
    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _signupFlow.value = Resource.Failure(IllegalArgumentException("Please enter your name, email, and password."))
            return@launch
        }

        _signupFlow.value = Resource.Loading
        val result = repository.signup(name, email, password)
        if (result is Resource.Success) {
            _signupFlow.value = result
            _loginFlow.value = result
        } else if (result is Resource.Failure) {
            _signupFlow.value = result
        }
    }

    fun submitUserDetails(  modExerciseMins: Int, vigExerciseMins: Int, age : Int, height: Float, weight: Float, respiratoryDisType : String)
    {
        val userType: UserDetails.UserType
        val physicalActivityLevel: UserDetails.PhysicalActivityLevel
        val weightLevel: UserDetails.WeightLevel

         if ( (modExerciseMins in 0..89) && (vigExerciseMins in 0..59))
        {
            physicalActivityLevel = UserDetails.PhysicalActivityLevel.LOW
        }else if ( (modExerciseMins in 89..300) && (vigExerciseMins in 59..150))
        {
            physicalActivityLevel = UserDetails.PhysicalActivityLevel.MODERATE
        }
         else if ( (modExerciseMins >300) && (vigExerciseMins >150))
        {
            physicalActivityLevel = UserDetails.PhysicalActivityLevel.HIGH
        }
        else
         {
             physicalActivityLevel = UserDetails.PhysicalActivityLevel.MODERATE
         }

        val BMI = weight  / ((height  / 100) * (height / 100))
        weightLevel =
        if (BMI <18.5)
            UserDetails.WeightLevel.UNDERWEIGHT
        else if (BMI in 18.5..24.9)
            UserDetails.WeightLevel.NORMAL
        else if (BMI >25)
            UserDetails.WeightLevel.OVERWEIGHT
        else
            UserDetails.WeightLevel.NORMAL



        // Creating a new user with their type, physical activity goal, and weight goal
        val newUser = UserDetails(
            userType = UserDetails.UserType.PATIENT,
            physicalActivityLevel = physicalActivityLevel,
            weightLevel = weightLevel,
            modExerciseMins = modExerciseMins,
            vigExerciseMins = vigExerciseMins,
            age =age,
            height= height,
            weight= weight,
            respiratoryDisType = respiratoryDisType,
            physicalActivityProgress = userDetails.value?.physicalActivityProgress?:0f,
            weightProgress = userDetails.value?.weightProgress?:0f
        )
        // Update the userDetails value in the ViewModel
        _userDetails.value = newUser
        val userData = UserData(
            userId = currentUser?.uid?:"",
            fullName = currentUser?.displayName?:"",
            email = currentUser?.email?:"",

            userDetails = newUser
        )
        val customDatabase = FirebaseDatabase.getInstance("https://respfit-9fd8d-default-rtdb.europe-west1.firebasedatabase.app/").reference
        // Save user data to the Realtime Database
        customDatabase.child("users").child("USER"+ currentUser?.uid?:"").setValue(userData)

    }

    fun fetchUserData() {
        val customDatabase = FirebaseDatabase.getInstance("https://respfit-9fd8d-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val userReference = customDatabase.child("users").child("USER"+ currentUser?.uid?:"").child("userDetails")

        // on below line adding value event listener for database reference.
       userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(UserDetails::class.java)
                _userDetails.value =value
            }

            override fun onCancelled(error: DatabaseError) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
            Log.d("vm", "Failed to get data")
                _userDetails.value =null
            }
        })
    }


    fun processBadges()
    {
        if ((userDetails.value?.weightProgress ?: 0f) >= 100f && (userDetails.value?.physicalActivityProgress ?: 0f) >= 100f)
        { //If the user has completed all assigned goals
        }

    }


    fun logout() {
        repository.logout()
        _loginFlow.value =null
        _signupFlow.value =null
        // Clear the user details
        _userDetails.value = null

    }
}
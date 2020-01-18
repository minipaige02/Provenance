package minipaige.example.provenance.com.Controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import minipaige.example.provenance.com.R
import minipaige.example.provenance.com.Utilities.RC_SIGN_IN

class LoginActivity : MainActivity() {
    //Google login implementation code taken from Myric September:
    // https://medium.com/@myric.september/authenticate-using-google-sign-in-kotlin-firebase-4490f71d9e44

    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        signInBtn.setOnClickListener{
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account!!.displayName != null) {
                    displayName = account.displayName.toString()
                } else {
                    displayName = account.email.toString()
                }

                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful) {
                val user = FirebaseAuth.getInstance().currentUser
                username = user!!.uid

                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)
            } else {
                Toast.makeText(this, "Google sign in failed.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {

            if (user.displayName != null) {
                displayName = user.displayName.toString()
            } else {
                displayName = user.email.toString()
            }
            username = user.uid

            val homeActivity = Intent(this, HomeActivity::class.java)
            startActivity(homeActivity)
        }
    }

}

package icesi.edu.co.icesicare.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import icesi.edu.co.icesicare.activities.AdminMainActivity
import icesi.edu.co.icesicare.activities.AuthActivity
import icesi.edu.co.icesicare.activities.PsychologistMainActivity
import icesi.edu.co.icesicare.activities.StudentMainActivity
import icesi.edu.co.icesicare.databinding.FragmentSignInBinding
import icesi.edu.co.icesicare.model.entity.Psychologist
import icesi.edu.co.icesicare.model.entity.Student
import icesi.edu.co.icesicare.viewmodel.AuthViewModel

class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    private val vm: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.signupLink.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.signupFragment)
        }
        binding.loginBtn.setOnClickListener {

            val email = binding.emailET.text.toString()
            val password = binding.passET.text.toString()

            vm.signIn(email,password)
        }

        binding.backBtnSignIn.setOnClickListener {
            val authActivity = activity as AuthActivity
            authActivity.loadFragment(authActivity.firstscreenFragment)
        }

        binding.recoverPassLink.setOnClickListener {
            if (binding.emailET.text.toString() == ""){
                Toast.makeText(requireContext(), "Por favor ingrese su correo para recuperar su contraseña", Toast.LENGTH_SHORT).show()

            }else{
                Firebase.auth.sendPasswordResetEmail(binding.emailET.text.toString())
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Revise su correo: "+binding.emailET.text.toString(), Toast.LENGTH_SHORT).show()

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        //Observer
        vm.authStateLV.observe(viewLifecycleOwner){ state ->
            if(state.isAuth){
                sendToCorrectActivity(state.userID.toString())
            }
        }
        vm.errorLV.observe(viewLifecycleOwner){error->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun sendToCorrectActivity(userId : String){
        vm.user.observe(viewLifecycleOwner){
            when(it){
                is Student
                    -> startActivity(Intent(requireContext(), StudentMainActivity::class.java))
                is Psychologist
                    -> {
                    if (it.approved && !it.pendingApproval)
                        startActivity(Intent(requireContext(), PsychologistMainActivity::class.java))
                    else{
                        showAlertDialog("Su solicitud de registro como psicólogo ha sido negada.")
                        vm.signOut()
                    }
                }
            }
        }
        vm.isAdmin.observe(viewLifecycleOwner){
            when(it){
                true -> startActivity(Intent(requireContext(), AdminMainActivity::class.java))
                else -> {}
            }
        }
        vm.getRoleOfLoggedUser(userId)
    }

    private fun showAlertDialog(message:String){
        val builder = AlertDialog.Builder(requireContext()).setTitle(message).setNeutralButton("OK",null)
        builder.create().show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}

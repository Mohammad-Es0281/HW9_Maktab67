package ir.es.mohammad.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ir.es.mohammad.login.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        binding.btnRegister.setOnClickListener { btnRegisterClickListener() }
    }

    private fun btnRegisterClickListener() {
        val bundle = bundleOf(
            "FullName" to binding.editTextFullName.text.toString(),
            "Username" to binding.editTextUsername.text.toString(),
            "Email" to binding.editTextEmail.text.toString(),
            "Password" to binding.editTextPassword.text.toString(),
            "Gender" to if (binding.radioButtonFemale.isChecked) "Female" else "Male"
        )
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SaveFragment>(R.id.fragContainer, args = bundle)
            addToBackStack("Register")
        }
    }
}
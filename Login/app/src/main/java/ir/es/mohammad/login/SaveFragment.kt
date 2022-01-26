package ir.es.mohammad.login

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ir.es.mohammad.login.databinding.FragmentRegisterBinding
import ir.es.mohammad.login.databinding.FragmentSaveBinding

class SaveFragment : Fragment(R.layout.fragment_save) {

    private lateinit var binding: FragmentSaveBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveBinding.bind(view)

        with(binding){
            textViewFullName.text = "Full name: ${arguments?.getString("FullName")}"
            textViewUsername.text = "Username: ${arguments?.getString("Username")}"
            textViewEmail.text = "Email: ${arguments?.getString("Email")}"
            textViewPassword.text = "Password: ${arguments?.getString("Password")}"
            textViewGender.text = "Gender: ${arguments?.getString("Gender")}"

            btnSave.setOnClickListener{ onSaveClicked() }
        }
    }

    private fun onSaveClicked(){
        this.activity!!.getSharedPreferences("UserInfo", MODE_PRIVATE).run {
            with(edit()) {
                putString("Full name", "Full name: ${binding.textViewFullName.text}")
                putString("Username", "Username: ${binding.textViewUsername.text}")
                putString("Email", "Email: ${binding.textViewEmail.text}")
                putString("Password", "Password: ${binding.textViewPassword.text}")
                putString("Gender", "Gender: ${binding.textViewGender.text}")
                apply()
            }
        }

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RegisterFragment>(R.id.fragContainer)
            addToBackStack("Save")
        }
    }
}
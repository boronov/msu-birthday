package tj.msu.birthday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tj.msu.birthday.R
import tj.msu.birthday.databinding.ChooseNapravDialogBinding
import tj.msu.birthday.interfaces.ChooseNapravListener

class ChooseNapravDialog(private val listener: ChooseNapravListener, val showAll: Boolean = true) :
    BottomSheetDialogFragment() {
    private lateinit var binding: ChooseNapravDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChooseNapravDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAll.isVisible = showAll

        binding.buttonGeo.setOnClickListener {
            listener.callbackNaprav(getString(R.string.geo), "Геология")
            dismiss()
        }

        binding.buttonGmu.setOnClickListener {
            listener.callbackNaprav(getString(R.string.gmu), "ГМУ")
            dismiss()
        }

        binding.buttonHfmm.setOnClickListener {
            listener.callbackNaprav(getString(R.string.hfmm), "ХФММ")
            dismiss()
        }

        binding.buttonLing.setOnClickListener {
            listener.callbackNaprav(getString(R.string.ling), "Лингвистика")
            dismiss()
        }

        binding.buttonMo.setOnClickListener {
            listener.callbackNaprav(getString(R.string.mo), "МО")
            dismiss()
        }

        binding.buttonPmii.setOnClickListener {
            listener.callbackNaprav(getString(R.string.pmii), "ПМиИ")
            dismiss()
        }
        binding.buttonAll.setOnClickListener {
            listener.callbackNaprav(getString(R.string.naprav), "")
            dismiss()
        }
    }
}
package tj.msu.birthday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.joda.time.LocalDate
import tj.msu.birthday.R
import tj.msu.birthday.databinding.ChooseCursDialogBinding
import tj.msu.birthday.databinding.ChooseNapravDialogBinding
import tj.msu.birthday.interfaces.ChooseCursListener
import tj.msu.birthday.interfaces.ChooseNapravListener

class ChooseCursDialog(private val listener: ChooseCursListener) : BottomSheetDialogFragment() {
    private lateinit var binding: ChooseCursDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChooseCursDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = LocalDate.now().year - 2000

        binding.buttonCurs1.setOnClickListener {
            listener.callbackCurs(getString(R.string.curs_1), year - 1)
            dismiss()
        }

        binding.buttonCurs2.setOnClickListener {
            listener.callbackCurs(getString(R.string.curs_2), year - 2)
            dismiss()
        }

        binding.buttonCurs3.setOnClickListener {
            listener.callbackCurs(getString(R.string.curs_3), year - 3)
            dismiss()
        }

        binding.buttonCurs4.setOnClickListener {
            listener.callbackCurs(getString(R.string.curs_4), year - 4)
            dismiss()
        }

        binding.buttonCurs5.setOnClickListener {
            listener.callbackCurs(getString(R.string.curs_5), year - 5)
            dismiss()
        }

        binding.buttonCursAll.setOnClickListener {
            listener.callbackCurs(getString(R.string.curs), 0)
            dismiss()
        }
    }
}
package ru.bullyboo.application.ui.bids.create

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.addOnMapLongClickListener
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.databinding.FragmentCreateBidsBinding
import ru.bullyboo.application.extensions.replaceFragment
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.application.ui.bids.done.DoneFragment
import javax.inject.Inject
import javax.inject.Provider

class BidsNewCreateFragment : BaseFragment<FragmentCreateBidsBinding>(
    FragmentCreateBidsBinding::inflate
), BidsNewCreateView {

    companion object {

        fun newInstance() = BidsNewCreateFragment()
    }

    @Inject
    internal lateinit var provider: Provider<BidsNewCreatePresenter>

    @InjectPresenter
    internal lateinit var presenter: BidsNewCreatePresenter

    @ProvidePresenter
    fun provide(): BidsNewCreatePresenter = provider.get()

    override fun onAttach(context: Context) {
        Dagger.app.main.provide().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            photosImage.setOnClickListener {
                ImagePicker.with(this@BidsNewCreateFragment)
                    .crop() //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start()

                binding.openMapText.setOnClickListener {

                }
            }

            sendBtn.setOnClickListener {
                val fragment = DoneFragment.newInstance()
                replaceFragment(fragment)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                binding.photosImage.setImageURI(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
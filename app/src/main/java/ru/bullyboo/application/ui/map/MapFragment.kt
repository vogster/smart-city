package ru.bullyboo.application.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.bullyboo.application.R
import ru.bullyboo.application.dagger.Dagger
import ru.bullyboo.application.databinding.FragmentMapBinding
import ru.bullyboo.application.ui.base.fragments.BaseFragment
import ru.bullyboo.application.ui.bids.create.BidsNewCreateFragment
import ru.bullyboo.application.ui.bids.create.BidsNewCreatePresenter
import javax.inject.Inject
import javax.inject.Provider

class MapFragment : BaseFragment<FragmentMapBinding>(
    FragmentMapBinding::inflate
), MapView {

    companion object {

        fun newInstance() = MapFragment()
    }

    @Inject
    internal lateinit var provider: Provider<MapPresenter>

    @InjectPresenter
    internal lateinit var presenter: MapPresenter

    @ProvidePresenter
    fun provide(): MapPresenter = provider.get()

    override fun onAttach(context: Context) {
        Dagger.app.main.provide().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            mapView.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS
            ) { addAnnotationToMap() }
        }
    }

    private fun addAnnotationToMap() {
        bitmapFromDrawableRes(
            requireContext(),
            R.drawable.red_marker
        )?.let {
            val annotationApi = binding.mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager(binding.mapView)
            val pointAnnotationOptions1 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.617837, 42.839065))
                .withIconImage(it)
            val pointAnnotationOptions2 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.603352, 42.857135))
                .withIconImage(it)
            val pointAnnotationOptions3 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.549251, 42.855445))
                .withIconImage(it)
            val pointAnnotationOptions4 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.537944, 42.846229))
                .withIconImage(it)
            val pointAnnotationOptions5 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.564561, 42.815643))
                .withIconImage(it)
            val pointAnnotationOptions6 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.666881, 42.867939))
                .withIconImage(it)
            val pointAnnotationOptions7 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.601138, 42.922976))
                .withIconImage(it)
            val pointAnnotationOptions8 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.647857, 42.825304))
                .withIconImage(it)
            val pointAnnotationOptions9 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.494474, 42.845579))
                .withIconImage(it)
            val pointAnnotationOptions10 = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(74.603672, 42.845809))
                .withIconImage(it)

            pointAnnotationManager.create(
                listOf(
                    pointAnnotationOptions1,
                    pointAnnotationOptions2,
                    pointAnnotationOptions3,
                    pointAnnotationOptions4,
                    pointAnnotationOptions5,
                    pointAnnotationOptions6,
                    pointAnnotationOptions7,
                    pointAnnotationOptions8,
                    pointAnnotationOptions9,
                    pointAnnotationOptions10,
                )
            )
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
//        binding.mapView.onDestroy()
    }
}
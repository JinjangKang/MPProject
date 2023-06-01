package com.example.pit_a_pet

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.Fragment
import com.example.pit_a_pet.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.io.IOException
import java.util.jar.Attributes.Name

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class MapFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Places.initialize(requireContext(), "YOUR_API_KEY") // Google Places API 키를 입력하세요.
        placesClient = Places.createClient(requireContext())

        binding = FragmentMapBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 원하는 주소를 인자로 전달하여 초기 마커를 추가합니다.
        addMarkerWithAddress("서울특별시 노원구 공릉동 362-3번지", "엔젤동물병원")
        addMarkerWithAddress("서울특별시 노원구 공릉동 323-10", "라라Q동물병원")
        addMarkerWithAddress("서울특별시 노원구 공릉동 441-152", "스누피동물병원")




        // 기타 설정 및 로직을 추가할 수 있습니다.
    }


    private fun addMarkerWithAddress(address: String, name: String) {
        val geocoder = Geocoder(requireContext())
        val locationList = geocoder.getFromLocationName(address, 1)
        if (locationList!!.isNotEmpty()) {
            val location = LatLng(locationList[0].latitude, locationList[0].longitude)
            val markerOptions = MarkerOptions()
                .position(location)
                .title(address)
                .snippet(name)
            googleMap.addMarker(markerOptions)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        } else {
            Toast.makeText(requireContext(), "주소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }






//    override fun onMapReady(map: GoogleMap) {
//        googleMap = map
//
//        // 위치 권한이 허용되어 있는지 확인합니다.
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            // 위치 권한이 허용된 경우 현재 위치를 가져옵니다.
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    location?.let {
//                        val currentLocation = LatLng(location.latitude, location.longitude)
//                        googleMap.addMarker(MarkerOptions().position(currentLocation).title("현재 위치"))
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
//                    }
//                }
//        } else {
//            // 위치 권한이 허용되지 않은 경우 권한을 요청합니다.
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST_CODE
//            )
//        }
//    }

//    override fun onMapReady(map: GoogleMap) {
//        googleMap = map
//
//        val markers = listOf(
//            MarkerInfo(LatLng( 37.62849955895432, 127.07196367887758), "엔젤동물병원"),
//            MarkerInfo(LatLng(37.62849955895432, 127.07196367887758), "수피아동물병원"),
//            MarkerInfo(LatLng(37.631961982417316, 127.07496351866536), "서울과학기술대학교 정문"),
//            // 추가적인 마커 정보를 리스트에 추가할 수 있습니다.
//        )
//
//        for (markerInfo in markers) {
//            val markerOptions = MarkerOptions()
//                .position(markerInfo.location)
//                .title(markerInfo.title)
//            googleMap.addMarker(markerOptions)
//        }
//
//        val initialMarker = markers.firstOrNull()
//        if (initialMarker != null) {
//            val initialLocation = initialMarker.location
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15f))
//        }
//    }
//
//    // 마커 정보를 담는 데이터 클래스
//    data class MarkerInfo(val location: LatLng, val title: String)



//    private fun addMarkerOnCurrentLocation(location: Location) {
//        val currentLocation = LatLng(location.latitude, location.longitude)
//        googleMap.addMarker(MarkerOptions().position(currentLocation).title("현재 위치"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
//    }
//
//    private fun searchLocation(locationName: String) {
//        val geocoder = Geocoder(requireContext())
//        try {
//            val addressList = geocoder.getFromLocationName(locationName, 1)
//            if (!addressList.isNullOrEmpty()) {
//                val address = addressList[0]
//                val latitude = address.latitude
//                val longitude = address.longitude
//                val location = LatLng(latitude, longitude)
//
//                googleMap.clear()
//                googleMap.addMarker(MarkerOptions().position(location).title(locationName))
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
//            } else {
//                Toast.makeText(requireContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//            Toast.makeText(requireContext(), "검색 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
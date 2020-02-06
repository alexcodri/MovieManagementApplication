package eu.ase.moviemanagementapplication.UserAccessManagement;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import eu.ase.moviemanagementapplication.R;

public class GmapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;

        LatLng AfiCotroceni = new LatLng(44.424664968,26.051499794);
        map.addMarker(new MarkerOptions().position(AfiCotroceni).title(getString(R.string.Afi_title)));
        map.moveCamera(CameraUpdateFactory.newLatLng(AfiCotroceni));

    }
}

package network.solidary.mobile;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import io.ptech.cc.android.sdk.exported.CitizenCard;
import io.ptech.cc.android.sdk.exported.CitizenCardReader;
import io.ptech.cc.android.sdk.exported.exceptions.CardException;
import io.ptech.cc.android.sdk.exported.exceptions.InvalidLicenseException;
import io.ptech.cc.android.sdk.exported.listeners.OnEventsListener;
import io.ptech.cc.android.sdk.exported.model.Person;

public class MainActivity extends AppCompatActivity {

  private final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final TextView textView = findViewById(R.id.textview);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Person person = CitizenCard.getPublicData();
          Log.d(TAG, person.toString());
        } catch (CardException e) {
          e.printStackTrace();
        }
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      }
    });

    try {
      String packageName = getApplicationContext().getPackageName();
      String encodedLicense = "eyJ2ZXJzaW9uIjoxLCJ0eXBlIjoiQ0xJRU5UIiwiaW5mbyI6eyJhcHBsaWNhdGlvbiI6Im5ldHdvcmsuc29saWRhcnkubW9iaWxlIiwiYXBpS2V5IjoiMDY3NTk2OWMtY2U2Yi00NzM1LTgyMWQtNzFmYzZhYThiZDlmIiwicGxhdGZvcm1zIjpbIklPUyIsIkFORFJPSUQiXSwib3BzIjpbIlJFQURfUFVCTElDX0RBVEEiLCJSRUFEX0FERFJFU1MiXSwiZXhwaXJhdGlvbkRhdGUiOjE1OTA5NjYwMDAwMDB9LCJzaWduYXR1cmUiOiJPM3lUVFg1ZDlORlkyNTBheTNlZjkxOU8vcm1LbVM5MkNYYUxOYWN5M2x5SzMzcGJJNjFOSUh1dGViUXdhcUczVkdCazMwNXFSMHhidVFXRVRiRDRKeUtINUttUU9yb2RJWnAyY3pRNGdRUUUxVUp5OGRjUHZMa21CWk15aVl0T1oyaEdrWVZ6cjlsNHRLVUdzK2FjNDU4MnBNOUdocUdXMzFqOVNxM0ZYWllIYi94czlDZTRLcXlPcU96eTh1SjVLRlZVV3dLaElwUXROb2N3Yld5dEZ2cUZrY1NLU1BVWkUrR3N0M1RhRVd0TVo5TlY5V08xY0txS0pZMjhzamhaYmZ5TE9iYlYyTDhWdzJETzJHTXl2dk1yNFFxQnJtaU16bDhyUmlQdlJkWVVFTXdvc2ZUZHA1cHg3bVhFK2kvRFdobGo5eWw3Z1RZRzcxQ2tycTllRVE9PSJ9";
      String remoteHostAddress = "https://api.citizencard.cadsh.com";
      String deviceID = getDeviceId(getApplication());
      Log.d(TAG, packageName);

      // Application application, String remoteHostAddress, String license, String deviceID

      // your application context
      CitizenCardReader.setup(getApplication(),
        // the base url to the signatures server
        remoteHostAddress,
        // your license encoded as Base64
        encodedLicense,
        // your deviceID
        deviceID)
        .connect(new OnEventsListener() {
          @Override
          public void onEvent(EventType eventType) {
            if (eventType.equals(EventType.CARD_READY)) {
              Log.i(TAG, "Card is ready for operations...");
              // At this point you can start using all the card functionality
              Person person = null;
              try {
                person = CitizenCard.getPublicData();
              } catch (CardException e) {
                e.printStackTrace();
              }
              Log.d(TAG, person.toString());
              textView.setText(person.toString());
            }
          }
        });
    } catch (InvalidLicenseException e) {
      Log.i(TAG, "Detected invalid license: " + e.getMessage());
    // } catch (InternalErrorException e) {
    //  Log.i(TAG, "Failed to initialize the card reader: " + e.getMessage());
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public static String getDeviceId(Context context) {
    String androidId = Settings.Secure.getString(
      context.getContentResolver(), Settings.Secure.ANDROID_ID);

    return androidId;
  }
}

package network.solidary.mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.cert.X509Certificate;

import io.ptech.cc.android.sdk.exported.CitizenCard;
import io.ptech.cc.android.sdk.exported.CitizenCardReader;
import io.ptech.cc.android.sdk.exported.exceptions.CardException;
import io.ptech.cc.android.sdk.exported.exceptions.InvalidLicenseException;
import io.ptech.cc.android.sdk.exported.listeners.OnEventsListener;
import io.ptech.cc.android.sdk.exported.model.Person;

public class MainActivity extends AppCompatActivity {

  private final String TAG = "SNCitizenCardReader";
  private ImageView bitmapPhoto;
  private TextView tvCardType;
  private TextView tvEventBox;
  private TextView tvLog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = findViewById(R.id.fab);
    bitmapPhoto = findViewById(R.id.imPhoto);
    tvCardType= findViewById(R.id.tvCardType);
    tvEventBox = findViewById(R.id.tvEventStatus);
    tvLog = findViewById(R.id.tvLog);
    tvLog.setMovementMethod(new ScrollingMovementMethod());

    final String remoteHostAddress = "https://api.citizencard.cadsh.com";

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        readCard();
      }
    });

    try {
      String encodedLicense = network.solidary.mobile.BuildConfig.encodedLicense;
      String deviceID = getDeviceId(getApplication());
      String packageName = getApplicationContext().getPackageName();
      Log.d(TAG, String.format("packageName: %s", packageName));
      Log.d(TAG, String.format("deviceID: %s", deviceID));

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
            String message = "Unknown...";
            switch (eventType) {
              case SEARCHING_READER:
                message = "Searching Reader";
                Log.e(TAG, message);
                break;
              case READER_DISCONNECTED:
                message = "Reader disconnected";
                Log.i(TAG, message);
                break;
              case REQUESTING_USB_PERMISSIONS:
                message = "Requesting usb permissions";
                Log.i(TAG, message);
                break;
              case USB_PERMISSIONS_REFUSED:
                message = "Usb permissions refused";
                Log.e(TAG, message);
                break;
              case READER_POWERING_UP:
                message = "Reader powering up";
                Log.i(TAG, message);
                break;
              case READER_POWERUP_FAILED:
                message = "Reader power up failed";
                Log.e(TAG, message);
                break;
              case READER_READY:
                message = "Reader ready";
                Log.i(TAG, message);
                break;
              case CARD_POWERING_UP:
                message = "Card powering up";
                Log.i(TAG, message);
                break;
              case CARD_STATUS_UNKNOWN:
                message = "Card status unknown";
                Log.e(TAG, message);
                break;
              case CARD_INITIALIZING:
                message = "Card initializing";
                Log.i(TAG, message);
                break;
              case CARD_DETECTED:
                message = "Card detected";
                Log.i(TAG, message);
                break;
              case CARD_READY:
                // At this point you can start using all the card functionality
                message = "Card ready";
                Log.i(TAG, message);
                readCard();
                break;
              case CARD_REMOVED:
                message = "Card removed";
                Log.i(TAG, message);
                break;
              case CARD_ERROR:
                message = "Card error";
                Log.e(TAG, message);
                break;
              case BLUETOOTH_PAIRING_CORRUPTED:
                message = "Bluetooth pairing corrupt";
                Log.e(TAG, message);
                break;
            }

            updateEventStatus(message);
          }
        });
    } catch (InvalidLicenseException e) {
      Log.i(TAG, "Detected invalid license: " + e.getMessage());
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

  @SuppressLint("HardwareIds")
  public static String getDeviceId(Context context) {
    return Settings.Secure.getString(
      context.getContentResolver(), Settings.Secure.ANDROID_ID);
  }

  private void updateEventStatus(final String statusMessage){
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        tvEventBox.setText(statusMessage);
      }
    });
  }

  private void readCard() {
    try {
      // get card type
      updateEventStatus("Read card type");
      String cardType = CitizenCard.getCardType();
      // get Picture
      updateEventStatus("Read picture");
      Bitmap picture = CitizenCard.getPicture();
      // get public info
      updateEventStatus("Read public info");
      Person person = CitizenCard.getPublicData();
      // log person object
      Person formattedPersonData = Util.formatPersonData(person);
      // get certificates
      X509Certificate signingCert = CitizenCard.getSigningCertificate();
      X509Certificate signingCACert = CitizenCard.getCASigningCertificate();
      X509Certificate authCert = CitizenCard.getAuthenticationCertificate();
      X509Certificate authCACert = CitizenCard.getCAAuthCertificate();
      X509Certificate rootCACert = CitizenCard.getCARootCertificate();

      // set PersonPayload
      PersonPayload personPayload = new PersonPayload(cardType, person, signingCert, signingCACert, authCert, authCACert, rootCACert);

      // log
      // Log.d(TAG, formattedPersonData.toString());
      // update UI
      bitmapPhoto.setImageBitmap(picture);
      tvCardType.setText(cardType);
      tvLog.append(formattedPersonData.toString() + "\n");

      // show snackBar
      Snackbar.make(tvLog, "Citizen card public info read", Snackbar.LENGTH_SHORT)
        .setAction("Action", null).show();
    } catch (CardException e) {
      e.printStackTrace();
    }
  }
}

package pl.softace.passwordless;

import pl.softace.passwordless.server.SmsServer;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * 
 * Main activity of the application. 
 * 
 * @author lukasz.nowak@homersoft.com
 *
 */
public class MainActivity extends Activity {
	
	/**
	 * Sms server.
	 */
	private SmsServer smsServer;
	
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);            
        
        smsServer = new SmsServer(this);        
        final Button button = (Button) findViewById(R.id.buttonStart);
        button.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) { 
				if (button.getText().equals(getString(R.string.button_start))) {
					Thread thread = new Thread(smsServer);
					thread.start();            
					
					button.setText(getString(R.string.button_stop));
				} else {
					smsServer.stop();					
					
					button.setText(getString(R.string.button_start));
				}
			}
		});        
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

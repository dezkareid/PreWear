package goio.mx.prewear;


import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.RemoteInput;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class Wear extends Activity implements OnClickListener{

	public int count = 0;
	public String notify;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wear);
		Button boton = (Button) findViewById(R.id.simple);
		boton.setOnClickListener(this);
		boton = (Button) findViewById(R.id.icon);
		boton.setOnClickListener(this);
		boton = (Button) findViewById(R.id.voice);
		boton.setOnClickListener(this);
		notify = Context.NOTIFICATION_SERVICE;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		NotificationCompat.Builder notificationBuilder = null;
		Intent viewIntent = new Intent(this, Wear.class);
		Boolean withoutIcon = false;
		switch (v.getId()) {
		case R.id.simple:
			notificationBuilder =
	        new NotificationCompat.Builder(getApplicationContext())
			.setSmallIcon(R.drawable.ic_launcher)
	        .setContentTitle(getString(R.string.simple_button_title))
	        .setContentText(getString(R.string.simple_button_message));
			withoutIcon = true;
			break;
		case R.id.icon:
			
			
			viewIntent.putExtra("Hola", 0);
			PendingIntent viewPendingIntent =
			        PendingIntent.getActivity(this, 0, viewIntent, 0);

			notificationBuilder =
			        new NotificationCompat.Builder(this)
			        .setSmallIcon(R.drawable.puke_rainbows)
			        .setLargeIcon(BitmapFactory.decodeResource(
			                getResources(), R.drawable.puke_rainbows))
			        .setContentTitle(getString(R.string.image_button_title))
			        .setContentText(getString(R.string.image_button_message))
			        .setContentIntent(viewPendingIntent);
					
			break;
		
		
		case R.id.voice:

			String replyLabel = getResources().getString(R.string.voz_button_title);
			String[] replyChoices =  getResources().getStringArray(R.array.respuestas);

	        
	        PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

	        notificationBuilder = new NotificationCompat.Builder(this);
	        notificationBuilder.setSmallIcon(android.R.drawable.ic_btn_speak_now);
	        notificationBuilder.setContentTitle(getString(R.string.voz_button_title));
	        notificationBuilder.setContentIntent(replyPendingIntent);
	        notificationBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.llamado_de_sordon));
	       
	       
	        RemoteInput remoteInput = new RemoteInput.Builder("extra_voice_reply")
	        .setLabel(replyLabel)
	        .setChoices(replyChoices)
	        .build();

	        Notification replyNotification = new WearableNotifications.Builder(notificationBuilder).
	        		addRemoteInputForContentIntent(remoteInput).build();

	        NotificationManagerCompat.from(this).notify(100, replyNotification);

			notificationBuilder = null;
			
			break;

		default:
			notificationBuilder =
	        new NotificationCompat.Builder(getApplicationContext())
	        .setContentTitle(getString(R.string.simple_button_title))
	        .setSmallIcon(R.drawable.ic_launcher)
	        .setContentText(getString(R.string.simple_button_message));

			break;
		}
		if (notificationBuilder != null){
			Notification notification =
		        new WearableNotifications.Builder(notificationBuilder)
		        .setHintHideIcon(withoutIcon)
		        .build();
				NotificationManagerCompat.from(getApplicationContext()).notify(100, notification);
				count++;
		}
	}
}

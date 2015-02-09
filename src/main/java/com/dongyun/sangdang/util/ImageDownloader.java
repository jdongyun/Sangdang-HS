package com.dongyun.sangdang.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

//웹에서 가져온 이미지 비트맵으로 리턴하는 클래스
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public ImageDownloader(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];
		Bitmap mIcon = null;
        Bitmap mResized = null;
		try {
			InputStream in = new java.net.URL(url).openStream();
			mIcon = BitmapFactory.decodeStream(in);
            int r_height = mIcon.getHeight();
            int r_width = mIcon.getWidth();
            while (r_height > 300) {
                mResized = Bitmap.createScaledBitmap(mIcon, (r_width * 300) / r_height, 300, true);
                r_height = mResized.getHeight();
                r_width = mResized.getWidth();
            }
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		return mResized;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}

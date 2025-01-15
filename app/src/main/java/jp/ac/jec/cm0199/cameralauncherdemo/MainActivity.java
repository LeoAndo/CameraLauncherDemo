package jp.ac.jec.cm0199.cameralauncherdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ボタンをクリックした時の処理
        findViewById(R.id.button).setOnClickListener(v -> {
            // カメラアプリを起動する - START
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                Log.e("MainActivity", "error: ", e);
            }
            // カメラアプリを起動する - END
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult: requestCode: " + requestCode + " resultCode: " + resultCode + " data: " + data);
        if (data == null) {
            return;
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { // カメラアプリから戻ってきたとき
            // 撮影した画像をImageViewに表示する - START
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(imageBitmap);
            }
            // 撮影した画像をImageViewに表示する - END
        }
    }
}
package frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import whack.beer.databinding.StartLayoutBinding;
public class StartActivity extends AppCompatActivity {
    private StartLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = StartLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        binding.button2.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, TestActivity.class);
            startActivity(intent);
        });

    }

    public void aboutPage(View view){
        Intent intent = new Intent(StartActivity.this, AboutActivity.class);
        startActivity(intent);
    }


}

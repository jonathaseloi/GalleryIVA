package jonathaseloi.br.galeriaiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jonathaseloi.br.galleryiva.StringUtils;

public class MainActivity extends AppCompatActivity {

    private EditText etLowCase;
    private Button btnConvert;
    private TextView tvUpperCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initIds();

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvUpperCase.setText(StringUtils.UpperCase(etLowCase.getText().toString()));
                etLowCase.setText("");
            }
        });
    }

    private void initIds() {
        etLowCase = findViewById(R.id.etLowCase);
        btnConvert = findViewById(R.id.btnConvert);
        tvUpperCase = findViewById(R.id.tvUpperCase);
    }
}

package id.myreadwritefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBaru;
    Button btnBuka;
    Button btnSimpan;
    EditText editContent;
    EditText editJudul;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBaru = (Button) findViewById(R.id.btn_baru);
        btnBuka = (Button) findViewById(R.id.btn_buka);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        editContent = (EditText) findViewById(R.id.file);
        editJudul = (EditText) findViewById(R.id.judul);

        btnBaru.setOnClickListener(this);
        btnBuka.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_baru:
                newFile();
                break;
            case R.id.btn_buka:
                openFile();
                break;
            case R.id.btn_simpan:
                saveFile();
                break;
        }
    }


    public void newFile() {

        editJudul.setText("");
        editContent.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editJudul.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            private DialogInterface dialog;
            private int item;

            public void onClick(DialogInterface dialog, int item) {
                this.dialog = dialog;
                this.item = item;
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (editJudul.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editJudul.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editJudul.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}

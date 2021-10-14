package iss.workshops.telemedicinemobile.activities.ConsultationHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import iss.workshops.telemedicinemobile.API;
import iss.workshops.telemedicinemobile.BuildConfig;
import iss.workshops.telemedicinemobile.MainActivity;
import iss.workshops.telemedicinemobile.R;
import iss.workshops.telemedicinemobile.RetrofitClient;
import iss.workshops.telemedicinemobile.domain.Patient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class DownloadMcPdfActivity extends AppCompatActivity
{


    Button generatePDFbtn;
    Context context;


    int pageHeight = 1120;
    int pagewidth = 792;


    Bitmap bmp, scaledbmp;
    String url;
    String filename,dateTo,dateFrom,patientId,patientName,doctorId,doctorName;
    int id,duration;


    final int PERMISSION_REQUEST_CODE = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_history_item);

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("mcId"));
        dateFrom = intent.getStringExtra("mcDateFrom");
        dateTo = intent.getStringExtra("mcDateTo");
        duration = intent.getIntExtra("mcDuration",0);
        patientId=intent.getStringExtra("patientId");
        patientName=intent.getStringExtra("patientName");
        doctorId=intent.getStringExtra("doctorId");
        doctorName=intent.getStringExtra("doctorName");
        //generatePDFbtn = findViewById(R.id.btn_downloadMc);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo1);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);


        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        generatePDF();

    }


    private void generatePDF()
    {
        PdfDocument pdfDocument = new PdfDocument();

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(65);
        title.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));


        canvas.drawText("Medical Certificate", 209, 100, title);
        //canvas.drawText("Geeks for Geeks", 209, 80, title);

        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(35);

        title.setTextAlign(Paint.Align.CENTER);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        canvas.drawText("Mc Id  :  " + id , 396, 400, title);
        canvas.drawText("Patient NRIC  :  "  + patientId , 396, 460, title);
        canvas.drawText("Patient Name  :  " + patientName, 396, 520, title);
        canvas.drawText("Doctor NRIC  :  "  + doctorId , 396, 580, title);
        canvas.drawText("Doctor Name  :  " + doctorName, 396, 640, title);
        canvas.drawText("Issue Date  :  " + date, 396, 700, title);



        canvas.drawText("This certifies that " + patientName+" is unfit for work", 396, 780, title);
        canvas.drawText(" from "+ dateFrom +" to "+ dateTo+" .", 396, 850, title);

        pdfDocument.finishPage(myPage);

        filename= UUID.randomUUID().toString();
        filename=filename +".pdf";

        File file = new File(Environment.getExternalStorageDirectory(), filename);

        try {

            pdfDocument.writeTo(new FileOutputStream(file));

            Toast.makeText(DownloadMcPdfActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        pdfDocument.close();
        viewPdfFile(filename);
    }

    public void viewPdfFile(String filename) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),filename);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri= FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID,file);
        intent.setDataAndType(uri, "application/pdf");
        PackageManager pm=getPackageManager();
        //PackageManager pm = getActivity().getPackageManager();
        if (intent.resolveActivity(pm) != null) {
            startActivity(intent);
        }


        /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"my4.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");

        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);*/
    }
    private boolean checkPermission() {

        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission()
    {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0)
            {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}


package com.example.ivan.minibar;

/**
 * Created by ivan on 12/12/17.
 */

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfCreator {
    public static void pdfCreate(String imgPath, String pdfName) throws DocumentException, MalformedURLException, IOException {
        String dirPath = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS)+"/Minibar/");
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dirPath+pdfName+".pdf"));
        document.open();
        Image img = Image.getInstance(imgPath);
        //document.add(new Paragraph("Sample 1: This is simple image demo."));
        img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        document.add(img);
        document.close();
        Log.i("","Done");
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static String store(Bitmap bm, String fileName){
        final String dirPath = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS)+"/Minibar/");
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            return dirPath+fileName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int GenerarPdf(View vista, Activity activity, String pdfName) {

        if( ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            String imgPath = PdfCreator.store(PdfCreator.getScreenShot(vista), "captura_ticket.png");
            try {
                PdfCreator.pdfCreate( imgPath, pdfName );

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return 0;
        }
    }
}

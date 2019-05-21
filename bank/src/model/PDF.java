package model;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;


public class PDF {

    public String kwota_wiersz;
    public String tytul_wiersz;
    public String dane_nadawcy_wiersz;
    public String adres_nadawcy_wiersz;
    public String data_wiersz;
    public String nr_konta_nadawcy_wiersz;
    public String path;

    public PDF(String kwota,String tytul,String dane,String adres,String data,String nrkonta, String path){
        this.kwota_wiersz=kwota;
        this.tytul_wiersz=tytul;
        this.dane_nadawcy_wiersz=dane;
        this.adres_nadawcy_wiersz=adres;
        this.data_wiersz=data;
        this.nr_konta_nadawcy_wiersz=nrkonta;
        this.path=path;
    }

    public void stworzPDF() {
        String FILE_NAME = path;
        //"D:\\potwierdzenie.pdf"
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();

            Paragraph p = new Paragraph();
            p.add("Potwierdzenie przelewu");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Paragraph p2 = new Paragraph();
            p2.add("Kwota: "+kwota_wiersz+" zl");//no alignment
            document.add(p2);
            Paragraph p3 = new Paragraph();
            p3.add("Tytuł przelewu: "+tytul_wiersz);
            document.add(p3);
            Paragraph p4 = new Paragraph();
            p4.add("Dane nadawcy: "+dane_nadawcy_wiersz);
            document.add(p4);
            Paragraph p5 = new Paragraph();
            p5.add("Adres nadawcy: "+adres_nadawcy_wiersz);
            document.add(p5);
            Paragraph p6 = new Paragraph();
            p6.add("Data: "+data_wiersz);
            document.add(p6);
            Paragraph p7 = new Paragraph();
            p7.add("Numer konta nadawcy: "+nr_konta_nadawcy_wiersz);
            document.add(p7);

            // document.add(Image.getInstance("D:\\chillyfacts.png"));

            document.close();

            System.out.println("Done");
            System.out.println(adres_nadawcy_wiersz);
            System.out.println(kwota_wiersz);
            System.out.println("Done");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
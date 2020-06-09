/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author phong
 */
public class file {
    
    public static String docFile(String fileName) {
        String fullName = "D:\\13.KH 2n4 (HK8)\\02.KyThuatGiauTin\\02.Mid-Test\\demo\\June05\\" + fileName;
        String a = "";
        try {
            FileInputStream fis = new FileInputStream(fullName);
            XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paraList = document.getParagraphs();
            for (XWPFParagraph para : paraList) {

                a += para.getText();
            }
            document.close();
        } catch (Exception e) {
            System.err.println("Read file error");
        }
        return a;
    }

    public static boolean ghiFile(String fileN, String t) {
        System.out.println("fileN=" + fileN);
        String fullN = "D:\\13.KH 2n4 (HK8)\\02.KyThuatGiauTin\\02.Mid-Test\\demo\\June05\\" + fileN;
        List<String> a = new ArrayList<>();
        //a = findSpaceWhite(t);

        try {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph para1 = document.createParagraph();
            XWPFRun run = para1.createRun();

            run.setText(t);
            FileOutputStream out;
            out = new FileOutputStream(new File(fullN));
            document.write(out);
            out.close();
            document.close();

        } catch (Exception e) {
            System.err.println("Error write file");
            return false;
        }
        return true;
    }

    public static String XuLyVB(String a) {
        a = a.trim();
        a = a.replaceAll("\\s+", " ");

        return a;
    }
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        String a="";
        System.out.println("Input file name: ");
        a=sc.nextLine();
        String b= docFile(a);
        System.out.println("Result:\n "+b);
         System.out.println("Input file name for write: ");
        String c=sc.nextLine();
        b=XuLyVB(b);
        if(ghiFile(c, b))
            System.out.println("Write file complete!");
    }
}

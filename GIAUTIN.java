/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import steganography.AES_Algorithm;
import static steganography.file.docFile;
import static steganography.file.ghiFile;

/**
 *
 * @author phong
 */
public class GIAUTIN {

    private String key;
    private String message;
    private String text;
    private String fileName;
    private String cover = "";
    private String fileNameForWrite;

    public GIAUTIN(String fileName, String fileNameForWrite, String message, String key) {
        this.message = message;

        this.key = key;
        this.fileName = fileName;
        this.fileNameForWrite = fileNameForWrite;

    }

    public GIAUTIN(String fileName, String key) {

        this.key = key;
        this.fileName = fileName;

    }

    public String XuLyVB(String a) {
        a = a.trim();
        a = a.replaceAll("\\s+", " ");

        return a;
    }

    public String findSpaceIsCorrect(String a) {//đầu vào là một ký tự và vị trí của ký tự

        String result = "";

        if (a.compareTo("  ") == 0) {
            result += "1";

        } else if (a.compareTo(" ") == 0) {
            result += "0";

        }

        return result;
    }

    public boolean find2Space(String a, String b) {

        return a.compareTo(" ") == 0 && b.compareTo(" ") == 0;
    }

    public List<String> findSpaceWhite(String a) {//tìm vị trí có 2 dấu cách liền kề để nối lại
        List<String> arrayList = new ArrayList<>();
        for (char b : a.toCharArray()) {
            arrayList.add(String.valueOf(b));
        }
        for (int i = 0; i < arrayList.size() - 1; i++) {
            if (find2Space(arrayList.get(i), arrayList.get(i + 1)) == true) {
                arrayList.set(i, "  ");
                arrayList.remove(i + 1);
            }
        }

        return arrayList;
    }

    public String returnBinaryChar(char a) {//trả về 1 dấu cách nếu là bit 1, KHÔNG dấu cách nếu bit 0
        String b;
        String c = String.valueOf(a);
        if (c.compareTo("1") == 0) {
            b = " ";

        } else {
            b = "";
        }
        return b;
    }

    public String textToBinary(int a) {//Input form char array one by one
        String result = Integer.toBinaryString(a);
        while (result.length() < 8) {
            result = "0" + result;
        }
        return result;
    }

    public String binaryToText(String a) {//input is string but only one character
        String result = "", b, c;
        int d;
        while (a.length() > 0) {
            b = a.substring(0, 8);
            if (b.equals("00000000") == true || b.length() < 8) {
                break;
            }
            d = Integer.parseInt(b, 2);
            c = new Character((char) d).toString();
            a = a.substring(8);
            result += c;
        }
        return result;
    }

    public static int[] convertKey(String a1, int d) {//a1 là key, d là dộ dài mảng nhị phân mess
        int b[] = new int[d];
        int total = 0;
        for (int i = 0; i < d; i++) {
            for (char c : a1.toCharArray()) {
                int e = c;
                while (e > 9) {
                    e %= 10;
                }
                if (e == 0) {
                    e++;
                }
                total += e;
                b[i] = total;
                //System.out.println("b["+i+"]= "+total+"e= "+e);
            }
            //System.out.println("b= "+b1);
        }
        //return b / (b / 2);//chức năng tạm
        return b;
    }

    public void Encode() {
        AES_Algorithm en = new AES_Algorithm();
        text = XuLyVB(docFile(fileName));//đọc file rồi gán vào biến text

        message = XuLyVB(message);
        message = en.encrypt(message, key);//mã hóa key bằng AES
        System.out.println("messageEncrypt=" + message);

        System.out.println("Van ban text:" + text);
        //mảng text
        char arrayText[] = text.toCharArray();
        System.out.println("arayTestLenght=" + arrayText.length);
        char arrayMess[] = message.toCharArray();

        String tempMess = "";

        for (int k = 0; k < arrayMess.length; k++) {//dịch sang nhị phân
            tempMess += textToBinary(arrayMess[k]);
        }
        System.out.println("tempMess=" + tempMess);
        //mảng key
        int[] arrayKey = convertKey(key, tempMess.length() + 16);//thêm  byte đánh dấu vị trí
        String tempMessLenght = Integer.toBinaryString(tempMess.length());// lấy độ dài chuỗi mess->convert key
        System.out.println("tempMessLenght=" + tempMessLenght);
        while (tempMessLenght.length() < 16) {
            tempMessLenght = "0" + tempMessLenght;
        }
        tempMess = tempMessLenght + tempMess;//cộng 2 byte vị trí mảng vào đầu chuỗi message
        System.out.println("tempMess Sau cong =" + tempMess);
        //mảng message
        arrayMess = tempMess.toCharArray();//bỏ bit nhị phân vào mảng  char
        System.out.println("mess.lenght=" + arrayMess.length);

        //check điều kiện bỏ message + convertKey vào text
        int countSpaceForCheck = 0;
        for (int i = 0; i < arrayText.length; i++) {
            if (arrayText[i] == ' ') {
                countSpaceForCheck++;
            }
        }

        int dem = 0, index = 0;
        for (int j = 0; j < arrayText.length; j++) {
            if (String.valueOf(arrayText[j]).compareTo(" ") == 0) {// && index < (arrayMess.length) điều kiện check độ dài tin nhắn để dừng
                dem++;
                if (dem == arrayKey[index]) {
                    cover += returnBinaryChar(arrayMess[index]) + arrayText[j];
                    dem = 0;

                    index++;

                } else {

                    cover += String.valueOf(arrayText[j]);//nạp phần tử text nếu khác key

                }
            } else {//nạp phần tử text nếu khác khoảng trắng
                cover += String.valueOf(arrayText[j]);

            }
        }
        System.out.println("cover=" + cover);
        if (ghiFile(fileNameForWrite, cover) == true) {
            System.out.println("Write file completed!!");
        }

    }

    public void Decode() {
        AES_Algorithm n = new AES_Algorithm();
        cover = docFile(fileName);
        System.out.println("coverInDecode=" + cover);
        System.out.println("keyInDecode=" + key);

        List<String> arrayList = new ArrayList<>();
        arrayList = findSpaceWhite(cover);
        int dem = 0, countForLastIndex = 0;
        String messDecode = "", arrayKey = "";
        int countMessL = 0;

        int[] arrayConvertKeyForLastIndex = convertKey(key, 16);//mảng chứa 2 byte vị trí cuối

        for (int i = 0; i <= arrayConvertKeyForLastIndex[15]; i++) {//phần tử cuối có giá trị là vị trí cuối của 2 byte
            if (i == arrayConvertKeyForLastIndex[countForLastIndex]) {//mỗi phần tử lưu giá trị của 2 byte
                arrayKey += findSpaceIsCorrect(arrayList.get(i));
                countForLastIndex++;
            }
        }
        int resultForLastIndex = Integer.parseInt(arrayKey, 2);
        int[] arrayForConvertKey = convertKey(key, resultForLastIndex);
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == resultForLastIndex) {
                break;
            }
            if (i == arrayForConvertKey[countForLastIndex]) {
                messDecode += findSpaceIsCorrect(arrayList.get(i));
                countForLastIndex++;
            }

        }

        System.out.println("Mes Decode: " + messDecode);
        System.out.println("Mes.lenght=" + messDecode.length());
        // messDecode=messDecode.substring(16);
        System.out.println("Mes.lenght Sau Cat=" + messDecode.length());
        // ghiFile(fileName, text);
        System.out.println("MessageEncrypt=" + binaryToText(messDecode));
        System.out.println("Message=" + n.decrypt(binaryToText(messDecode), key));
        // return messDecode;
        //System.out.println("messDecode="+messDecode);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------SteganographyInDOC-----------");

        int i;
        do {
            System.out.println("Nhap lua chon(0): ");
            i = sc.nextInt();
            sc.nextLine();
            switch (i) {
                case 0: {
                    System.out.println("0. Menu");
                    System.out.println("1.Giau tin");
                    System.out.println("2.Giai ma");
                    System.out.println("3.Phat hien tin");
                    System.out.println("4.Huy tin");
                    System.out.println("5.Thoat");
                    break;
                }
                case 1: {
                    System.out.println("-------GIAU TIN -------");
                    System.out.println("Nhap ten file nguon: ");
                    String inputFileName = sc.nextLine();
                    System.out.println("Nhap Message: ");
                    String inputMessage = sc.nextLine();
                    System.out.println("Nhap key: ");
                    String key = sc.nextLine();
                    System.out.println("Nhap ten file ghi: ");
                    String outputFileName = sc.nextLine();
                    //sc.nextLine();
                    GIAUTIN gt;
                    gt = new GIAUTIN(inputFileName, outputFileName, inputMessage, key);
                    gt.Encode();

                    break;
                }
                case 2: {
                    System.out.println("--------GIAI MA--------");
                    System.out.println("Nhap ten file can Giai Ma: ");
                    String inputFN = sc.nextLine();
                    System.out.println("Nhap key: ");
                    String k = sc.nextLine();
                    //sc.nextLine();
                    GIAUTIN gm = new GIAUTIN(inputFN, k);
                    gm.Decode();
                    break;
                }
                case 5:
                    break;
                default: {
                    System.out.println("Nhap Sai!");
                    break;
                }
            }

        } while (i != 5);
    }
}

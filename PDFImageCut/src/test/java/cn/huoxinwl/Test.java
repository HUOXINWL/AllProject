package cn.huoxinwl;

public class Test {
    public static void main(String[] args) {
        String fileName = "C:\\Users\\cbx12\\Desktop\\pdf\\hh.jpg";
        int midPos = fileName.lastIndexOf(".");
        System.out.println(fileName.substring(midPos));
    }
}

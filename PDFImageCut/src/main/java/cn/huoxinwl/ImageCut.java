package cn.huoxinwl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageCut {
    /**
     * 矩形裁剪，设定起始位置，裁剪宽度，裁剪长度
     * 裁剪范围需小于等于图像范围
     * @param image
     * @param xCoordinate
     * @param yCoordinate
     * @param xLength
     * @param yLength
     * @return
     */
    public BufferedImage imageCutByRectangle(BufferedImage image, int xCoordinate, int yCoordinate, int xLength,
                                             int yLength) {
        //判断x、y方向是否超过图像最大范围
        if((xCoordinate + xLength) >= image.getWidth()) {
            xLength = image.getWidth() - xCoordinate;
        }
        if ((yCoordinate + yLength) >= image.getHeight()) {
            yLength = image.getHeight() - yCoordinate;
        }
        BufferedImage resultImage = new BufferedImage(xLength, yLength, image.getType());
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
                resultImage.setRGB(x, y, rgb);
            }
        }
        return resultImage;
    }

    /**
     * 圆形裁剪，定义圆心坐标，半径
     * 裁剪半径可以输入任意大于零的正整数
     * @param image
     * @param xCoordinate
     * @param yCoordinate
     * @param radius
     * @return
     */
    public BufferedImage imageCutByCircle(BufferedImage image, int xCoordinate, int yCoordinate, int radius) {
        //判断圆心左右半径是否超限
        if ((xCoordinate + radius) > image.getWidth() || radius > xCoordinate) {
            int a = image.getWidth() - 1 - xCoordinate;
            if (a > xCoordinate) {
                radius = xCoordinate;
            }else {
                radius = a;
            }
        }
        //判断圆心上下半径是否超限
        if ((yCoordinate + radius) > image.getHeight() || radius >yCoordinate) {
            int a = image.getHeight() - 1 - yCoordinate;
            if (a > yCoordinate) {
                radius = yCoordinate;
            }else {
                radius = a;
            }
        }
        int length = 2 * radius + 1;
        BufferedImage resultImage = new BufferedImage(length, length, image.getType());
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int x = i - radius;
                int y = j - radius;
                int distance = (int) Math.sqrt(x * x + y * y);
                if (distance <= radius) {
                    int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
                    resultImage.setRGB(i, j, rgb);
                }
            }
        }
        return resultImage;
    }

    public static void cutManage(String path,String outPath,int x,int y1,int y2,int xLen,int yLen) throws IOException {
        //读取文件夹下的所有图片
        ArrayList<String> fileList = getFiles(path);

        //生成文件夹名和文件夹
        if (outPath.equals("")){
            outPath = outPath+"\\out\\";
        }
        File file=new File(outPath);
        if(!file.exists()){		//如果 module文件夹不存在
            file.mkdir();		//创建文件夹
        }

        //对图片进行循环裁剪
        for (int i= 0;i<fileList.size();i++) {
            String fileName = fileList.get(i);
            File input = new File(fileName);
            fileName = fileName.substring(fileName.lastIndexOf("\\")+1);

            int midPos = fileName.lastIndexOf(".");
            String lastName,firstName;
            BufferedImage image = ImageIO.read(input);
            BufferedImage result;
            File output;

            firstName = fileName.substring(0,midPos);
            lastName = fileName.substring(midPos+1);
            output = new File(outPath+firstName+"-1."+lastName);
            result= new ImageCut().imageCutByRectangle(image, x, y1, xLen,yLen);
            ImageIO.write(result, lastName, output);
            System.out.println(firstName+"-1."+lastName);
            output = new File(outPath+firstName+"-2."+lastName);
            result= new ImageCut().imageCutByRectangle(image, x, y2, xLen,yLen);
            ImageIO.write(result, lastName, output);
            System.out.println(firstName+"-2."+lastName);
        }

    }

    //获取文件夹下所有文件
    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {

                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {
                //  System.out.println("文件夹：" + tempList[i]);
            }
        }
        return files;
    }

    public static void main(String[] args) throws Exception {
        //String path = "C:\\Users\\cbx12\\Desktop\\1\\10";
        String outPath = "C:\\Users\\cbx12\\Desktop\\1\\out\\";

        //2046*1158
        //cutManage(path,241,504,1902,2046,1158);

        //1123
        //cutManage(path,75,156,604,644,365);

        //2512*3555
        //cutManage(path,242,495,1911,2033,1149);
        //cutManage(path,219,316,1884,2082,1358);//常用

        //1322*1871
        //cutManage(path,127,236,1029,1072,602);
        /*for(int i = 1;i<=16;i++){
            String path = "C:\\Users\\cbx12\\Desktop\\1\\"+Integer.toString(i);
            cutManage(path,outPath,127,265,998,1072,602);//常用
        }*/
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.1_1",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.1_2",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.1_3",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.1_4",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.1_5",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.2_1",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.2_2",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.2_3",outPath,127,265,1002,1072,602);//常用
        cutManage("I:\\百度网盘\\04.操作系统\\05.第五章输入输出管理\\5.2_4",outPath,127,265,1002,1072,602);//常用
        //1983*2807
        //cutManage(path,192,400,1500,1605,908);
    }
}
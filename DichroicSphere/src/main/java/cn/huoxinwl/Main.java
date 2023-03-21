package cn.huoxinwl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.MarshalledObject;
import java.util.*;

public class Main {
    public static int periods=0;
    public static void main(String[] args) {
        //读入文件
        //http://www.17500.cn/getData/ssq.TXT
        //https://www.55123.cn/tool/ssqdb.aspx
        Map<int[],String> map = readByLine("C:\\Users\\HUOXI\\Desktop\\ssq.TXT");


        //定义一些号码，并读入
        List<int[]> list = readNumbers();


        //开始检索中将号码
        LinkedHashMap<int[], Map<String,Integer>> resultMap = RetrieveNnumber(map,list);


        //输出中奖结果
        OutputWinningResult(resultMap);
    }

    public static void OutputWinningResult(LinkedHashMap<int[], Map<String,Integer>> resultMap){
        for(Map.Entry<int[], Map<String,Integer>> entry : resultMap.entrySet()){
            int[] sort = new int[]{0,0,0,0,0,0};
            int sum = 0;
            for (Map.Entry<String,Integer> m : entry.getValue().entrySet()){
                //System.out.println(m.getKey());
                sum += m.getValue();
                switch (m.getValue()){
                    case 5:
                        sort[5] += 1;
                        break;
                    case 10:
                        sort[4] += 1;
                        break;
                    case 200:
                        sort[3] += 1;
                        break;
                    case 3000:
                        sort[2] += 1;
                        break;
                    case 2500000:
                        sort[1] += 1;
                        break;
                    case 5000000:
                        sort[0] += 1;
                        break;
                }
            }

            System.out.print("目前的中将号码："+Arrays.toString(entry.getKey()) + "    金额："+sum+"  赚了："+(sum-periods*2));
            for (int i=0;i<6;i++){
                if (sort[i]!=0){
                    System.out.print("  "+(i+1)+"等奖有"+sort[i]+"次");
                }
            }

            System.out.println("\n\n\n");
        }
    }

    public static LinkedHashMap<int[], Map<String,Integer>> RetrieveNnumber(Map<int[],String> map,List<int[]> list){
        LinkedHashMap<int[], Map<String,Integer>> resultMap = new LinkedHashMap<>();
        for (int[] ints : map.keySet()) {
            for (int[] nums : list) {
                resultMap.putIfAbsent(nums,new HashMap<String,Integer>());

                boolean blurFlag = false;
                int count = 0;
                HashSet<Integer> integers = new HashSet<>();
                for (int i = 0;i<6;i++){
                    integers.add(ints[i]);
                }
                for (int i = 0;i<6;i++){
                    if (integers.contains(nums[i])) count++;
                }
                if (ints[6] == nums[6]) blurFlag = true;

                //判断是否中奖
                if (count == 6){
                    if (blurFlag) resultMap.get(nums).put("一等奖 "+map.get(ints),5000000);
                    else resultMap.get(nums).put("二等奖 "+map.get(ints),2500000);
                }else if (count == 5){
                    if (blurFlag) resultMap.get(nums).put("三等奖 "+map.get(ints),3000);
                    else resultMap.get(nums).put("四等奖 "+map.get(ints),200);
                }else if (count == 4){
                    if (blurFlag)  resultMap.get(nums).put("四等奖 "+map.get(ints),200);
                    else  resultMap.get(nums).put("五等奖 "+map.get(ints),10);
                }else if (count == 3) {
                    if (blurFlag) resultMap.get(nums).put("五等奖 "+map.get(ints),10);
                }
                else if (blurFlag) resultMap.get(nums).put("六等奖 "+map.get(ints),5);

                /**
                 * 六等奖  5元       1篮球
                 * 五等奖  10元      3+1 或 4+0
                 * 四等奖  200元     4+1 或 5+0
                 * 三等奖  3000元    5+1
                 * 二等奖  25%       6+0
                 * 一等奖  ****      6+1
                 */
            }
        }
        return resultMap;
    }
    public static Map<int[],String> readByLine(String path){
        File file=new File(path);
        BufferedReader reader=null;
        String temp=null;

        Map map = new LinkedHashMap<int[],String>();

        int line=1;
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                //System.out.println("line"+line+":"+temp);
                int[] ints = new int[7];
                for (int i=0;i<7;i++){
                    ints[i] = Integer.parseInt(temp.substring(19+i*3,21+i*3));
                }
                map.put(ints,temp);
                line++;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        periods=line-1;
        return map;
    }

    public static List<int[]> readNumbers(){
        File file=new File(new Main().getPath());
        BufferedReader reader=null;
        String temp=null;

        LinkedList<int[]> list = new LinkedList<>();
        int line=1;
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                //System.out.println("line"+line+":"+temp);
                int[] ints = new int[7];
                for (int i=0;i<7;i++){
                    ints[i] = Integer.parseInt(temp.substring(i*3,2+i*3));
                }
                list.add(ints);
                line++;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public String getPath(){
        return this.getClass().getResource("/numbers.txt").getPath();
    }
}
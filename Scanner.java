
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Scanner{

public Scanner(){

}

  public int[] scan(String[] args) throws IOException{
    String filename;
    if(args == null){
      return null;
    }else{
      filename = args[0];


    FileReader fileReader = new FileReader(filename);

    BufferedReader bufferedReader = new BufferedReader(fileReader);
    List<Integer> lines = new ArrayList<Integer>();
    String line = null;

    while ((line = bufferedReader.readLine()) != null)
    {
        lines.add(Integer.parseInt(line));
        //System.out.println(Integer.parseInt(line));
    }

    bufferedReader.close();

    int[] array = toIntArray(lines);

    return array;
      }
  }
  int[] toIntArray(List<Integer> list){
    int[] ret = new int[list.size()];
    for(int i = 0;i < ret.length;i++)
      ret[i] = list.get(i);
    return ret;
  }



}

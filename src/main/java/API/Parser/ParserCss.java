package API.Parser;

import com.vaadin.server.Page;

import java.io.*;

public class ParserCss {


    private Page page;

    public ParserCss(Page page){
        this.page = page;
    }

    public void convertPx(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        File newFile = new File(file.getAbsolutePath()+"lol");
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile)));

        while (bufferedReader.ready()){
            String tmp = bufferedReader.readLine();
            int oldPixel;
            if(tmp.contains("px")) {
                oldPixel = Integer.parseInt(tmp.substring(tmp.indexOf(":"), tmp.indexOf("px")));
            }
            //int newPixel = get


        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private double getHeightPercent(double px) {
//
//
//    }


}

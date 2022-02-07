package my_project.control;

import java.io.*;

public class LeaderboardController {

    private String[] names;
    private int[] scores;
    private String[] difficult;

    public LeaderboardController(){
        String fileString = getFileContent();
        String[] fileStringArray = fileString.split("\n");
        names = new String[fileStringArray.length];
        scores = new int[fileStringArray.length];
        difficult = new String[fileStringArray.length];
        for(int i=0;i< fileStringArray.length;i++){
            String[] strings = fileStringArray[i].split(":");
            names[i]=strings[0];
            scores[i]=Integer.parseInt(strings[1]);
            difficult[i]=strings[2];
        }
    }

    private String getFileContent() {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/main/java/my_project/control/LeaderBoard.txt");
        } catch (
                FileNotFoundException ignore) {
        }

        StringBuilder sb = new StringBuilder();

        try {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return sb.toString();
    }
}

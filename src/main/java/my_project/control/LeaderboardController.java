package my_project.control;

import java.io.*;

public class LeaderboardController {

    private String[] names;
    private int[] scores;
    private String[] difficults;

    public LeaderboardController(){
        updateArrays();
    }

    private void updateArrays(){
        String fileString = getFileContent();
        String[] fileStringArray = fileString.split("\n");
        names = new String[fileStringArray.length];
        scores = new int[fileStringArray.length];
        difficults = new String[fileStringArray.length];
        for(int i=0;i< fileStringArray.length-1;i++){
            String[] strings = fileStringArray[i].split(":");
            names[i]=strings[0];
            scores[i]=Integer.parseInt(strings[1]);
            difficults[i]=strings[2];
        }
    }

    public void addToLeaderBoard(String name,int score,String difficult){
        String[] oldNames=new String[names.length];
        System.arraycopy(names,0,oldNames,0,names.length);
        String[] oldDifficults=new String[names.length];
        System.arraycopy(difficults,0,oldDifficults,0,names.length);
        int[] oldScores=new int[names.length];
        System.arraycopy(scores,0,oldScores,0,names.length);
        names=new String[oldNames.length];
        difficults=new String[oldNames.length];
        scores=new int[oldNames.length];
        System.arraycopy(oldNames,0,names,0,oldNames.length);
        System.arraycopy(oldDifficults,0,difficults,0,oldNames.length);
        System.arraycopy(oldScores,0,scores,0,oldNames.length);
        names[names.length-1]=name;
        difficults[names.length-1]=difficult;
        scores[names.length-1]=score;
        writeLeaderBoard();
        updateArrays();
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

    public void writeLeaderBoard() {
        StringBuilder newFileDataString = new StringBuilder();

        for(int i=0;i< names.length;i++) newFileDataString.append(String.format("%s:%s:%s%n", names[i], scores[i], difficults[i]));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/my_project/control/LeaderBoard.txt"))) {
            writer.write(newFileDataString.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String[] getNames(){ return names; }
    public String[] getDifficults(){ return difficults; }
    public int[] getScores(){ return scores; }
}

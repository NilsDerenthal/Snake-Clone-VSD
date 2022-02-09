package my_project.control;

import KAGO_framework.model.abitur.datenstrukturen.List;
import my_project.model.LeaderboardEntry;

import java.io.*;
import java.util.Arrays;

public class LeaderboardController {

    private List<LeaderboardEntry> entries;

    public LeaderboardController(){
        entries = getFileContent();
    }


    public void addToLeaderBoard(String name,int score,String difficulty){
        entries.append(new LeaderboardEntry(score, name, difficulty));
        writeLeaderBoard();
    }

    private List<LeaderboardEntry> getFileContent() {
        List<LeaderboardEntry> lb = new List<>();
        FileInputStream inputStream;

        try {
            inputStream = new FileInputStream("src/main/java/my_project/control/LeaderBoard.txt");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(":");
                    lb.append(new LeaderboardEntry(Integer.parseInt(values[0]), values[1], values[2]));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return lb;
    }

    public void writeLeaderBoard() {
        StringBuilder newFileDataString = new StringBuilder();

        entries.toFirst();
        while (entries.hasAccess()) {
            var entry = entries.getContent();
            newFileDataString.append(String.format("%s:%d:%s%n", entry.name(), entry.score(), entry.difficulty()));
            entries.next();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/my_project/control/LeaderBoard.txt"))) {
            writer.write(newFileDataString.toString());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public List<LeaderboardEntry> getEntries (){
        return entries;
    }

}

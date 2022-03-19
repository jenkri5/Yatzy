package dk.jenskristensen.yatzy;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore {

    //src/main/resources/dk/jenskristensen/yatzy/
    private final File highScore = new File("highScore.dat");
    private ArrayList<Score> scores = new ArrayList<>();

    public HighScore() {
        if (!highScore.exists()) {
            try {
                highScore.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadHighScore();
    }

    public void loadHighScore() {
        scores.clear();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(highScore);
            bufferedReader = new BufferedReader(fileReader);
            String data = bufferedReader.readLine();
            while(data != null){
                String[] s = data.split(":");
                Score score = new Score(s[0], Integer.parseInt(s[1]));
                scores.add(score);
                data = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(scores, Collections.reverseOrder());
        if (scores.size() > 5)
            scores = new ArrayList<>(scores.subList(0, 5));
        if (scores.size() < 5) {
            int temp = scores.size();
            for (int i = 0; i < 5 - temp; i++) {
                Score score = new Score("Nobody",0);
                scores.add(score);
            }
        }
        trimHighScore();
    }

    public void submitHighScore(String name, int score) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(highScore, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(name + ":" + score);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trimHighScore() {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        highScore.delete();
        try {
            fileWriter = new FileWriter(highScore, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (Score score : scores) {
                bufferedWriter.write(score.getName() + ":" + score.getScore());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

}

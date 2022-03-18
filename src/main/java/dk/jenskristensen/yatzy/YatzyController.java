package dk.jenskristensen.yatzy;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class YatzyController {

    private final Image diceImageOne = new Image(Objects.requireNonNull(YatzyController.class.getResourceAsStream("dice/1.png")));
    private final Image diceImageTwo = new Image(Objects.requireNonNull(YatzyController.class.getResourceAsStream("dice/2.png")));
    private final Image diceImageThree = new Image(Objects.requireNonNull(YatzyController.class.getResourceAsStream("dice/3.png")));
    private final Image diceImageFour = new Image(Objects.requireNonNull(YatzyController.class.getResourceAsStream("dice/4.png")));
    private final Image diceImageFive = new Image(Objects.requireNonNull(YatzyController.class.getResourceAsStream("dice/5.png")));
    private final Image diceImageSix = new Image(Objects.requireNonNull(YatzyController.class.getResourceAsStream("dice/6.png")));
    private final Image[] diceImages = {diceImageOne, diceImageTwo, diceImageThree, diceImageFour, diceImageFive, diceImageSix};

    private final ImageView dice0 = new ImageView(diceImageOne),
            dice1 = new ImageView(diceImageTwo),
            dice2 = new ImageView(diceImageThree),
            dice3 = new ImageView(diceImageFour),
            dice4 = new ImageView(diceImageFive);
    private final ImageView[] diceImageViews = {dice0, dice1, dice2, dice3, dice4};

    private final Text scoreOnes = new Text("0"), scoreTwos = new Text("0"), scoreThrees = new Text("0"),
            scoreFours = new Text("0"), scoreFives = new Text("0"), scoreSixes = new Text("0"), scoreSumUpper = new Text("0"),
            scoreBonus = new Text("50"), scorePair = new Text("0"), scoreTwoPairs = new Text("0"), scoreThreeKind = new Text("0"),
            scoreFourKind = new Text("0"), scoreLowStraight = new Text("0"), scoreHighStraight = new Text("0"),
            scoreFullHouse = new Text("0"), scoreChance = new Text("0"), scoreYatzy = new Text("0"), scoreTotalSum = new Text("0");
    private final Text[] scoreUpperText = {scoreOnes, scoreTwos, scoreThrees, scoreFours, scoreFives, scoreSixes};
    private final Text[] scoreLowerText = {scorePair, scoreTwoPairs, scoreThreeKind, scoreFourKind, scoreLowStraight, scoreHighStraight, scoreFullHouse, scoreChance, scoreYatzy};

    @FXML
    private HBox diceBox;
    @FXML
    private GridPane scorePane;
    @FXML
    private Text pointsLabel;
    @FXML
    private Button rollButton, submitButton,
            cheatOnes, cheatTwos, cheatThrees, cheatFours, cheatFives,
            cheatPairs, cheatLowStraight, cheatHighStraight, cheatFullHouse, cheatYatzy;
    @FXML
    private PasswordField passwordCheat;

    private final boolean[] isClicked = new boolean[scoreUpperText.length + scoreLowerText.length];
    private final boolean[] isSubmitted = new boolean[scoreUpperText.length + scoreLowerText.length];
    private final int[] scoreUpper = new int[scoreUpperText.length];
    private final int[] scoreLower = new int[scoreLowerText.length];

    private final Die die1 = new Die(), die2 = new Die(), die3 = new Die(), die4 = new Die(), die5 = new Die();
    private final Die[] dice = {die1, die2, die3, die4, die5};
    private int rollCount = 0;

    public void initialize() {
        for (int i = 0; i < dice.length; i++) {
            diceImageViews[i].setFitHeight(40.0);
            diceImageViews[i].setFitWidth(40.0);
            int finalI = i;
            diceImageViews[i].setOnMouseClicked(event -> lockDice(finalI));
        }
        diceBox.getChildren().addAll(diceImageViews);
        for (int i = 0; i < scoreUpperText.length; i++) {
            scoreUpperText[i].setOpacity(0.5);
            int finalI = i;
            scoreUpperText[i].setOnMouseClicked(event -> scoreClicked(finalI));
            scorePane.add(scoreUpperText[i], 1, i);
        }
        for (int i = 0; i < scoreLowerText.length; i++) {
            scoreLowerText[i].setOpacity(0.5);
            int finalI = i + 6;
            scoreLowerText[i].setOnMouseClicked(event -> scoreClicked(finalI));
            scorePane.add(scoreLowerText[i], 1, i + 8);
        }
        scoreBonus.setOpacity(0.2);
        scorePane.add(scoreSumUpper, 1, 6);
        scorePane.add(scoreBonus, 1, 7);
        scorePane.add(scoreTotalSum, 1, 17);
    }

    public void roll() {
        Arrays.stream(dice).filter(die -> !die.isLocked).forEach(Die::roll);
        rollCount++;
        if (rollCount >= 3)
            rollButton.setDisable(true);
        updateImage();
        updateText();
    }

    private void lockDice(int i) {
        if (rollCount > 0) {
            dice[i].setLocked();
            updateContrast(i);
        }
    }

    private void scoreClicked(int i) {
        if (validateClick(i)) {
            if (i <= 5)
                scoreUpperText[i].setOpacity(1.0);
            else
                scoreLowerText[i - 6].setOpacity(1.0);
        } else if (!isSubmitted[i]) {
            if (i <= 5)
                scoreUpperText[i].setOpacity(0.5);
            else
                scoreLowerText[i - 6].setOpacity(0.5);
        }
        updateText();
    }

    public void submit() {
        for (int i = 0; i < scoreUpperText.length + scoreLowerText.length; i++) {
            if (isClicked[i]) {
                int points = calculatePoints(i);
                if (i <= 5) {
                    scoreUpperText[i].setText("" + points);
                    scoreUpper[i] = points;
                    scoreUpperText[i].setCursor(Cursor.DEFAULT);
                } else if (validateSubmit(i)) {
                    scoreLowerText[i - 6].setText("" + points);
                    scoreLowerText[i].setCursor(Cursor.DEFAULT);
                    scoreLower[i - 6] = points;
                }
                isSubmitted[i] = true;
                reset();
            }
        }
    }

    public void reset() {
        rollCount = 0;
        for (int i = 0; i < dice.length; i++)
            if (dice[i].isLocked) {
                dice[i].setLocked();
                updateContrast(i);
            }
        Arrays.fill(isClicked, false);
        submitButton.setDisable(true);
        rollButton.setDisable(false);
        updateText();
    }

    public void restart() {
        Arrays.fill(isClicked, false);
        Arrays.fill(isSubmitted, false);
        Arrays.fill(scoreUpper, 0);
        Arrays.fill(scoreLower, 0);
        for (Text score : scoreUpperText) {
            score.setText("0");
            score.setOpacity(0.5);
            score.setCursor(Cursor.HAND);
        }
        for (Text score : scoreLowerText) {
            score.setText("0");
            score.setOpacity(0.5);
            score.setCursor(Cursor.HAND);
        }
        scoreSumUpper.setText("0");
        scoreBonus.setOpacity(0.2);
        scoreTotalSum.setText("0");
        pointsLabel.setText("0");
        submitButton.setDisable(true);
        rollButton.setDisable(false);
        rollCount = 0;
        for (int i = 0; i < dice.length; i++) {
            dice[i].faceValue = i + 1;
            dice[i].isLocked = false;
            updateContrast(i);
        }
        updateImage();
        cheatOnes.setDisable(true);
        cheatTwos.setDisable(true);
        cheatThrees.setDisable(true);
        cheatFours.setDisable(true);
        cheatFives.setDisable(true);
        cheatPairs.setDisable(true);
        cheatLowStraight.setDisable(true);
        cheatHighStraight.setDisable(true);
        cheatFullHouse.setDisable(true);
        cheatYatzy.setDisable(true);
    }

    private boolean validateClick(int n) {
        boolean isValid = false;
        if (!isSubmitted[n] && rollCount != 0) {
            for (int i = 0; i < scoreUpperText.length; i++) {
                if (isClicked[i] && i != n) {
                    if (!isSubmitted[i])
                        scoreUpperText[i].setOpacity(0.5);
                    isClicked[i] = false;
                }
            }
            for (int i = 0; i < scoreLowerText.length; i++) {
                if (isClicked[i + 6] && i + 6 != n) {
                    if (!isSubmitted[i + 6])
                        scoreLowerText[i].setOpacity(0.5);
                    isClicked[i + 6] = false;
                }
            }
            if (!isClicked[n]) {
                isValid = true;
                submitButton.setDisable(false);
                isClicked[n] = true;
            } else {
                submitButton.setDisable(true);
                isClicked[n] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            for (int i = 0; i < scoreUpperText.length; i++) {
                if (!isSubmitted[i])
                    scoreUpperText[i].setOpacity(0.5);
            }
            for (int i = 0; i < scoreLowerText.length; i++) {
                if (!isSubmitted[i + 6])
                    scoreLowerText[i].setOpacity(0.5);
            }
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
        return isValid;
    }

    private boolean validateSubmit(int n) {
        boolean isValid = true;
        switch (n) {
            case 6:
                boolean hasPair = false;
                for (int i = 0; i < dice.length - 1; i++)
                    for (int j = i + 1; j < dice.length; j++)
                        if (dice[i].faceValue == dice[j].faceValue) {
                            hasPair = true;
                            break;
                        }
                isValid = hasPair;
                break;
            case 7:
                boolean hasTwoPairs = false;
                int numberOfPairs = 0;
                int[] twoPairsBucket = new int[6];
                for (Die die : dice)
                    twoPairsBucket[die.faceValue - 1]++;
                for (int j : twoPairsBucket)
                    if (j >= 2)
                        numberOfPairs += j / 2;
                if (numberOfPairs >= 2)
                    hasTwoPairs = true;
                isValid = hasTwoPairs;
                break;
            case 8:
                boolean hasThreeKind = false;
                int[] threeKindBucket = new int[6];
                for (Die die : dice)
                    threeKindBucket[die.faceValue - 1]++;
                for (int i : threeKindBucket)
                    if (i >= 3) {
                        hasThreeKind = true;
                        break;
                    }
                isValid = hasThreeKind;
                break;
            case 9:
                boolean hasFourKind = false;
                int[] fourKindBucket = new int[6];
                for (Die die : dice)
                    fourKindBucket[die.faceValue - 1]++;
                for (int i : fourKindBucket)
                    if (i >= 4) {
                        hasFourKind = true;
                        break;
                    }
                isValid = hasFourKind;
                break;
            case 10:
                int[] lowStraight = {1, 2, 3, 4, 5};
                int[] tempLowStraight = new int[dice.length];
                for (int i = 0; i < tempLowStraight.length; i++)
                    tempLowStraight[i] = dice[i].faceValue;
                Arrays.sort(tempLowStraight);
                isValid = Arrays.equals(lowStraight, tempLowStraight);
                break;
            case 11:
                int[] highStraight = {2, 3, 4, 5, 6};
                int[] tempHighStraight = new int[dice.length];
                for (int i = 0; i < tempHighStraight.length; i++)
                    tempHighStraight[i] = dice[i].faceValue;
                Arrays.sort(tempHighStraight);
                isValid = Arrays.equals(highStraight, tempHighStraight);
                break;
            case 12:
                int[] houseBucket = new int[6];
                for (Die die : dice)
                    houseBucket[die.faceValue - 1]++;
                Arrays.sort(houseBucket);
                if (houseBucket[4] != 2 || houseBucket[5] != 3)
                    isValid = false;
                break;
            case 14:
                for (int i = 1; i < dice.length; i++) {
                    if (dice[0].faceValue != dice[i].faceValue) {
                        isValid = false;
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return isValid;
    }

    private int calculatePoints(int i) {
        int points = 0;
        if (i >= 0 && i <= 5) {
            for (Die die : dice)
                if (i + 1 == die.faceValue)
                    points += die.faceValue;
        } else if (i >= 6 && i <= 9) {
            switch (i) {
                case 6:
                    int pair = 0;
                    int[] pairsBucket = new int[6];
                    for (Die die : dice)
                        pairsBucket[die.faceValue - 1]++;
                    for (int j = 0; j < pairsBucket.length; j++)
                        if (pairsBucket[j] >= 2)
                            pair = (j + 1) * 2;
                    points = pair;
                    break;
                case 7:
                    int twoPairs = 0;
                    int[] twoPairsBucket = new int[6];
                    int[] twoPairsTemp = new int[dice.length];
                    for (int j = 0; j < dice.length; j++)
                        twoPairsTemp[j] = dice[j].faceValue;
                    for (int j = 0; j < dice.length - 1; j++) {
                        for (int k = j + 1; k < dice.length; k++) {
                            if (twoPairsTemp[j] == twoPairsTemp[k] && twoPairsTemp[j] != 0) {
                                twoPairsBucket[twoPairsTemp[j] - 1]++;
                                twoPairsTemp[j] = 0;
                                twoPairsTemp[k] = 0;
                            }
                        }
                    }
                    for (int j = 0; j < twoPairsBucket.length; j++) {
                        if (twoPairsBucket[j] >= 2) {
                            twoPairs += ((j + 1) * 2) * 2;
                            twoPairsBucket[j]--;
                        } else if (twoPairsBucket[j] >= 1) {
                            twoPairs += (j + 1) * 2;
                        }
                    }
                    points = twoPairs;
                    break;
                case 8:
                    int threeKind = 0;
                    int[] threeKindBucket = new int[6];
                    for (Die die : dice)
                        threeKindBucket[die.faceValue - 1]++;
                    for (int j = 0; j < threeKindBucket.length; j++)
                        if (threeKindBucket[j] >= 3)
                            threeKind = (j + 1) * 3;
                    points = threeKind;
                    break;
                case 9:
                    int fourKind = 0;
                    int[] fourKindBucket = new int[6];
                    for (Die die : dice)
                        fourKindBucket[die.faceValue - 1]++;
                    for (int j = 0; j < fourKindBucket.length; j++)
                        if (fourKindBucket[j] >= 4)
                            fourKind = (j + 1) * 4;
                    points = fourKind;
                    break;
                default:
                    break;
            }
        } else if (i == 14) {
            points = 50;
        } else {
            for (Die die : dice)
                points += die.faceValue;
        }
        return points;
    }

    private void updateImage() {
        for (int i = 0; i < dice.length; i++)
            diceImageViews[i].setImage(diceImages[dice[i].faceValue - 1]);
    }

    private void updateContrast(int i) {
        ColorAdjust colorAdjust = new ColorAdjust();
        if (dice[i].isLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        diceImageViews[i].setEffect(colorAdjust);
    }

    private void updateText() {
        pointsLabel.setText("0");
        if (rollCount == 0) {
            for (Text score : scoreUpperText)
                score.setCursor(Cursor.DEFAULT);
            for (Text score : scoreLowerText)
                score.setCursor(Cursor.DEFAULT);
        } else {
            for (int i = 0; i < isSubmitted.length; i++) {
                if (!isSubmitted[i] && i <= 5) {
                    scoreUpperText[i].setCursor(Cursor.HAND);
                } else if (!isSubmitted[i]) {
                    scoreLowerText[i - 6].setCursor(Cursor.HAND);
                }
            }
        }
        for (int i = 0; i < isClicked.length; i++)
            if (isClicked[i] && validateSubmit(i))
                pointsLabel.setText("" + calculatePoints(i));
        int sumUpper = 0;
        for (int i : scoreUpper)
            sumUpper += i;
        scoreSumUpper.setText("" + sumUpper);
        int totalSum = sumUpper;
        if (sumUpper >= 63) {
            scoreBonus.setOpacity(1.0);
            totalSum += 50;
        }
        for (int i : scoreLower)
            totalSum += i;
        scoreTotalSum.setText("" + totalSum);
    }

    public void unlockCheats() {
        String password = passwordCheat.getText();
        if (password.equals("yatzy")) {
            cheatOnes.setDisable(false);
            cheatTwos.setDisable(false);
            cheatThrees.setDisable(false);
            cheatFours.setDisable(false);
            cheatFives.setDisable(false);
            cheatPairs.setDisable(false);
            cheatLowStraight.setDisable(false);
            cheatHighStraight.setDisable(false);
            cheatFullHouse.setDisable(false);
            cheatYatzy.setDisable(false);
        }
    }

    public void cheatOnes() {
        dice[0].faceValue = 1;
        dice[1].faceValue = 1;
        dice[2].faceValue = 1;
        dice[3].faceValue = 1;
        dice[4].faceValue = 1;
        updateImage();
        updateText();
    }

    public void cheatTwos() {
        dice[0].faceValue = 2;
        dice[1].faceValue = 2;
        dice[2].faceValue = 2;
        dice[3].faceValue = 2;
        dice[4].faceValue = 2;
        updateImage();
        updateText();
    }

    public void cheatThrees() {
        dice[0].faceValue = 3;
        dice[1].faceValue = 3;
        dice[2].faceValue = 3;
        dice[3].faceValue = 3;
        dice[4].faceValue = 3;
        updateImage();
        updateText();
    }

    public void cheatFours() {
        dice[0].faceValue = 4;
        dice[1].faceValue = 4;
        dice[2].faceValue = 4;
        dice[3].faceValue = 4;
        dice[4].faceValue = 4;
        updateImage();
        updateText();
    }

    public void cheatFives() {
        dice[0].faceValue = 5;
        dice[1].faceValue = 5;
        dice[2].faceValue = 5;
        dice[3].faceValue = 5;
        dice[4].faceValue = 5;
        updateImage();
        updateText();
    }

    public void cheatPairs() {
        dice[0].faceValue = 1;
        dice[1].faceValue = 3;
        dice[2].faceValue = 3;
        dice[3].faceValue = 6;
        dice[4].faceValue = 6;
        updateImage();
        updateText();
    }

    public void cheatLowStraight() {
        dice[0].faceValue = 1;
        dice[1].faceValue = 2;
        dice[2].faceValue = 3;
        dice[3].faceValue = 4;
        dice[4].faceValue = 5;
        updateImage();
        updateText();
    }

    public void cheatHighStraight() {
        dice[0].faceValue = 2;
        dice[1].faceValue = 3;
        dice[2].faceValue = 4;
        dice[3].faceValue = 5;
        dice[4].faceValue = 6;
        updateImage();
        updateText();
    }

    public void cheatFullHouse() {
        dice[0].faceValue = 5;
        dice[1].faceValue = 5;
        dice[2].faceValue = 6;
        dice[3].faceValue = 6;
        dice[4].faceValue = 6;
        updateImage();
        updateText();
    }

    public void cheatYatzy() {
        dice[0].faceValue = 6;
        dice[1].faceValue = 6;
        dice[2].faceValue = 6;
        dice[3].faceValue = 6;
        dice[4].faceValue = 6;
        updateImage();
        updateText();
    }

    private static class Die {

        private static final Random rand = new Random();
        private int faceValue;
        private boolean isLocked = false;

        private void setLocked() {
            isLocked = !isLocked;
        }

        private void roll() {
            faceValue = rand.nextInt(6) + 1;
        }

    }

}
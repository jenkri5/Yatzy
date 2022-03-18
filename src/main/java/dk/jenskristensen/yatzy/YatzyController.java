package dk.jenskristensen.yatzy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private final Text[] scoreUpperTexts = {scoreOnes, scoreTwos, scoreThrees, scoreFours, scoreFives, scoreSixes};
    private final Text[] scoreLowerTexts = {scorePair, scoreTwoPairs, scoreThreeKind, scoreFourKind, scoreLowStraight, scoreHighStraight, scoreFullHouse, scoreChance, scoreYatzy};

    @FXML
    private HBox diceBox;
    @FXML
    private GridPane scorePane;
    @FXML
    private Text pointsLabel;
    @FXML
    private Button rollButton, submitButton;

    private final boolean[] isClicked = new boolean[15];
    private final boolean[] isSubmitted = new boolean[15];
    private final int[] scoresUpper = new int[6];
    private final int[] scoresLower = new int[9];

    private final int nDice = 5;
    private final Die die1 = new Die(), die2 = new Die(), die3 = new Die(), die4 = new Die(), die5 = new Die();
    private final Die[] dice = {die1, die2, die3, die4, die5};
    private int rollCount;

    public void initialize() {
        for (int i = 0; i < nDice; i++) {
            diceImageViews[i].setFitHeight(40.0);
            diceImageViews[i].setFitWidth(40.0);
            int finalI = i;
            diceImageViews[i].setOnMouseClicked(event -> lockDice(finalI));
            diceBox.getChildren().add(diceImageViews[i]);
        }
        for (int i = 0; i < scoreUpperTexts.length; i++) {
            scoreUpperTexts[i].setOpacity(0.5);
            int finalI = i;
            scoreUpperTexts[i].setOnMouseClicked(event -> scoreClicked(finalI));
            scorePane.add(scoreUpperTexts[i],1,i);
        }

        for (int i = 0; i < scoreLowerTexts.length; i++) {
            scoreLowerTexts[i].setOpacity(0.5);
            int finalI = i + 6;
            scoreLowerTexts[i].setOnMouseClicked(event -> scoreClicked(finalI));
            scorePane.add(scoreLowerTexts[i], 1, i + 8);
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
                scoreUpperTexts[i].setOpacity(1.0);
            else
                scoreLowerTexts[i-6].setOpacity(1.0);
        }
        else if (!isSubmitted[i]) {
            if (i <= 5)
                scoreUpperTexts[i].setOpacity(0.5);
            else
                scoreLowerTexts[i-6].setOpacity(0.5);
        }
        updateText();
    }

    // OBS! MISSING NEW LOGIC
    public void submit() {
        for (int i = 0; i < isClicked.length; i++) {
            switch (i) {
                case 0:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreOnes.setText("" + calculatePoints(i));
                        scoresUpper[i] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 1:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreTwos.setText("" + calculatePoints(i));
                        scoresUpper[i] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 2:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreThrees.setText("" + calculatePoints(i));
                        scoresUpper[i] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 3:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreFours.setText("" + calculatePoints(i));
                        scoresUpper[i] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 4:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreFives.setText("" + calculatePoints(i));
                        scoresUpper[i] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 5:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreSixes.setText("" + calculatePoints(i));
                        scoresUpper[i] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 6:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scorePair.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 7:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreTwoPairs.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 8:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreThreeKind.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 9:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreFourKind.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 10:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreLowStraight.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 11:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreHighStraight.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 12:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreFullHouse.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 13:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreChance.setText("" + calculatePoints(i));
                        scoresLower[i - 6] = calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 14:
                    if (isClicked[i] && !isSubmitted[i]) {
                        if (validateSubmit(i)) {
                            scoreYatzy.setText("" + calculatePoints(i));
                            scoresLower[i - 6] = calculatePoints(i);
                        } else {
                            scoresLower[i - 6] = 0;
                        }
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void reset() {
        rollCount = 0;
        for (int i = 0; i < nDice; i++)
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
        Arrays.fill(scoresUpper, 0);
        Arrays.fill(scoresLower, 0);
        scoreOnes.setText("0");
        scoreOnes.setOpacity(0.5);
        scoreTwos.setText("0");
        scoreTwos.setOpacity(0.5);
        scoreThrees.setText("0");
        scoreThrees.setOpacity(0.5);
        scoreFours.setText("0");
        scoreFours.setOpacity(0.5);
        scoreFives.setText("0");
        scoreFives.setOpacity(0.5);
        scoreSixes.setText("0");
        scoreSixes.setOpacity(0.5);
        scoreSumUpper.setText("0");
        scoreBonus.setOpacity(0.2);
        scorePair.setText("0");
        scorePair.setOpacity(0.5);
        scoreTwoPairs.setText("0");
        scoreTwoPairs.setOpacity(0.5);
        scoreThreeKind.setText("0");
        scoreThreeKind.setOpacity(0.5);
        scoreFourKind.setText("0");
        scoreFourKind.setOpacity(0.5);
        scoreLowStraight.setText("0");
        scoreLowStraight.setOpacity(0.5);
        scoreHighStraight.setText("0");
        scoreHighStraight.setOpacity(0.5);
        scoreFullHouse.setText("0");
        scoreFullHouse.setOpacity(0.5);
        scoreChance.setText("0");
        scoreChance.setOpacity(0.5);
        scoreYatzy.setText("0");
        scoreYatzy.setOpacity(0.5);
        scoreTotalSum.setText("0");
        pointsLabel.setText("0");
        submitButton.setDisable(true);
        rollButton.setDisable(false);
        rollCount = 0;
        for (int i = 0; i < nDice; i++) {
            dice[i].faceValue = i + 1;
            dice[i].isLocked = false;
            updateContrast(i);
        }
        updateImage();
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

    private boolean validateClick(int n) {
        boolean isValid = false;
        if (!isSubmitted[n] && rollCount != 0) {
            for (int i = 0; i < scoreUpperTexts.length; i++) {
                if (isClicked[i] && i != n) {
                    if (!isSubmitted[i])
                        scoreUpperTexts[i].setOpacity(0.5);
                    isClicked[i] = false;
                }
            }
            for (int i = 0; i < scoreLowerTexts.length; i++) {
                if (isClicked[i+6] && i+6 != n) {
                    if (!isSubmitted[i+6])
                        scoreLowerTexts[i].setOpacity(0.5);
                    isClicked[i] = false;
                }
            }
            if (!isClicked[n]) {
                isValid = true;
                submitButton.setDisable(false);
                isClicked[n] = true;
            } else {
                submitButton.setDisable(false);
                isClicked[n] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            for (int i = 0; i < scoreUpperTexts.length; i++) {
                if (!isSubmitted[i])
                    scoreUpperTexts[i].setOpacity(0.5);
            }
            for (int i = 0; i < scoreLowerTexts.length; i++) {
                if (!isSubmitted[i+6])
                    scoreLowerTexts[i].setOpacity(0.5);
            }
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
        return isValid;
    }

    // OBS! MISSING NEW LOGIC
    private boolean validateSubmit(int n) {
        boolean isValid = true;
        switch (n) {
            case 6:
                boolean hasPair = false;
                for (int i = 0; i < nDice - 1; i++)
                    for (int j = i + 1; j < nDice; j++)
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
                int[] tempLowStraight = new int[nDice];
                for (int i = 0; i < tempLowStraight.length; i++)
                    tempLowStraight[i] = dice[i].faceValue;
                Arrays.sort(tempLowStraight);
                isValid = Arrays.equals(lowStraight, tempLowStraight);
                break;
            case 11:
                int[] highStraight = {2, 3, 4, 5, 6};
                int[] tempHighStraight = new int[nDice];
                for (int i = 0; i < tempHighStraight.length; i++)
                    tempHighStraight[i] = dice[i].faceValue;
                Arrays.sort(tempHighStraight);
                isValid = Arrays.equals(highStraight, tempHighStraight);
                break;
            case 12:
                int[] houseBucket = new int[6];
                for (Die die : dice)
                    houseBucket[die.faceValue-1]++;
                Arrays.sort(houseBucket);
                if (houseBucket[4] != 2 || houseBucket[5] != 3)
                    isValid = false;
                break;
            case 14:
                for (int i = 1; i < nDice; i++) {
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
                    int[] twoPairsTemp = new int[nDice];
                    for (int j = 0; j < nDice; j++)
                        twoPairsTemp[j] = dice[j].faceValue;
                    for (int j = 0; j < nDice - 1; j++) {
                        for (int k = j + 1; k < nDice; k++) {
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
        for (int i = 0; i < nDice; i++) {
            switch (dice[i].faceValue) {
                case 1:
                    diceImageViews[i].setImage(diceImageOne);
                    break;
                case 2:
                    diceImageViews[i].setImage(diceImageTwo);
                    break;
                case 3:
                    diceImageViews[i].setImage(diceImageThree);
                    break;
                case 4:
                    diceImageViews[i].setImage(diceImageFour);
                    break;
                case 5:
                    diceImageViews[i].setImage(diceImageFive);
                    break;
                case 6:
                    diceImageViews[i].setImage(diceImageSix);
                    break;
                default:
                    break;
            }
        }
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
        for (int i = 0; i < isClicked.length; i++)
            if (isClicked[i] && validateSubmit(i))
                pointsLabel.setText("" + calculatePoints(i));
        int sumUpper = 0;
        for (int i : scoresUpper)
            sumUpper += i;
        scoreSumUpper.setText("" + sumUpper);
        int totalSum = sumUpper;
        if (sumUpper >= 63) {
            scoreBonus.setOpacity(1.0);
            totalSum += 50;
        }
        for (int i : scoresLower)
            totalSum += i;
        scoreTotalSum.setText("" + totalSum);
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
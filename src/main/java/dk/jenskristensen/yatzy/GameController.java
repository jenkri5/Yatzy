package dk.jenskristensen.yatzy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameController {

    @FXML
    private ImageView dice0, dice1, dice2, dice3, dice4;
    @FXML
    private Text pointsLabel, scoreOnes, scoreTwos, scoreThrees,
            scoreFours, scoreFives, scoreSixes, scoreSumUpper,
            scoreBonus, scorePair, scoreTwoPairs, scoreThreeKind,
            scoreFourKind, scoreLowStraight, scoreHighStraight,
            scoreFullHouse, scoreChance, scoreYatzy, scoreTotalSum;
    @FXML
    private Button rollButton, submitButton;

    private final Image diceImageOne = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/1.png")));
    private final Image diceImageTwo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/2.png")));
    private final Image diceImageThree = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/3.png")));
    private final Image diceImageFour = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/4.png")));
    private final Image diceImageFive = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/5.png")));
    private final Image diceImageSix = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/6.png")));

    private final boolean[] isClicked = new boolean[15];
    private final boolean[] isSubmitted = new boolean[15];
    private final int[] scoresUpper = new int[6];
    private final int[] scoresLower = new int[9];

    public static void generateDice() {
        DiceThrower.generateDice();
    }

    public void roll() {
        DiceThrower.roll();
        updateImage();
        updateText();
    }

    public void reset() {
        DiceThrower.resetRollCount();
        for (DiceThrower.Die die : DiceThrower.dice)
            if (die.isLocked)
                die.setLocked();
        for (int i = 0; i < DiceThrower.nDice; i++)
            updateContrast(i);
        Arrays.fill(isClicked, false);
        submitButton.setDisable(true);
        rollButton.setDisable(false);
        updateText();
    }

    public void restart() {
        Arrays.fill(isClicked, false);
        Arrays.fill(isSubmitted, false);
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
        pointsLabel.setText("0");
        scoreSumUpper.setText("0");
        scoreBonus.setOpacity(0.2);
        submitButton.setDisable(true);
        rollButton.setDisable(false);
        DiceThrower.rollCount = 0;
        for (int i = 0; i < DiceThrower.nDice; i++) {
            DiceThrower.dice[i].faceValue = i + 1;
            DiceThrower.dice[i].isLocked = false;
            updateContrast(i);
        }
        updateImage();
    }

    public void lockOne() {
        if (DiceThrower.rollCount > 0) {
            DiceThrower.lock(0);
            updateContrast(0);
        }
    }

    public void lockTwo() {
        if (DiceThrower.rollCount > 0) {
            DiceThrower.lock(1);
            updateContrast(1);
        }
    }

    public void lockThree() {
        if (DiceThrower.rollCount > 0) {
            DiceThrower.lock(2);
            updateContrast(2);
        }
    }

    public void lockFour() {
        if (DiceThrower.rollCount > 0) {
            DiceThrower.lock(3);
            updateContrast(3);
        }
    }

    public void lockFive() {
        if (DiceThrower.rollCount > 0) {
            DiceThrower.lock(4);
            updateContrast(4);
        }
    }

    public void onesClicked() {
        if (!isSubmitted[0] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 1:
                            if (!isSubmitted[i])
                                scoreTwos.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 2:
                            if (!isSubmitted[i])
                                scoreThrees.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 3:
                            if (!isSubmitted[i])
                                scoreFours.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 4:
                            if (!isSubmitted[i])
                                scoreFives.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 5:
                            if (!isSubmitted[i])
                                scoreSixes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[0]) {
                scoreOnes.setOpacity(1.0);
                pointsLabel.setText("" + DiceThrower.calculatePoints(0));
                submitButton.setDisable(false);
                isClicked[0] = true;
            } else {
                scoreOnes.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[0] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[1])
                scoreTwos.setOpacity(0.5);
            if (!isSubmitted[2])
                scoreThrees.setOpacity(0.5);
            if (!isSubmitted[3])
                scoreFours.setOpacity(0.5);
            if (!isSubmitted[4])
                scoreFives.setOpacity(0.5);
            if (!isSubmitted[5])
                scoreSixes.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void twosClicked() {
        if (!isSubmitted[1] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 0:
                            if (!isSubmitted[i])
                                scoreOnes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 2:
                            if (!isSubmitted[i])
                                scoreThrees.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 3:
                            if (!isSubmitted[i])
                                scoreFours.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 4:
                            if (!isSubmitted[i])
                                scoreFives.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 5:
                            if (!isSubmitted[i])
                                scoreSixes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[1]) {
                scoreTwos.setOpacity(1.0);
                pointsLabel.setText("" + DiceThrower.calculatePoints(1));
                submitButton.setDisable(false);
                isClicked[1] = true;
            } else {
                scoreTwos.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[1] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[0])
                scoreOnes.setOpacity(0.5);
            if (!isSubmitted[2])
                scoreThrees.setOpacity(0.5);
            if (!isSubmitted[3])
                scoreFours.setOpacity(0.5);
            if (!isSubmitted[4])
                scoreFives.setOpacity(0.5);
            if (!isSubmitted[5])
                scoreSixes.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void threesClicked() {
        if (!isSubmitted[2] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 0:
                            if (!isSubmitted[i])
                                scoreOnes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 1:
                            if (!isSubmitted[i])
                                scoreTwos.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 3:
                            if (!isSubmitted[i])
                                scoreFours.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 4:
                            if (!isSubmitted[i])
                                scoreFives.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 5:
                            if (!isSubmitted[i])
                                scoreSixes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[2]) {
                scoreThrees.setOpacity(1.0);
                pointsLabel.setText("" + DiceThrower.calculatePoints(2));
                submitButton.setDisable(false);
                isClicked[2] = true;
            } else {
                scoreThrees.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[2] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[0])
                scoreOnes.setOpacity(0.5);
            if (!isSubmitted[1])
                scoreTwos.setOpacity(0.5);
            if (!isSubmitted[3])
                scoreFours.setOpacity(0.5);
            if (!isSubmitted[4])
                scoreFives.setOpacity(0.5);
            if (!isSubmitted[5])
                scoreSixes.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void foursClicked() {
        if (!isSubmitted[3] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 0:
                            if (!isSubmitted[i])
                                scoreOnes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 1:
                            if (!isSubmitted[i])
                                scoreTwos.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 2:
                            if (!isSubmitted[i])
                                scoreThrees.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 4:
                            if (!isSubmitted[i])
                                scoreFives.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 5:
                            if (!isSubmitted[i])
                                scoreSixes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[3]) {
                scoreFours.setOpacity(1.0);
                pointsLabel.setText("" + DiceThrower.calculatePoints(3));
                submitButton.setDisable(false);
                isClicked[3] = true;
            } else {
                scoreFours.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[3] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[0])
                scoreOnes.setOpacity(0.5);
            if (!isSubmitted[1])
                scoreTwos.setOpacity(0.5);
            if (!isSubmitted[2])
                scoreThrees.setOpacity(0.5);
            if (!isSubmitted[4])
                scoreFives.setOpacity(0.5);
            if (!isSubmitted[5])
                scoreSixes.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void fivesClicked() {
        if (!isSubmitted[4] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 0:
                            if (!isSubmitted[i])
                                scoreOnes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 1:
                            if (!isSubmitted[i])
                                scoreTwos.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 2:
                            if (!isSubmitted[i])
                                scoreThrees.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 3:
                            if (!isSubmitted[i])
                                scoreFours.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 5:
                            if (!isSubmitted[i])
                                scoreSixes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[4]) {
                scoreFives.setOpacity(1.0);
                pointsLabel.setText("" + DiceThrower.calculatePoints(4));
                submitButton.setDisable(false);
                isClicked[4] = true;
            } else {
                scoreFives.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[4] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[0])
                scoreOnes.setOpacity(0.5);
            if (!isSubmitted[1])
                scoreTwos.setOpacity(0.5);
            if (!isSubmitted[2])
                scoreThrees.setOpacity(0.5);
            if (!isSubmitted[3])
                scoreFours.setOpacity(0.5);
            if (!isSubmitted[5])
                scoreSixes.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void sixesClicked() {
        if (!isSubmitted[5] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 0:
                            if (!isSubmitted[i])
                                scoreOnes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 1:
                            if (!isSubmitted[i])
                                scoreTwos.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 2:
                            if (!isSubmitted[i])
                                scoreThrees.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 3:
                            if (!isSubmitted[i])
                                scoreFours.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 4:
                            if (!isSubmitted[i])
                                scoreFives.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[5]) {
                scoreSixes.setOpacity(1.0);
                pointsLabel.setText("" + DiceThrower.calculatePoints(5));
                submitButton.setDisable(false);
                isClicked[5] = true;
            } else {
                scoreSixes.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[5] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[0])
                scoreOnes.setOpacity(0.5);
            if (!isSubmitted[1])
                scoreTwos.setOpacity(0.5);
            if (!isSubmitted[2])
                scoreThrees.setOpacity(0.5);
            if (!isSubmitted[3])
                scoreFours.setOpacity(0.5);
            if (!isSubmitted[4])
                scoreFives.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void pairClicked() {
        // add && DiceThrower.hasPair() to if statement
        if (!isSubmitted[6] && DiceThrower.rollCount != 0) {
            for (int i = 0; i < isClicked.length; i++) {
                if (isClicked[i]) {
                    switch (i) {
                        case 0:
                            if (!isSubmitted[i])
                                scoreOnes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 1:
                            if (!isSubmitted[i])
                                scoreTwos.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 2:
                            if (!isSubmitted[i])
                                scoreThrees.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 3:
                            if (!isSubmitted[i])
                                scoreFours.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 4:
                            if (!isSubmitted[i])
                                scoreFives.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 5:
                            if (!isSubmitted[i])
                                scoreSixes.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 7:
                            if (!isSubmitted[i])
                                scoreTwoPairs.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 8:
                            if (!isSubmitted[i])
                                scoreThreeKind.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 9:
                            if (!isSubmitted[i])
                                scoreFourKind.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 10:
                            if (!isSubmitted[i])
                                scoreLowStraight.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 11:
                            if (!isSubmitted[i])
                                scoreHighStraight.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 12:
                            if (!isSubmitted[i])
                                scoreFullHouse.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 13:
                            if (!isSubmitted[i])
                                scoreChance.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        case 14:
                            if (!isSubmitted[i])
                                scoreYatzy.setOpacity(0.5);
                            isClicked[i] = false;
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!isClicked[6]) {
                scorePair.setOpacity(1.0);
                pointsLabel.setText("" + "DiceThrower.calculatePair()");
                submitButton.setDisable(false);
                isClicked[6] = true;
            } else {
                scorePair.setOpacity(0.5);
                pointsLabel.setText("0");
                submitButton.setDisable(true);
                isClicked[6] = false;
            }
        } else {
            Arrays.fill(isClicked, false);
            if (!isSubmitted[0])
                scoreOnes.setOpacity(0.5);
            if (!isSubmitted[1])
                scoreTwos.setOpacity(0.5);
            if (!isSubmitted[2])
                scoreThrees.setOpacity(0.5);
            if (!isSubmitted[3])
                scoreFours.setOpacity(0.5);
            if (!isSubmitted[4])
                scoreFives.setOpacity(0.5);
            if (!isSubmitted[5])
                scoreSixes.setOpacity(0.5);
            if (!isSubmitted[7])
                scoreTwoPairs.setOpacity(0.5);
            if (!isSubmitted[8])
                scoreThreeKind.setOpacity(0.5);
            if (!isSubmitted[9])
                scoreFourKind.setOpacity(0.5);
            if (!isSubmitted[10])
                scoreLowStraight.setOpacity(0.5);
            if (!isSubmitted[11])
                scoreHighStraight.setOpacity(0.5);
            if (!isSubmitted[12])
                scoreFullHouse.setOpacity(0.5);
            if (!isSubmitted[13])
                scoreChance.setOpacity(0.5);
            if (!isSubmitted[14])
                scoreYatzy.setOpacity(0.5);
            submitButton.setDisable(true);
            pointsLabel.setText("0");
        }
    }

    public void twoPairsClicked() {

    }

    public void threeKindClicked() {

    }

    public void fourKindClicked() {

    }

    public void lowStraightClicked() {

    }

    public void highStraightClicked() {

    }

    public void fullHouseClicked() {

    }

    public void chanceClicked() {

    }

    public void yatzyClicked() {

    }

    public void submit() {
        for (int i = 0; i < isClicked.length; i++) {
            switch (i) {
                case 0:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreOnes.setText("" + DiceThrower.calculatePoints(i));
                        scoresUpper[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 1:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreTwos.setText("" + DiceThrower.calculatePoints(i));
                        scoresUpper[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 2:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreThrees.setText("" + DiceThrower.calculatePoints(i));
                        scoresUpper[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 3:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreFours.setText("" + DiceThrower.calculatePoints(i));
                        scoresUpper[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 4:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreFives.setText("" + DiceThrower.calculatePoints(i));
                        scoresUpper[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 5:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreSixes.setText("" + DiceThrower.calculatePoints(i));
                        scoresUpper[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void updateImage() {
        for (int i = 0; i < DiceThrower.nDice; i++) {
            switch (DiceThrower.dice[i].faceValue) {
                case 1:
                    if (i == 0)
                        dice0.setImage(diceImageOne);
                    else if (i == 1)
                        dice1.setImage(diceImageOne);
                    else if (i == 2)
                        dice2.setImage(diceImageOne);
                    else if (i == 3)
                        dice3.setImage(diceImageOne);
                    else
                        dice4.setImage(diceImageOne);
                    break;
                case 2:
                    if (i == 0)
                        dice0.setImage(diceImageTwo);
                    else if (i == 1)
                        dice1.setImage(diceImageTwo);
                    else if (i == 2)
                        dice2.setImage(diceImageTwo);
                    else if (i == 3)
                        dice3.setImage(diceImageTwo);
                    else
                        dice4.setImage(diceImageTwo);
                    break;
                case 3:
                    if (i == 0)
                        dice0.setImage(diceImageThree);
                    else if (i == 1)
                        dice1.setImage(diceImageThree);
                    else if (i == 2)
                        dice2.setImage(diceImageThree);
                    else if (i == 3)
                        dice3.setImage(diceImageThree);
                    else
                        dice4.setImage(diceImageThree);
                    break;
                case 4:
                    if (i == 0)
                        dice0.setImage(diceImageFour);
                    else if (i == 1)
                        dice1.setImage(diceImageFour);
                    else if (i == 2)
                        dice2.setImage(diceImageFour);
                    else if (i == 3)
                        dice3.setImage(diceImageFour);
                    else
                        dice4.setImage(diceImageFour);
                    break;
                case 5:
                    if (i == 0)
                        dice0.setImage(diceImageFive);
                    else if (i == 1)
                        dice1.setImage(diceImageFive);
                    else if (i == 2)
                        dice2.setImage(diceImageFive);
                    else if (i == 3)
                        dice3.setImage(diceImageFive);
                    else
                        dice4.setImage(diceImageFive);
                    break;
                case 6:
                    if (i == 0)
                        dice0.setImage(diceImageSix);
                    else if (i == 1)
                        dice1.setImage(diceImageSix);
                    else if (i == 2)
                        dice2.setImage(diceImageSix);
                    else if (i == 3)
                        dice3.setImage(diceImageSix);
                    else
                        dice4.setImage(diceImageSix);
                    break;
                default:
                    break;
            }
        }
        if (DiceThrower.rollCount >= 3) {
            rollButton.setDisable(true);
        }
    }

    private void updateContrast(int i) {
        ColorAdjust colorAdjust = new ColorAdjust();
        switch (i) {
            case 0:
                if (DiceThrower.dice[i].isLocked)
                    colorAdjust.setContrast(-0.5);
                else
                    colorAdjust.setContrast(0.0);
                dice0.setEffect(colorAdjust);
                break;
            case 1:
                if (DiceThrower.dice[i].isLocked)
                    colorAdjust.setContrast(-0.5);
                else
                    colorAdjust.setContrast(0.0);
                dice1.setEffect(colorAdjust);
                break;
            case 2:
                if (DiceThrower.dice[i].isLocked)
                    colorAdjust.setContrast(-0.5);
                else
                    colorAdjust.setContrast(0.0);
                dice2.setEffect(colorAdjust);
                break;
            case 3:
                if (DiceThrower.dice[i].isLocked)
                    colorAdjust.setContrast(-0.5);
                else
                    colorAdjust.setContrast(0.0);
                dice3.setEffect(colorAdjust);
                break;
            case 4:
                if (DiceThrower.dice[i].isLocked)
                    colorAdjust.setContrast(-0.5);
                else
                    colorAdjust.setContrast(0.0);
                dice4.setEffect(colorAdjust);
                break;
            default:
                break;
        }
    }

    private void updateText() {
        for (int i = 0; i < isClicked.length; i++)
            if (isClicked[i])
                pointsLabel.setText("" + DiceThrower.calculatePoints(i));
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

    private static class DiceThrower {

        private final static int nDice = 5;
        private final static Die[] dice = new Die[nDice];
        private static int rollCount;

        private static void generateDice() {
            for (int i = 0; i < nDice; i++)
                dice[i] = new DiceThrower.Die();
        }

        private static void resetRollCount() {
            rollCount = 0;
        }

        private static void roll() {
            Arrays.stream(dice).filter(die -> !die.isLocked).forEach(Die::roll);
            rollCount++;
        }

        private static void lock(int i) {
            dice[i].setLocked();
        }

        private static int calculatePoints(int i) {
            int points = 0;
            for (Die die : dice) {
                if (i + 1 == die.faceValue)
                    points += die.faceValue;
            }
            return points;
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

}

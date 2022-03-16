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
    private Text pointsLabel, scoreOnes, scoreTwos, scoreThrees, scoreFours, scoreFives, scoreSixes;
    @FXML
    private Button rollButton, submitButton;

    private final Image diceImageOne = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/1.png")));
    private final Image diceImageTwo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/2.png")));
    private final Image diceImageThree = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/3.png")));
    private final Image diceImageFour = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/4.png")));
    private final Image diceImageFive = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/5.png")));
    private final Image diceImageSix = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/6.png")));

    private final boolean[] isClicked = new boolean[6];
    private final boolean[] isSubmitted = new boolean[6];
    private final int[] scores = new int[6];

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
        for (boolean b : isClicked)
            b = false;
        submitButton.setDisable(true);
        pointsLabel.setText("" + 0);
        rollButton.setDisable(false);
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
            for (int i = 0; i < isClicked.length; i++) {
                isClicked[i] = false;
            }
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
            for (int i = 0; i < isClicked.length; i++) {
                isClicked[i] = false;
            }
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
            for (int i = 0; i < isClicked.length; i++) {
                isClicked[i] = false;
            }
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
            for (int i = 0; i < isClicked.length; i++) {
                isClicked[i] = false;
            }
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
            for (int i = 0; i < isClicked.length; i++) {
                isClicked[i] = false;
            }
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
            for (int i = 0; i < isClicked.length; i++) {
                isClicked[i] = false;
            }
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

    public void submit() {
        for (int i = 0; i < isClicked.length; i++) {
            switch (i) {
                case 0:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreOnes.setText("" + DiceThrower.calculatePoints(i));
                        scores[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 1:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreTwos.setText("" + DiceThrower.calculatePoints(i));
                        scores[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 2:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreThrees.setText("" + DiceThrower.calculatePoints(i));
                        scores[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 3:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreFours.setText("" + DiceThrower.calculatePoints(i));
                        scores[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 4:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreFives.setText("" + DiceThrower.calculatePoints(i));
                        scores[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                case 5:
                    if (isClicked[i] && !isSubmitted[i]) {
                        scoreSixes.setText("" + DiceThrower.calculatePoints(i));
                        scores[i] = DiceThrower.calculatePoints(i);
                        isSubmitted[i] = true;
                        reset();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // future :: rework as array of ImageViews rather than if-else
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
            if (isClicked[i] == true)
                pointsLabel.setText(""+DiceThrower.calculatePoints(i));
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

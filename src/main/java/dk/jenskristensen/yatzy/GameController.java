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
    private Text pointsLabel;
    @FXML
    private Button rollButton;

    private final Image diceImageOne = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/1.png")));
    private final Image diceImageTwo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/2.png")));
    private final Image diceImageThree = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/3.png")));
    private final Image diceImageFour = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/4.png")));
    private final Image diceImageFive = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/5.png")));
    private final Image diceImageSix = new Image(Objects.requireNonNull(getClass().getResourceAsStream("dice/6.png")));

    public void roll() {
        DiceThrower.roll();
        updateGame();
        pointsLabel.setText("" + DiceThrower.calculatePoints());
    }

    public void lockOne() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(0);
        if (DiceThrower.dice[0].isLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice0.setEffect(colorAdjust);
    }

    public void lockTwo() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(1);
        if (DiceThrower.dice[1].isLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice1.setEffect(colorAdjust);
    }

    public void lockThree() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(2);
        if (DiceThrower.dice[2].isLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice2.setEffect(colorAdjust);
    }

    public void lockFour() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(3);
        if (DiceThrower.dice[3].isLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice3.setEffect(colorAdjust);
    }

    public void lockFive() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(4);
        if (DiceThrower.dice[4].isLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice4.setEffect(colorAdjust);
    }

    // future :: rework as array of ImageViews rather than if-else
    private void updateGame() {
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

    public void reset() {
        DiceThrower.resetRollCount();
        for(DiceThrower.Die die : DiceThrower.dice) {
            if(die.isLocked)
                die.setLocked();
        }
        rollButton.setDisable(false);
    }

    public static void generateDice() {
        DiceThrower.generateDice();
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

        private static int calculatePoints() {
            int points = 0;
            // This class is not implemented
            for (Die die : dice) {
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

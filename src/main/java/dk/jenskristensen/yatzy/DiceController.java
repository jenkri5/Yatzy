package dk.jenskristensen.yatzy;

import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class DiceController {

    @FXML
    ImageView dice0, dice1, dice2, dice3, dice4, dice5;
    @FXML
    Image diceImage;

    public void roll() {
        DiceThrower.roll();
        updateImage();
    }

    public void lockOne() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(0);
        if(DiceThrower.m_Dice[0].m_IsLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice0.setEffect(colorAdjust);
    }

    public void lockTwo() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(1);
        if(DiceThrower.m_Dice[1].m_IsLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice1.setEffect(colorAdjust);
    }

    public void lockThree() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(2);
        if(DiceThrower.m_Dice[2].m_IsLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice2.setEffect(colorAdjust);
    }

    public void lockFour() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(3);
        if(DiceThrower.m_Dice[3].m_IsLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice3.setEffect(colorAdjust);
    }

    public void lockFive() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(4);
        if(DiceThrower.m_Dice[4].m_IsLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice4.setEffect(colorAdjust);
    }

    public void lockSix() {
        ColorAdjust colorAdjust = new ColorAdjust();
        DiceThrower.lock(5);
        if(DiceThrower.m_Dice[5].m_IsLocked)
            colorAdjust.setContrast(-0.5);
        else
            colorAdjust.setContrast(0.0);
        dice5.setEffect(colorAdjust);
    }

    private void updateImage() {
        for (int i = 0; i < DiceThrower.m_nDice; i++) {
            switch (DiceThrower.m_Dice[i].m_FaceValue) {
                case 1:
                    diceImage = new Image(getClass().getResourceAsStream("dice/1.png"));
                    if (i == 0)
                        dice0.setImage(diceImage);
                    else if (i == 1)
                        dice1.setImage(diceImage);
                    else if (i == 2)
                        dice2.setImage(diceImage);
                    else if (i == 3)
                        dice3.setImage(diceImage);
                    else if (i == 4)
                        dice4.setImage(diceImage);
                    else
                        dice5.setImage(diceImage);
                    break;
                case 2:
                    diceImage = new Image(getClass().getResourceAsStream("dice/2.png"));
                    if (i == 0)
                        dice0.setImage(diceImage);
                    else if (i == 1)
                        dice1.setImage(diceImage);
                    else if (i == 2)
                        dice2.setImage(diceImage);
                    else if (i == 3)
                        dice3.setImage(diceImage);
                    else if (i == 4)
                        dice4.setImage(diceImage);
                    else
                        dice5.setImage(diceImage);
                    break;
                case 3:
                    diceImage = new Image(getClass().getResourceAsStream("dice/3.png"));
                    if (i == 0)
                        dice0.setImage(diceImage);
                    else if (i == 1)
                        dice1.setImage(diceImage);
                    else if (i == 2)
                        dice2.setImage(diceImage);
                    else if (i == 3)
                        dice3.setImage(diceImage);
                    else if (i == 4)
                        dice4.setImage(diceImage);
                    else
                        dice5.setImage(diceImage);
                    break;
                case 4:
                    diceImage = new Image(getClass().getResourceAsStream("dice/4.png"));
                    if (i == 0)
                        dice0.setImage(diceImage);
                    else if (i == 1)
                        dice1.setImage(diceImage);
                    else if (i == 2)
                        dice2.setImage(diceImage);
                    else if (i == 3)
                        dice3.setImage(diceImage);
                    else if (i == 4)
                        dice4.setImage(diceImage);
                    else
                        dice5.setImage(diceImage);
                    break;
                case 5:
                    diceImage = new Image(getClass().getResourceAsStream("dice/5.png"));
                    if (i == 0)
                        dice0.setImage(diceImage);
                    else if (i == 1)
                        dice1.setImage(diceImage);
                    else if (i == 2)
                        dice2.setImage(diceImage);
                    else if (i == 3)
                        dice3.setImage(diceImage);
                    else if (i == 4)
                        dice4.setImage(diceImage);
                    else
                        dice5.setImage(diceImage);
                    break;
                case 6:
                    diceImage = new Image(getClass().getResourceAsStream("dice/6.png"));
                    if (i == 0)
                        dice0.setImage(diceImage);
                    else if (i == 1)
                        dice1.setImage(diceImage);
                    else if (i == 2)
                        dice2.setImage(diceImage);
                    else if (i == 3)
                        dice3.setImage(diceImage);
                    else if (i == 4)
                        dice4.setImage(diceImage);
                    else
                        dice5.setImage(diceImage);
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }
    }

    public void generateDice() {
        DiceThrower.generateDice();
    }

    private static class DiceThrower {

        private final static int m_nDice = 6;
        private final static Die[] m_Dice = new Die[m_nDice];
        private static int m_RollCount;

        private static void generateDice() {
            for (int i = 0; i < m_nDice; i++)
                m_Dice[i] = new DiceThrower.Die();
        }

        private static void resetRollCount() {
            m_RollCount = 0;
        }

        private static void roll() {
            Arrays.stream(m_Dice).filter(die -> !die.m_IsLocked).forEach(Die::roll);
            m_RollCount++;
        }

        private static void lock(int i) {
            m_Dice[i].setLocked();
        }

        private static int calculatePoints() {
            int points = 0;
            // This class is not implemented
            return points;
        }

        private static class Die {

            private static final Random rand = new Random();
            private int m_FaceValue;
            private boolean m_IsLocked = false;

            private void setLocked() {
                m_IsLocked = !m_IsLocked;
            }

            private void roll() {
                m_FaceValue = rand.nextInt(6) + 1;
            }
        }
    }

}

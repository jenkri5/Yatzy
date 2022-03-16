package dk.jenskristensen.yatzy;

import javafx.fxml.FXML;
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
        DiceThrower.lock(0);
    }

    public void lockTwo() {
        DiceThrower.lock(1);
    }

    public void lockThree() {
        DiceThrower.lock(2);
    }

    public void lockFour() {
        DiceThrower.lock(3);
    }

    public void lockFive() {
        DiceThrower.lock(4);
    }

    public void lockSix() {
        DiceThrower.lock(5);
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
        private static Die[] m_Dice = new Die[m_nDice];
        private static int m_RollCount;

        private static void generateDice() {
            for (int i = 0; i < m_nDice; i++)
                m_Dice[i] = new DiceThrower.Die();
        }

        private static void resetRollCount() {
            m_RollCount = 0;
        }

        private static void roll() {
            Arrays.stream(m_Dice).filter(die -> !die.isLocked()).forEach(Die::roll);
            m_RollCount++;
            System.out.println(m_RollCount);
        }

        private static void sort() {
            m_Dice = Arrays.stream(m_Dice)
                    .sorted(Comparator.comparing(Die::isLocked).reversed().thenComparingInt(DiceThrower.Die::getFaceValue))
                    .toArray(Die[]::new);
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

            private int getFaceValue() {
                return m_FaceValue;
            }

            private void setLocked() {
                m_IsLocked = !m_IsLocked;
            }

            private boolean isLocked() {
                return m_IsLocked;
            }

            private void roll() {
                m_FaceValue = rand.nextInt(6) + 1;
            }
        }
    }

}

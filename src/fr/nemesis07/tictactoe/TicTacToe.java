package fr.nemesis07.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe implements ActionListener {

    private Random random = new Random();
    private JFrame frame = new JFrame();
    private JPanel title_panel = new JPanel();
    private JPanel button_panel = new JPanel();
    private JLabel textField = new JLabel();
    private JButton[] buttons = new JButton[9];
    private int[] buttonState = new int[9]; //0 = null, 1 = X, 2 = O
    private boolean playerOneTurn;
    private boolean started = false;

    private int clickCount;


    TicTacToe() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(25, 25, 25));

        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(new Font("Monospaced", Font.BOLD, 50));
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.getBounds(new Rectangle(0, 0, 800, 100));

        button_panel.setLayout(new GridLayout(3,3));
        button_panel.setBackground(new Color(150, 150, 150));
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("Monospaced", Font.BOLD, 100));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        title_panel.add(textField);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
        firstTurn();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!started) return;
        clickCount++;
        for (int i = 0; i < buttons.length; i++) {
            if(e.getSource()!=buttons[i]) continue;
            if(buttons[i].getText()=="") {
                if(playerOneTurn) {
                    buttons[i].setForeground(new Color(255, 25, 25));
                    buttons[i].setText("❌");
                    buttonState[i] = 1;

                    textField.setForeground(new Color(25, 25, 255));
                    textField.setText("Tour de ⭘");
                } else {
                    buttons[i].setForeground(new Color(25, 25, 255));
                    buttons[i].setText("⭘");
                    buttonState[i] = 2;

                    textField.setForeground(new Color(255, 25, 25));
                    textField.setText("Tour de ❌");
                }
                playerOneTurn = !playerOneTurn;
                check();
            }
        }

        if(clickCount >= 9) {
            textField.setForeground(new Color(25, 255, 0));
            textField.setText("Égalité");
            return;
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(2500);
            started = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(random.nextInt(2) == 0) {
            playerOneTurn = true;
            textField.setForeground(new Color(255, 25, 25));
            textField.setText("Tour de ❌");
        } else {
            playerOneTurn = false;
            textField.setForeground(new Color(25, 25, 255));
            textField.setText("Tour de ⭘");
        }
    }

    public void check() {
        checkRow();
        checkCol();
        checkDiag();
    }

    private void checkDiag() {
        if(buttonState[0] == 1 && buttonState[4] == 1 && buttonState[8] == 1)
            win(buttonState[0], 0, 4, 8);
        if(buttonState[2] == 1 && buttonState[4] == 1 && buttonState[6] == 1)
            win(buttonState[2], 2, 4, 6);

        if(buttonState[0] == 2 && buttonState[4] == 2 && buttonState[8] == 2)
            win(buttonState[0], 0, 4, 8);
        if(buttonState[2] == 2 && buttonState[4] == 2 && buttonState[6] == 2)
            win(buttonState[2], 2, 4, 6);
    }

    public void checkRow() {
        for (int i = 0; i < 9; i=i+3) {
            if(buttonState[i] == 1 && buttonState[i+1] == 1 && buttonState[i+2] == 1)
                win(buttonState[i], i, i+1, i+2);
            else if(buttonState[i] == 2 && buttonState[i+1] == 2 && buttonState[i+2] == 2)
                win(buttonState[i], i, i+1, i+2);
        }
    }

    public void checkCol() {
        for (int i = 0; i < 3; i++) {
            if(buttonState[i] == 1 && buttonState[i+3] == 1 && buttonState[i+6] == 1) {
                win(buttonState[i], i, i+3, i+6);
            } else if(buttonState[i] == 2 && buttonState[i+3] == 2 && buttonState[i+6] == 2) {
                win(buttonState[i], i, i+3, i+6);
            }
        }
    }

    public void win(int winner, int b1, int b2, int b3) {
        buttons[b1].setBackground(Color.GREEN);
        buttons[b2].setBackground(Color.GREEN);
        buttons[b3].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }

        textField.setForeground(new Color(25, 255, 0));
        if(winner == 1)
            textField.setText("Victoire de ❌");
        else if(winner == 2)
            textField.setText("Victoire de ⭘");
        else textField.setText("ERROR");
        return;
    }


}

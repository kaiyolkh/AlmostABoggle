/*==============================================================================================================================================================
2D Array GUI Search Assignment
Name: Kyle Lam
Course: ICS4U1
Date: March 26th, 2021
================================================================================================================================================================
Problem Definition - Create a program to determine if the user input word exists in the 2D string array generated and in the dictionary.
Input - User enters a random word (String input), checks if the input is valid in the 2D string array generated and in the dictionary.
Output - Tells the user if their input word exists in the dictionary and in the 2D string array generated
Process - Generate 2D array with random number generator to determine the vowels and consonants
          Go through and create a string for each horizontal, vertical, and diagonal of the generated 2D array, then add each string into an arraylist
          Read the 'wordlist.txt' file with scanner and add each line of content into a hashset
          Go through each line of the arraylist and hashset to look for matching strings
================================================================================================================================================================
 */
package boggle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Boggle{
    //universal variables/gui elements declaration
    static final int limit = 6;
    static JFrame jf = new JFrame();
    static JPanel jp = new JPanel();
    static JLabel[][]jlGrid = new JLabel[limit][limit];
    static JLabel title, jl, jl2, LSOutput, GSOutput;
    static JTextField jtf = new JTextField(30);
    static JButton jb = new JButton("Enter");

    static Scanner sc = new Scanner (System.in);//universal scanner function declaration
    static char grid[][] = new char[limit][limit];//2d array declaration

    public Boggle() {//constructor boggle for GUI
        //Setting up the GUI framework
        jf.setTitle("Kyle Lam - Boggle Word Search");
        jp.setLayout(null);
        jp.revalidate();
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Setting up label elements
        title = new JLabel();
        jl = new JLabel();
        jl2 = new JLabel();
        LSOutput = new JLabel();
        GSOutput = new JLabel();
        //Setting up title text
        title.setText("Almost a Boggle!");
        title.setFont(new Font("Ariel", Font.BOLD, 30));
        title.setBounds(20, 8, 600, 50);
        jp.add(title);
        //Setting up instruction text
        jl.setText("Enter your word guesses in the text box below to search. Press the 'Enter' button on the screen or your keyboard to proceed.");
        jl.setBounds(20, 50, 750, 25);
        jp.add(jl);
        //Setting up text box
        jtf.setBounds(20, 80, 200, 25);
        jp.add(jtf);
        jtf.addActionListener (new ActionListener() {//ActionListener method to tell the program what to do when enter key is pressed
            public void actionPerformed(ActionEvent e){
                String input = jtf.getText();
                UserInput(input);
            }
        });
        //Setting up the enter button
        jb.setBounds(240, 80, 100, 24);
        jp.add(jb);
        jb.addActionListener (new ActionListener() {//ActionListener method to tell the program what to do when enter button is pressed
           public void actionPerformed(ActionEvent e){
               String input = jtf.getText();
               UserInput(input);
           }
        });
        //Setting up result outputs
        GSOutput.setBounds(20, 395, 300, 25);
        jp.add(GSOutput);
        LSOutput.setBounds(20, 410, 300, 25);
        jp.add(LSOutput);

        //Configure and finalize the implementation of GUI
        jf.add(jp);
        jf.setVisible(true);
        jf.pack();
        jf.setSize(800, 600);
    }

    public static void main (String args[]) {//main method
        //Call constructor 'Boggle' to output GUI
        Boggle gui = new Boggle();
        //call the following methods:
        GenerateGrid(grid);
    }
    public static void GenerateGrid(char grid[][]) {//assignValue method declaration, calls 2d array to assign a value for each index
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        String vowels = "AEIOU";
        Random rand = new Random();//randomizer declaration

        for (int i=0;i<limit;i++) {//for loop to initialize/fill the array with random alphabets
            for (int j=0;j<limit;j++) {
                if (rand.nextInt(3) == 0) {//ensure vowels are assigned to at most 1/3 of the entire 2d array
                    grid[i][j] = vowels.charAt(rand.nextInt(vowels.length()));
                }
                else {//assign consonants for the remaining indices of the 2d array
                    grid[i][j] = consonants.charAt(rand.nextInt(consonants.length()));
                }
            }
        }
        //Calls the Display method to display the generated grid
        Display(jlGrid, grid);
    }//end main method
    public static void Display(JLabel jlGrid[][], char grid[][]) {//Display method declaration, calls 2d array to be displayed
        for (int i=0;i<limit;i++) {//proper display format
            for (int j=0;j<limit;j++) {
                jlGrid[i][j] = new JLabel(String.valueOf(grid[j][i]));//convert 2d array into GUI display
                jlGrid[i][j].setFont(new Font("Ariel", Font.BOLD, 30));
                jlGrid[i][j].setBounds((400 + i * 50), (150 + j * 50), 30, 30);
                jp.add(jlGrid[i][j]);

            }
        }
    }//end Display method
    public void UserInput(String input) {//userInput method declaration
        //Calls the following methods:
        gridSplit(grid, input);
        listSearch(input);
    }
    public void gridSplit (char grid[][], String input) {//gridSplit method declaration, calls 2d array and input to process the calculation
        ArrayList<String> gridList = new ArrayList();//Declare arraylist to hold every line (horizontal, vertical, diagonal) of the grid
        //Declare and initialize the strings
        String h1, h2, h3, h4, h5, h6, h1r, h2r, h3r, h4r, h5r, h6r;
        h1 = h2 = h3 = h4 = h5 = h6 = h1r = h2r = h3r = h4r = h5r = h6r = "";
        String v1, v2, v3, v4, v5, v6, v1r, v2r, v3r, v4r, v5r, v6r;
        v1 = v2 = v3 = v4 = v5 = v6 = v1r = v2r = v3r = v4r = v5r = v6r = "";
        StringBuilder dtl1, dtl2, dtl3, dtl4, dtl5, dtl6, dtl2r, dtl3r, dtl4r, dtl5r, dtl6r;
        dtl1 = dtl2 = dtl3 = dtl4 = dtl5 = dtl6 = dtl2r = dtl3r = dtl4r = dtl5r = dtl6r = new StringBuilder();
        StringBuilder dtr1, dtr2, dtr3, dtr4, dtr5, dtr6, dtr2r, dtr3r, dtr4r, dtr5r, dtr6r;
        dtr1 = dtr2 = dtr3 = dtr4 = dtr5 = dtr6 = dtr2r = dtr3r = dtr4r = dtr5r = dtr6r = new StringBuilder();
        StringBuilder dbl1, dbl2, dbl3, dbl4, dbl5, dbl2r, dbl3r, dbl4r, dbl5r;
        dbl1 = dbl2 = dbl3 = dbl4 = dbl5 = dbl2r = dbl3r = dbl4r = dbl5r = new StringBuilder();
        StringBuilder dbr1, dbr2, dbr3, dbr4, dbr5, dbr2r, dbr3r, dbr4r, dbr5r;
        dbr1 = dbr2 = dbr3 = dbr4 = dbr5 = dbr2r = dbr3r = dbr4r = dbr5r = new StringBuilder();
        //declare and initialize local variables to perform for loops
        int hRow, vCol;
        hRow = vCol = 0;
        //add horizontal rows into individual strings
        for (int hCol = 0; hCol < grid.length; hCol++) {
            h1 += grid[hRow][hCol];
            h2 += grid[hRow + 1][hCol];
            h3 += grid[hRow + 2][hCol];
            h4 += grid[hRow + 3][hCol];
            h5 += grid[hRow + 4][hCol];
            h6 += grid[hRow + 5][hCol];
        }
        //add reversed horizontal rows into individual strings
        for (int hCol = grid.length - 1; hCol >= 0; hCol--) {
            h1r += grid[hRow][hCol];
            h2r += grid[hRow + 1][hCol];
            h3r += grid[hRow + 2][hCol];
            h4r += grid[hRow + 3][hCol];
            h5r += grid[hRow + 4][hCol];
            h6r += grid[hRow + 5][hCol];
        }
        //add vertical rows into individual strings
        for (int vRow = 0; vRow < grid.length; vRow++) {
            v1 += grid[vRow][vCol];
            v2 += grid[vRow][vCol + 1];
            v3 += grid[vRow][vCol + 2];
            v4 += grid[vRow][vCol + 3];
            v5 += grid[vRow][vCol + 4];
            v6 += grid[vRow][vCol + 5];
        }
        //add reversed vertical rows into individual strings
        for (int vRow = grid.length - 1; vRow >= 0; vRow--) {
            v1r += grid[vRow][vCol];
            v2r += grid[vRow][vCol + 1];
            v3r += grid[vRow][vCol + 2];
            v4r += grid[vRow][vCol + 3];
            v5r += grid[vRow][vCol + 4];
            v6r += grid[vRow][vCol + 5];
        }
        //the for loops below add all diagonals and reversed diagonals into individual strings
        for (int dRow = 0, dCol = grid.length - 6; dRow < grid.length-5 && dCol >= 0; dRow++, dCol--){
            dtl1.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = grid.length - 5; dRow < grid.length-4 && dCol >= 0; dRow++, dCol--){
            dtl2.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = grid.length - 4; dRow < grid.length-3 && dCol >= 0; dRow++, dCol--) {
            dtl3.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = grid.length - 3; dRow < grid.length-2 && dCol >= 0; dRow++, dCol--) {
            dtl4.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = grid.length - 2; dRow < grid.length-1 && dCol >= 0; dRow++, dCol--) {
            dtl5.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = grid.length - 1; dRow < grid.length && dCol >= 0; dRow++, dCol--){
            dtl6.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 5, dCol = 0; dRow >= 0 && dCol < grid.length-4; dRow--, dCol++){
            dtl2r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 4, dCol = 0; dRow >= 0 && dCol < grid.length-3; dRow--, dCol++){
            dtl3r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 3, dCol = 0; dRow >= 0 && dCol < grid.length-2; dRow--, dCol++){
            dtl4r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 2, dCol = 0; dRow >= 0 && dCol < grid.length-1; dRow--, dCol++){
            dtl5r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 0; dRow >= 0 && dCol < grid.length; dRow--, dCol++){
            dtl6r.append(grid[dRow][dCol]);
        }
        for (int dRow = 5, dCol = grid.length - 1; dRow < grid.length && dCol > 4; dRow++, dCol--){
            dbr1.append(grid[dRow][dCol]);
        }
        for (int dRow = 4, dCol = grid.length - 1; dRow < grid.length && dCol > 3; dRow++, dCol--){
            dbr2.append(grid[dRow][dCol]);
        }
        for (int dRow = 3, dCol = grid.length - 1; dRow < grid.length && dCol > 2; dRow++, dCol--){
            dbr3.append(grid[dRow][dCol]);
        }
        for (int dRow = 2, dCol = grid.length - 1; dRow < grid.length && dCol > 1; dRow++, dCol--){
            dbr4.append(grid[dRow][dCol]);
        }
        for (int dRow = 1, dCol = grid.length - 1; dRow < grid.length && dCol > 0; dRow++, dCol--){
            dbr5.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 4; dRow > 3 && dCol < grid.length; dRow--, dCol++){
            dbr2r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 3; dRow > 2 && dCol < grid.length; dRow--, dCol++){
            dbr3r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 2; dRow > 1 && dCol < grid.length; dRow--, dCol++){
            dbr4r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 1; dRow > 0 && dCol < grid.length; dRow--, dCol++){
            dbr5r.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = 5; dRow < grid.length - 5 && dCol < grid.length; dRow++, dCol++){
            dtr1.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = 4; dRow < grid.length - 4 && dCol < grid.length; dRow++, dCol++){
            dtr2.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = 3; dRow < grid.length - 3 && dCol < grid.length; dRow++, dCol++){
            dtr3.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = 2; dRow < grid.length - 2 && dCol < grid.length; dRow++, dCol++){
            dtr4.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = 1; dRow < grid.length - 1 && dCol < grid.length; dRow++, dCol++){
            dtr5.append(grid[dRow][dCol]);
        }
        for (int dRow = 0, dCol = 0; dRow < grid.length && dCol < grid.length; dRow++, dCol++){
            dtr6.append(grid[dRow][dCol]);
        }
        for (int dRow = 1, dCol = grid.length - 1; dRow >= 0 && dCol >= 4; dRow--, dCol--){
            dtr2r.append(grid[dRow][dCol]);
        }
        for (int dRow = 2, dCol = grid.length - 1; dRow >= 0 && dCol >= 3; dRow--, dCol--){
            dtr3r.append(grid[dRow][dCol]);
        }
        for (int dRow = 3, dCol = grid.length - 1; dRow >= 0 && dCol >= 2; dRow--, dCol--){
            dtr4r.append(grid[dRow][dCol]);
        }
        for (int dRow = 4, dCol = grid.length - 1; dRow >= 0 && dCol >= 1; dRow--, dCol--){
            dtr5r.append(grid[dRow][dCol]);
        }
        for (int dRow = 5, dCol = grid.length - 1; dRow >= 0 && dCol >= 0; dRow--, dCol--){
            dtr6r.append(grid[dRow][dCol]);
        }
        for (int dRow = 5, dCol = 0; dRow < grid.length && dCol < 1; dRow++, dCol++) {
            dbl1.append(grid[dRow][dCol]);
        }
        for (int dRow = 4, dCol = 0; dRow < grid.length && dCol < 2; dRow++, dCol++) {
            dbl2.append(grid[dRow][dCol]);
        }
        for (int dRow = 3, dCol = 0; dRow < grid.length && dCol < 3; dRow++, dCol++) {
            dbl3.append(grid[dRow][dCol]);
        }
        for (int dRow = 2, dCol = 0; dRow < grid.length && dCol < 4; dRow++, dCol++) {
            dbl4.append(grid[dRow][dCol]);
        }
        for (int dRow = 1, dCol = 0; dRow < grid.length && dCol < 5; dRow++, dCol++) {
            dbl5.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 1; dRow > 3 && dCol >= 0; dRow--, dCol--) {
            dbl2r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 2; dRow > 2 && dCol >= 0; dRow--, dCol--) {
            dbl3r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 3; dRow > 1 && dCol >= 0; dRow--, dCol--) {
            dbl4r.append(grid[dRow][dCol]);
        }
        for (int dRow = grid.length - 1, dCol = 4; dRow > 0 && dCol >= 0; dRow--, dCol--) {
            dbl5r.append(grid[dRow][dCol]);
        }
        //add all strings into the arraylist
        gridList.add(h1);
        gridList.add(h2);
        gridList.add(h3);
        gridList.add(h4);
        gridList.add(h5);
        gridList.add(h6);
        gridList.add(h1r);
        gridList.add(h2r);
        gridList.add(h3r);
        gridList.add(h4r);
        gridList.add(h5r);
        gridList.add(h6r);
        gridList.add(v1);
        gridList.add(v2);
        gridList.add(v3);
        gridList.add(v4);
        gridList.add(v5);
        gridList.add(v6);
        gridList.add(v1r);
        gridList.add(v2r);
        gridList.add(v3r);
        gridList.add(v4r);
        gridList.add(v5r);
        gridList.add(v6r);
        gridList.add(dtl1.toString());
        gridList.add(dtl2.toString());
        gridList.add(dtl3.toString());
        gridList.add(dtl4.toString());
        gridList.add(dtl5.toString());
        gridList.add(dtl6.toString());
        gridList.add(dtl2r.toString());
        gridList.add(dtl3r.toString());
        gridList.add(dtl4r.toString());
        gridList.add(dtl5r.toString());
        gridList.add(dtl6r.toString());
        gridList.add(dbr1.toString());
        gridList.add(dbr2.toString());
        gridList.add(dbr3.toString());
        gridList.add(dbr4.toString());
        gridList.add(dbr5.toString());
        gridList.add(dbr2r.toString());
        gridList.add(dbr3r.toString());
        gridList.add(dbr4r.toString());
        gridList.add(dbr5r.toString());
        gridList.add(dtr1.toString());
        gridList.add(dtr2.toString());
        gridList.add(dtr3.toString());
        gridList.add(dtr4.toString());
        gridList.add(dtr5.toString());
        gridList.add(dtr6.toString());
        gridList.add(dtr2r.toString());
        gridList.add(dtr3r.toString());
        gridList.add(dtr4r.toString());
        gridList.add(dtr5r.toString());
        gridList.add(dtr6r.toString());
        gridList.add(dbl1.toString());
        gridList.add(dbl2.toString());
        gridList.add(dbl3.toString());
        gridList.add(dbl4.toString());
        gridList.add(dbl5.toString());
        gridList.add(dbl2r.toString());
        gridList.add(dbl3r.toString());
        gridList.add(dbl4r.toString());
        gridList.add(dbl5r.toString());
        //Calls the following method to search for matching elements/strings
        gridSearch(gridList, input);
    }//end gridSplit method
    public void gridSearch(ArrayList<String> gridList, String input){//gridSearch method declaration
        boolean valid = false;//searches through the arraylist to find matching strings
        for(int i=0; i<gridList.size(); i++) {
            if (gridList.get(i).contains(input.toUpperCase())) {//boolean becomes true when matching string/element is found
                valid = true;
            }
        }
        //Calls the following method to output result
        gridSearchOutput(valid, input);
    }//end gridSearch method
    public void listSearch (String input) {//listSearch method declaration
        HashSet<String> wordList = new HashSet<String>(); //declare HashSet wordlist
        try {//try catch function and scanner to locate and read the target file (wordlist.txt)
            Scanner file = new Scanner(new File("wordlist.txt"));
            while (file.hasNextLine()) {//incorporate every line of words from the text file into the HashSet list
                wordList.add(file.nextLine());
            }
            file.close();//stop reading the file
        } catch (FileNotFoundException e) {//catch if the required wordlist text file is not found
            System.out.println("Destination file does not exist. 'wordlist.txt' is required for this program to run. Please check your directories.");
        }
        //Calls the following method to output result
        listSearchOutput(wordList, input);
    }
    public void gridSearchOutput(boolean valid, String input){
        if (valid != true){//if boolean passed down is true (the grid has the input word)
            GSOutput.setText("The word '"+input+"' does not exist on the grid");
        }
        else{//if the grid doesn't have the input word
            GSOutput.setText("The word '"+input+"' exists on the grid");
        }
    }
    public void listSearchOutput (HashSet<String> wordList, String input) {
        if (wordList.contains(input.toLowerCase())) {//searches if the wordlist has the input word
            LSOutput.setText("The word '"+input+"' is a valid word");
        }
        else {//if the wordlist doesn't have the input word
            LSOutput.setText("The word '"+input+"' is not a valid word");
        }
    }//end listSearchOutput method
}//end Boggle class
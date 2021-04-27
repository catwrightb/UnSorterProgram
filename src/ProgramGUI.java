import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgramGUI extends JPanel implements ActionListener {

    private JLabel label,label2;
    private JButton button, queueTask, runTask;
    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JTextField gapValueInput;
    private Queue<UnSortListAlgorithm> fileSorterQueue = new LinkedList<>();
    private JLabel filesQueued;
    private String gap;
    private JLabel text = new JLabel();


    public ProgramGUI() {

        super(new GridLayout(4,1));

        panel1 = new JPanel();
        filesQueued = new JLabel();
        filesQueued.setText("Queued task: "+ fileSorterQueue.size());
        panel1.add(filesQueued);

        panel2 = new JPanel(new GridLayout(2,1));
        filesQueued.setHorizontalAlignment(SwingConstants.CENTER);
        label2 = new JLabel("Current gap is: "+ gap);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(label2);

        panel3 = new JPanel();
        label = new JLabel("Gap: ");
        gapValueInput = new JTextField(10);
        button = new JButton("Enter");
        button.addActionListener(this);
        panel3.add(label);
        panel3.add(gapValueInput);
        panel3.add(button);


        panel4 = new JPanel(new GridLayout(1,2));
        queueTask = new JButton("Queue task");
        queueTask.addActionListener(this);
        panel4.add(queueTask);
        runTask = new JButton("Run Task");
        runTask.addActionListener(this);
        panel4.add(runTask);


        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button){

            if (gapValueInput.getText().equals("0")){
                JOptionPane.showMessageDialog(null, "Please enter a number gap value that is NOT zero", "Gap Not Zero", JOptionPane.PLAIN_MESSAGE);
                gapValueInput.setText("");
            }
            else if (!gapValueInput.getText().equals("0")){
                Pattern pattern = Pattern.compile("[^0-9]");
                Matcher matcher = pattern.matcher(gapValueInput.getText());
                boolean isStringContainsSpecialCharacter = matcher.find();

                if(isStringContainsSpecialCharacter){
                    //if special chars are present remove them and return
                    JOptionPane.showMessageDialog(null, "Please enter a number gap value", "Gap Value needed", JOptionPane.PLAIN_MESSAGE);
                    gapValueInput.setText("");
                }
                else {
                    gap = gapValueInput.getText();
                    label2.setText("Current stringLimit is: "+ gap);
                }
            }


        }

        if (e.getSource() == queueTask) {

            if (gap == null) {
                JOptionPane.showMessageDialog(null, "Please enter a Gap Value", "Gap needed", JOptionPane.PLAIN_MESSAGE);

            }
            else if (gap != null) {

                JFileChooser fileInput = new JFileChooser();

                fileInput.setCurrentDirectory(new java.io.File("./resources/Sorted"));
                int result = fileInput.showOpenDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {

                }
                File selectedFile = fileInput.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());

//                JFileChooser fileOutput = new JFileChooser();
//                fileOutput.setCurrentDirectory(new java.io.File("./resources/UnSorted"));
//                int result2 = fileInput.showOpenDialog(this);
//
//                if (result2== JFileChooser.APPROVE_OPTION) {
//
//                }
//                File selectedFileOutput = fileInput.getSelectedFile();
//                System.out.println("Selected file: " + selectedFileOutput.getAbsolutePath());


                    int limit = Integer.parseInt(gap);
                    UnSortListAlgorithm newList = new UnSortListAlgorithm(limit, selectedFile);
                    fileSorterQueue.add(newList);
                    filesQueued.setText("Queued task: "+ fileSorterQueue.size());

            }
        }

        if (e.getSource() == runTask){

            if (fileSorterQueue.size() > 0 ){
                while( fileSorterQueue.size() > 0 ){
                    runTask.setEnabled(false);
                    queueTask.setEnabled(false);
                    UnSortListAlgorithm file = fileSorterQueue.poll();
                    file.run();
                    filesQueued.setText("Queued task: "+ fileSorterQueue.size());
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Nothing Queued", "nothing queued", JOptionPane.PLAIN_MESSAGE);
            }

            if (fileSorterQueue.size() == 0) {
                runTask.setEnabled(true);
                queueTask.setEnabled(true);
                gapValueInput.setText("");
                gap = "0";
                label2.setText("Current stringLimit is: "+ gap);
            }
        }
    }



    public static void main(String[] args) {
        ProgramGUI myPanel = new ProgramGUI();
        JFrame frame = new JFrame("File Sorter Program"); //create frame to hold our JPanel subclass
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(myPanel);  //add instance of MyGUI to the frame

        frame.setSize(300,200); //resize frame to fit the Jpanel
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width / 2) - (frame.getWidth() / 2), (d.height / 2) - (frame.getHeight() / 2)));
        //show the frame
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Student {
    String name;
    int[] marks = new int[3];
    int total;
    int highest;
    int lowest;
    double average;

    Student(String name, int[] marks) {
        this.name = name;
        this.marks = marks;
        calculateStats();
    }

    void calculateStats() {
        total = 0;
        highest = marks[0];
        lowest = marks[0];

        for (int mark : marks) {
            total += mark;
            if (mark > highest) highest = mark;
            if (mark < lowest) lowest = mark;
        }

        average = (double) total / marks.length;
    }

    public String getSummary() {
        return name + " → " +
               "Marks: " + Arrays.toString(marks) +
               " | Total: " + total +
               " | Avg: " + String.format("%.2f", average) +
               " | Highest: " + highest +
               " | Lowest: " + lowest;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    ArrayList<Student> studentList = new ArrayList<>();

    JTextField nameField, m1Field, m2Field, m3Field;
    JTextArea summaryArea;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker (Multi-subject)");
        setSize(600, 550);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Input Fields
        nameField = new JTextField(15);
        m1Field = new JTextField(5);
        m2Field = new JTextField(5);
        m3Field = new JTextField(5);

        JButton addButton = new JButton("Add Student");
        JButton showButton = new JButton("Show Summary");
        summaryArea = new JTextArea(18, 50);
        summaryArea.setEditable(false);

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("English:")); add(m1Field);
        add(new JLabel("Math:")); add(m2Field);
        add(new JLabel("Science:")); add(m3Field);
        add(addButton); add(showButton);
        add(new JScrollPane(summaryArea));

        // Add Student Logic
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int m1 = Integer.parseInt(m1Field.getText().trim());
                int m2 = Integer.parseInt(m2Field.getText().trim());
                int m3 = Integer.parseInt(m3Field.getText().trim());

                if (m1 < 0 || m1 > 100 || m2 < 0 || m2 > 100 || m3 < 0 || m3 > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.");
                    return;
                }

                int[] marks = {m1, m2, m3};
                studentList.add(new Student(name, marks));
                JOptionPane.showMessageDialog(this, "Student Added!");

                // Clear input
                nameField.setText(""); m1Field.setText(""); m2Field.setText(""); m3Field.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks.");
            }
        });

        // Show Summary Logic
        showButton.addActionListener(e -> {
            if (studentList.isEmpty()) {
                summaryArea.setText("No students added.");
                return;
            }

            StringBuilder sb = new StringBuilder("------ Student Summary ------\n\n");

            double classTotal = 0;
            int totalSubjects = 0;
            int highestScore = studentList.get(0).marks[0];
            int lowestScore = studentList.get(0).marks[0];
            String highestScorer = studentList.get(0).name;
            String lowestScorer = studentList.get(0).name;

            for (Student s : studentList) {
                sb.append(s.getSummary()).append("\n");

                for (int mark : s.marks) {
                    classTotal += mark;
                    totalSubjects++;

                    if (mark > highestScore) {
                        highestScore = mark;
                        highestScorer = s.name;
                    }

                    if (mark < lowestScore) {
                        lowestScore = mark;
                        lowestScorer = s.name;
                    }
                }
            }

            double classAverage = classTotal / totalSubjects;

            sb.append("\n------ Class Summary ------\n");
            sb.append("Overall Highest Score: ").append(highestScore).append(" (").append(highestScorer).append(")\n");
            sb.append("Overall Lowest Score: ").append(lowestScore).append(" (").append(lowestScorer).append(")\n");
            sb.append("Class Average: ").append(String.format("%.2f", classAverage)).append("\n");

            summaryArea.setText(sb.toString());
        });
    }

    public static void main(String[] args) {
        new StudentGradeTrackerGUI().setVisible(true);
    }
}
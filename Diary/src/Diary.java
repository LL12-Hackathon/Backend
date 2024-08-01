import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Diary extends JFrame {

    private JTextArea diaryEntry;
    private JList<String> diaryList;
    private DefaultListModel<String> listModel;

    public Diary() {
        setTitle("Diary");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Diary entry area
        diaryEntry = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(diaryEntry);
        add(scrollPane, BorderLayout.CENTER);

        // Diary list
        listModel = new DefaultListModel<>();
        diaryList = new JList<>(listModel);
        diaryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(diaryList);
        listScrollPane.setPreferredSize(new Dimension(200, 600));
        add(listScrollPane, BorderLayout.EAST);

        // Button to save diary entry
        JButton saveButton = new JButton("Save Entry");
        saveButton.addActionListener(new SaveButtonListener());
        add(saveButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String entry = diaryEntry.getText();
            if (!entry.trim().isEmpty()) {
                listModel.addElement(entry);
                diaryEntry.setText("");
            } else {
                JOptionPane.showMessageDialog(Diary.this, "Diary entry cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Diary());
    }
}

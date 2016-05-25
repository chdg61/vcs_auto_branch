package ru.chdg61;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.awt.*;

public class SettingsPage implements Configurable {

    public static String patternDefault = "^([\\d]+?)\\_.*";
    public static String messageOutputDefault = "#$1, ";

    private JTextField outputField;
    private JTextField patternBranch;

    private Project project;

    public SettingsPage(Project project) {
        this.project = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "VCS Auto Branch";
    }

    @Override
    public JComponent createComponent() {

        PropertiesComponent properties = PropertiesComponent.getInstance(project);

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        //strut
        panel.add(Box.createVerticalStrut(8));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

        panel.add(panel2);

        //regex pattern

        JPanel patternPanel = new JPanel();
        patternPanel.setLayout(new BorderLayout());
        patternPanel.setMaximumSize(new Dimension(5000, 25));

        JLabel labelPatternBranch = new JLabel("RegEx pattern branch:");
        labelPatternBranch.setSize(new Dimension(200, 20));
        patternBranch = new JTextField(15);
        labelPatternBranch.setLabelFor(patternBranch);
        patternBranch.setMaximumSize(new Dimension(500, 20));

        JPanel patternLabelPanel = new JPanel();
        patternLabelPanel.setPreferredSize(new Dimension(135, 25));
        patternLabelPanel.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        patternLabelPanel.add(labelPatternBranch);

        patternPanel.add(patternLabelPanel, BorderLayout.WEST);
        patternPanel.add(patternBranch, BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(8));
        panel.add(patternPanel);

        //message output

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setMaximumSize(new Dimension(5000, 25));

        outputField = new JTextField(15);
        outputField.setMaximumSize(new Dimension(500, 20));


        JLabel outputLabel = new JLabel("Message output:");
        outputLabel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        outputLabel.setSize(new Dimension(200, 20));
        outputLabel.setLabelFor(outputField);


        JPanel outputLabelPanel = new JPanel();
        outputLabelPanel.setPreferredSize(new Dimension(135, 25));
        outputLabelPanel.add(outputLabel);

        outputPanel.add(outputLabelPanel, BorderLayout.WEST);
        outputPanel.add(outputField, BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(8));
        panel.add(outputPanel);


        outputField.setText(properties.getValue("message.output", messageOutputDefault));
        patternBranch.setText(properties.getValue("pattern", patternDefault));

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent properties = PropertiesComponent.getInstance(project);
        properties.setValue("message.output", outputField.getText());
        properties.setValue("pattern", patternBranch.getText());
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public void disposeUIResources() {

    }

    @Override
    public void reset() {

    }
}

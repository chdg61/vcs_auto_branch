package ru.chdg61;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VCSAutoBranchProjectComponent extends AbstractProjectComponent {

    private PropertiesComponent properties;

    public VCSAutoBranchProjectComponent(Project project) {
        super(project);
        this.properties = PropertiesComponent.getInstance(project);
    }

    public static VCSAutoBranchProjectComponent getInstance(Project project) {
        return project.getComponent(VCSAutoBranchProjectComponent.class);
    }

    public Boolean checkBranchNameOnPattern(String branchName){
        Matcher m = getMatcherPatternBranch(branchName);
        return m.matches();
    }

    public String findOutputMessageForBranchName(String branchName) {
        Matcher m = getMatcherPatternBranch(branchName);
        String outputMessage = properties.getValue("message.output", SettingsPage.messageOutputDefault);
        Integer i = 0;
        while (m.find()){
            i++;
            String mather = m.group(1);
            String replaceNumber = "$" + i;
            outputMessage = outputMessage.replace(replaceNumber, mather);
        }

        return outputMessage;
    }

    @NotNull
    private Matcher getMatcherPatternBranch(String branchName) {
        String patternProperty = properties.getValue("pattern", SettingsPage.patternDefault);
        Pattern p = Pattern.compile(patternProperty);
        return p.matcher(branchName);
    }
}

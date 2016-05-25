package ru.chdg61.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.ui.Refreshable;
import git4idea.branch.GitBranchUtil;
import git4idea.repo.GitRepository;
import org.jetbrains.annotations.Nullable;
import ru.chdg61.VCSAutoBranchProjectComponent;


public class InsertBranchNumberAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String branchName;
        CommitMessageI cm = getCheckinPanel(e);

        Project project = e.getProject();
        if (project != null && cm != null) {
            GitRepository repository = GitBranchUtil.getCurrentRepository(project);
            if (repository != null) {
                branchName = GitBranchUtil.getBranchNameOrRev(repository);

                VCSAutoBranchProjectComponent component = VCSAutoBranchProjectComponent.getInstance(project);
                if (component.checkBranchNameOnPattern(branchName)) {
                    String message = component.findOutputMessageForBranchName(branchName);
                    cm.setCommitMessage(message);
                }
            }
        }
    }

    @Nullable
    private static CommitMessageI getCheckinPanel(@Nullable AnActionEvent e) {
        if (e == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
        if (data instanceof CommitMessageI) {
            return (CommitMessageI) data;
        }
        CommitMessageI commitMessageI = VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.getDataContext());
        if (commitMessageI != null) {
            return commitMessageI;
        }
        return null;
    }
}

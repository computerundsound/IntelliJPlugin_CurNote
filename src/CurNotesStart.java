import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class CurNotesStart extends AnAction {

    private static String curNotesFileName = ".curNotes.txt";
    private Project project;

    @Override
    public void actionPerformed(AnActionEvent event) {

        VirtualFile fileV;

        project = event.getData(PlatformDataKeys.PROJECT);

        fileV = buildVirtualFile();

        if (fileV != null) {
            FileEditorManager.getInstance(project).openFile(fileV, false);
        }

    }

    /**
     * @return String
     */
    @NotNull
    private String getFilePath() {
        return this.project.getBasePath() + File.separator + Project.DIRECTORY_STORE_FOLDER + File.separator + CurNotesStart.curNotesFileName;
    }

    /**
     * @return VirtualFile
     */
    private VirtualFile buildVirtualFile() {

        Boolean success = true;
        String filePath = getFilePath();
        File file = new File(filePath);
        VirtualFile fileVirtual = null;

        if (!file.exists()) {
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                success = false;
            }
        }

        if (success) {
            fileVirtual = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file);
        }

        String errorMessage = "An error has occurred. The VirtualFile could not be created. Please try again.";

        if (fileVirtual == null) {
            Messages.showMessageDialog(project, errorMessage, "Error", Messages.getErrorIcon());
        }

        return fileVirtual;
    }

}
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CurNoteStartBackup extends AnAction {

    private static String curNoteFileName = ".curNote.txt";
    private static String curNoteFilePath;

    public CurNoteStartBackup() {
//        super ("Text _Boxes");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        this.buildFilePath(project);

        String fileContent = this.getFileContent();

        Messages.showMessageDialog(project, fileContent, "Information", Messages.getInformationIcon());

    }

    private void buildFilePath(Project project) {

        //noinspection UnnecessaryLocalVariable
        String filePath = project.getBasePath() + File.separator + Project.DIRECTORY_STORE_FOLDER + File.separator + CurNoteStartBackup.curNoteFileName;
        CurNoteStartBackup.curNoteFilePath = filePath;

        try {
            this.createFileIfNotExist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeContentToFile(String content) {

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(CurNoteStartBackup.curNoteFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileWriter != null) {
            try {
                fileWriter.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getFileContent() {
        Path path = Paths.get(CurNoteStartBackup.curNoteFilePath);
        List<String> lines = new ArrayList<String>();
        String content = "";

        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {

            lines.add("No File found");
        }


        for (String element : lines) {
            content = content.concat(element);
        }

        return content;
    }

    private Boolean createFileIfNotExist() throws IOException {

        Boolean success = false;

        File file = new File(CurNoteStartBackup.curNoteFilePath);

        if (!file.exists()) {

            success = file.createNewFile();
        }

        return success;
    }
}

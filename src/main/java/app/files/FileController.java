package app.files;

import app.utils.TrimmedStringFactory;
import lombok.Getter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static app.Constants.MAX_ATTACHMENT_FILE_NAME_LENGTH;
import static app.Constants.MAX_RECEIVED_FILE_NAME_LENGTH;
import static app.gui.buttons.ButtonController.getButtonController;
import static app.gui.textFields.TextFieldController.getTextFieldController;
import static app.gui.texts.TextController.getTextController;
import static engine.scene.SceneBean.getScene;

public class FileController {

    private static FileController fileController;

    @Getter
    private FileData attachedFile;
    private final List<FileData> receivedFiles;

    private FileController() {
        this.receivedFiles = new ArrayList<>();

    }

    public static FileController getFileController() {
        if (fileController == null) {
            fileController = new FileController();
        }
        return fileController;
    }

    public void attachFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.attachedFile = new FileData(chooser.getSelectedFile());
            String trimmedFileName = TrimmedStringFactory.trimString(this.attachedFile.getName(), MAX_ATTACHMENT_FILE_NAME_LENGTH);
            getTextController().setFileName(trimmedFileName);
            getTextController().setFileExtension(this.attachedFile.getExtension());
            getTextController().setFileSize(this.attachedFile.getSize().toString());
            getTextController().setCurrentUploadInfoAsReadyToUpload();
            getScene().removeObject(getTextFieldController().getMessageTextField());
        }
    }

    public void detachFile() {
        this.attachedFile = null;
        getTextController().resetFileName();
        getTextController().resetFileExtension();
        getTextController().resetFileSize();
        getTextController().setCurrentUploadInfoAsBlank();
        getScene().addOnHighest(getTextFieldController().getMessageTextField());
    }

    public void addReceivedFile(FileData fileData) {
        this.receivedFiles.add(fileData);
        String trimmedFileName = TrimmedStringFactory.trimString(fileData.getName(), MAX_RECEIVED_FILE_NAME_LENGTH);
        String filenameWithExtension = trimmedFileName + " (" + fileData.getExtension() + ")";
        getButtonController().addReceivedFile(filenameWithExtension, fileData.hashCode());
    }

    public void downloadReceivedFile(int fileHashCode) {
        FileData fileToDownload = this.receivedFiles.stream()
                .filter(file -> file.hashCode() == fileHashCode)
                .findFirst()
                .orElseThrow();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String targetFile = fileToDownload.getName() + "." + fileToDownload.getExtension();
            String targetDirectory = chooser.getSelectedFile().getAbsolutePath();
            String targetPath = targetDirectory + "/" + targetFile;
            try (FileOutputStream outputStream = new FileOutputStream(targetPath)) {
                outputStream.write(fileToDownload.getBinaryFile());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateUploadInfo(double percentage) {
        getTextController().setCurrentUploadInfoAsProgress(percentage);
    }

}

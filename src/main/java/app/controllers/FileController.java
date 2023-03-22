package app.controllers;

import app.utils.TrimmedStringFactory;
import common.Serializer;
import common.transportObjects.FileData;
import lombok.Getter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Constants.MAX_ATTACHMENT_FILE_NAME_LENGTH;
import static app.Constants.MAX_RECEIVED_FILE_NAME_LENGTH;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;
import static app.gui.chat.buttons.ButtonController.getButtonController;
import static app.gui.chat.textFields.TextFieldController.getTextFieldController;
import static app.gui.chat.texts.TextController.getTextController;
import static common.CipherConfig.CIPHER_BLOCK_SIZE;

public class FileController {

    private static FileController fileController;

    @Getter
    private FileData attachedFile;
    private final List<FileData> receivedFiles;
    private final Map<Long, Map<Integer, byte[]>> receivedFileFragments;

    private FileController() {
        this.receivedFiles = new ArrayList<>();
        this.receivedFileFragments = new HashMap<>();
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
            getInput().resetMouseListener();
            getInput().resetKeyboardListener();
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
            getInput().resetMouseListener();
            getInput().resetKeyboardListener();
        }
    }

    public void addReceivedFile(FileData fileData) {
        this.receivedFiles.add(fileData);
        String trimmedFileName = TrimmedStringFactory.trimString(fileData.getName(), MAX_RECEIVED_FILE_NAME_LENGTH);
        String filenameWithExtension = trimmedFileName + " (" + fileData.getExtension() + ")";
        getButtonController().addReceivedFile(filenameWithExtension, fileData.hashCode());
    }

    public void sendFileOrText() {
        if (getButtonController().getSelectedReceiverId() != null && getButtonController().getCipherType() != null) {
            updateUploadInfo(0.0);
            if (attachedFile != null) {

            } else {

            }
        }
    }

    public void updateUploadInfo(double percentage) {
        getTextController().setCurrentUploadInfoAsProgress(percentage);
    }

    public void sendConfirmation(int fragmentNumber, Long senderId) {

    }

    public void receiveText(byte[] binaryText, Long senderId) {
        try {
            //decrypt
            String text = Serializer.deserialize(binaryText);
            getButtonController().addReceivedText(text);
            sendConfirmation(0, senderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveFileFragment(Long senderId, int fileFragmentNumber, int numberOfFileFragments, int rawFileSize, byte[] fileFragment) {
        if (!this.receivedFileFragments.containsKey(senderId)) {
            this.receivedFileFragments.put(senderId, new HashMap<>());
            sendConfirmation(fileFragmentNumber, senderId);
        }
        this.receivedFileFragments.get(senderId).put(fileFragmentNumber, fileFragment);
        if (this.receivedFileFragments.get(senderId).size() == numberOfFileFragments) {
            FileData receivedFile = restoreFile(numberOfFileFragments, rawFileSize, senderId);
            addReceivedFile(receivedFile);
        }
    }

    private FileData restoreFile(int numberOfFileFragments, int rawFileSize, Long senderId) {
        try {
            List<byte[]> fileFragments = new ArrayList<>();
            for (int i = 0; i < numberOfFileFragments; i++) {
                fileFragments.add(this.receivedFileFragments.get(senderId).get(i));
            }
            this.receivedFileFragments.remove(senderId);
            byte[] binaryFile = new byte[rawFileSize];
            for (int i = 0; i < numberOfFileFragments; i++) {
                for (int j = 0; j < CIPHER_BLOCK_SIZE && i * CIPHER_BLOCK_SIZE + j < rawFileSize; j++) {
                    //decrypt
                    binaryFile[i * CIPHER_BLOCK_SIZE] = fileFragments.get(i)[j];
                }
            }
            return Serializer.deserialize(binaryFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

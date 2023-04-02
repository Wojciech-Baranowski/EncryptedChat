package app.services;

import app.connection.ConnectionController;
import app.utils.TrimmedStringFactory;
import common.CipherConfig;
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
import static app.gui.chat.buttons.ChatButtonController.getChatButtonController;
import static app.gui.chat.textFields.ChatTextFieldController.getChatTextFieldController;
import static app.gui.chat.texts.ChatTextController.getChatTextController;
import static common.CipherConfig.CIPHER_BLOCK_SIZE;

public class FileService {

    private static FileService fileService;

    @Getter
    private FileData attachedFile;
    private final List<FileData> receivedFiles;
    private final Map<Integer, byte[]> receivedFileFragments;
    private int numberOfFileFragmentsToReceive;
    private final Map<Integer, Boolean> receivedConfirmations;

    private FileService() {
        this.receivedFiles = new ArrayList<>();
        this.receivedFileFragments = new HashMap<>();
        this.receivedConfirmations = new HashMap<>();
        this.numberOfFileFragmentsToReceive = 0;
    }

    public static FileService getFileService() {
        if (fileService == null) {
            fileService = new FileService();
        }
        return fileService;
    }

    public void attachFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.attachedFile = new FileData(chooser.getSelectedFile());
            this.receivedFileFragments.clear();
            this.receivedConfirmations.clear();
            String trimmedFileName = TrimmedStringFactory.trimString(this.attachedFile.getName(), MAX_ATTACHMENT_FILE_NAME_LENGTH);
            getChatTextController().setFileName(trimmedFileName);
            getChatTextController().setFileExtension(this.attachedFile.getExtension());
            getChatTextController().setFileSize(this.attachedFile.getSize().toString());
            getChatTextController().setCurrentUploadInfoAsReadyToUpload();
            getScene().removeObject(getChatTextFieldController().getMessageTextField());
            getInput().resetInputListener();
        }
    }

    public void detachFile() {
        this.attachedFile = null;
        getChatTextController().resetFileName();
        getChatTextController().resetFileExtension();
        getChatTextController().resetFileSize();
        getChatTextController().setCurrentUploadInfoAsBlank();
        getScene().addOnHighest(getChatTextFieldController().getMessageTextField());
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
            getInput().resetInputListener();
        }
    }

    public void addReceivedFile(FileData fileData) {
        this.receivedFiles.add(fileData);
        String trimmedFileName = TrimmedStringFactory.trimString(fileData.getName(), MAX_RECEIVED_FILE_NAME_LENGTH);
        String filenameWithExtension = trimmedFileName + " (" + fileData.getExtension() + ")";
        getChatButtonController().addReceivedFile(filenameWithExtension, fileData.hashCode());
    }

    public void updateUploadInfo(double percentage) {
        getChatTextController().setCurrentUploadInfoAsProgress(percentage);
    }

    public void sendFileOrText() {
        if (getChatButtonController().getSelectedReceiverId() != null && getChatButtonController().getCipherType() != null) {
            updateUploadInfo(0.0);
            if (this.attachedFile != null) {

            } else {

            }
        }
    }

    public void sendConfirmation(int fragmentNumber, Long receiverId) {
        ConnectionController.getChatConnectionController().prepareAndSendConfirmationMessage(fragmentNumber, receiverId);
    }

    public void receiveConfirmation(int fragmentNumber) {
        this.receivedConfirmations.put(fragmentNumber, true);
        updateUploadInfo(this.receivedConfirmations.size() / (double) this.numberOfFileFragmentsToReceive);
    }

    public void receiveText(Long senderId, CipherConfig.CipherType cipherType, byte[] binaryText) {
        try {
            //decryptAES
            String text = Serializer.deserialize(binaryText);
            getChatButtonController().addReceivedText(text);
            sendConfirmation(0, senderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveFileFragment(Long senderId, CipherConfig.CipherType cipherType, int fileFragmentNumber, int numberOfFileFragments, byte[] fileFragment) {
        this.receivedFileFragments.put(fileFragmentNumber, fileFragment);
        sendConfirmation(fileFragmentNumber, senderId);
        if (this.receivedFileFragments.size() == numberOfFileFragments) {
            FileData receivedFile = restoreFile(numberOfFileFragments, senderId, cipherType);
            addReceivedFile(receivedFile);
        }
    }

    private FileData restoreFile(int numberOfFileFragments, Long senderId, CipherConfig.CipherType cipherType) {
        try {
            List<byte[]> fileFragments = new ArrayList<>();
            int fileSize = 0;
            for (int i = 0; i < numberOfFileFragments; i++) {
                byte[] fileFragment;
                //decryptAES
                fileFragment = trimFileFragment(this.receivedFileFragments.get(i));
                fileSize += fileFragment.length;
                fileFragments.add(fileFragment);
            }
            this.receivedFileFragments.clear();
            byte[] mergedFile = mergeFileFragments(fileFragments, fileSize);
            return Serializer.deserialize(mergedFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] mergeFileFragments(List<byte[]> fileFragments, int fileSize) {
        byte[] binaryFile = new byte[fileSize];
        for (int iterator = 0, i = 0; i < fileFragments.size(); i++) {
            System.arraycopy(fileFragments.get(i), 0, binaryFile, iterator, fileFragments.get(i).length);
            iterator += fileFragments.get(i).length;
        }
        return binaryFile;
    }

    private byte[] trimFileFragment(byte[] fileFragment) {
        int size = getFragmentSize(fileFragment);
        byte[] trimmedBinaryFile = new byte[size];
        System.arraycopy(fileFragment, 0, trimmedBinaryFile, 0, size);
        return trimmedBinaryFile;
    }

    private int getFragmentSize(byte[] fileFragment) {
        int fragmentSize = CIPHER_BLOCK_SIZE;
        while (fileFragment[fragmentSize - 1] == 0) {
            fragmentSize--;
        }
        return fragmentSize - 1;
    }

}

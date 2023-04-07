package app.gui.chat.buttons;

import app.commands.InitiateHandshakeCommand;
import app.engine.button.radioButton.CommandRadioButton;
import app.engine.button.radioButton.RadioButton;
import app.engine.button.radioButton.RadioButtonBundle;
import app.engine.display.Drawable;
import app.engine.display.DrawableComposition;
import app.engine.input.inputCombination.InputCombination;
import common.transportObjects.UserData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static app.Constants.MAX_CLIENTS;
import static app.engine.display.DisplayBean.getDisplay;
import static app.engine.input.InputBean.getInput;
import static app.engine.scene.SceneBean.getScene;

public class ReceiversButton {

    private RadioButtonBundle receiversBundle;
    private final RadioButton[] receivers;
    private final Long[] receiversIds;
    private final Drawable background;

    public ReceiversButton(Drawable background) {
        this.receivers = new RadioButton[MAX_CLIENTS - 1];
        this.receiversIds = new Long[MAX_CLIENTS - 1];
        this.receiversBundle = new RadioButtonBundle(List.of(), true);
        this.background = background;
    }

    public void addReceiver(UserData userData) {
        int index = 0;
        for (int i = 0; i < MAX_CLIENTS - 1; i++) {
            if (this.receiversIds[i] == null) {
                index = i;
                break;
            }
        }
        Drawable offDrawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                this.background.getX() + 2,
                this.background.getY() + 2 + (30 * index),
                196,
                30,
                2,
                (index % 2 == 0) ? "gray" : "lightBlue",
                "lighterBlue"
        );
        Drawable onDrawable = getDisplay().getDrawableFactory().makeFramedRectangle(
                this.background.getX() + 2,
                this.background.getY() + 2 + (30 * index),
                196,
                30,
                2,
                (index % 2 == 0) ? "gray" : "lightBlue",
                "yellow"
        );
        Drawable userName = getDisplay().getDrawableFactory().makeText(
                userData.getUserName() + " (" + userData.getId() + ")",
                this.background.getX() + 5,
                this.background.getY() + 5 + (30 * index),
                "HBE24",
                "black"
        );
        offDrawable = new DrawableComposition(offDrawable, userName);
        onDrawable = new DrawableComposition(onDrawable, userName);
        InputCombination activationCombination = getInput().getInputCombinationFactory().makeLmbCombination();
        InitiateHandshakeCommand command = new InitiateHandshakeCommand(userData.getId());
        CommandRadioButton receiver = new CommandRadioButton(offDrawable, onDrawable, activationCombination, command);
        getScene().addOnHighest(receiver);
        this.receivers[index] = receiver;
        this.receiversIds[index] = userData.getId();
        buildBundle();
    }

    public void removeReceiver(Long receiverId) {
        int index = -1;
        for (int i = 0; i < MAX_CLIENTS - 1; i++) {
            if (Objects.equals(this.receiversIds[i], receiverId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            getScene().removeObject(this.receivers[index]);
            this.receivers[index] = null;
            this.receiversIds[index] = null;
            buildBundle();
        }
    }

    public Long getSelectedReceiverId() {
        if (this.receiversBundle.getSelectedRadioButtonIndex() >= 0) {
            return this.receiversIds[this.receiversBundle.getSelectedRadioButtonIndex()];
        }
        return null;
    }

    private void buildBundle() {
        int selectedReceiver = this.receiversBundle.getSelectedRadioButtonIndex();
        List<RadioButton> activeReceivers = Arrays.stream(this.receivers)
                .filter(Objects::nonNull)
                .toList();
        this.receiversBundle = new RadioButtonBundle(activeReceivers, selectedReceiver);
    }

}

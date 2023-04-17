package app.engine.button.radioButton;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class RadioButtonBundle {

    private final List<RadioButton> radioButtons;
    @Getter
    private RadioButton selectedRadioButton;
    private boolean unselectable;

    public RadioButtonBundle(boolean unselectable) {
        this.radioButtons = new LinkedList<>();
        this.unselectable = false;
    }

    public RadioButtonBundle(List<RadioButton> radioButtons, boolean unselectable) {
        this.radioButtons = radioButtons;
        this.unselectable = false;
        for (RadioButton radioButton : radioButtons) {
            radioButton.setRadioButtonBundle(this);
        }
    }

    public RadioButtonBundle(List<RadioButton> radioButtons, int selectedRadioButtonIndex) {
        this.radioButtons = radioButtons;
        for (RadioButton radioButton : radioButtons) {
            radioButton.setRadioButtonBundle(this);
        }
        if (selectedRadioButtonIndex >= radioButtons.size()) {
            selectedRadioButtonIndex = -1;
        }
        if (selectedRadioButtonIndex != -1) {
            this.selectedRadioButton = radioButtons.get(selectedRadioButtonIndex);
        }
    }

    public void update(RadioButton currentlySelected) {
        if (this.selectedRadioButton != null && this.selectedRadioButton != currentlySelected) {
            this.selectedRadioButton.setSelected(false);
        }
        if (currentlySelected.isSelected() && this.unselectable) {
            currentlySelected.setSelected(false);
            this.selectedRadioButton = null;
        } else {
            currentlySelected.setSelected(true);
            this.selectedRadioButton = currentlySelected;
        }
    }

    public void unset() {
        if (this.selectedRadioButton != null) {
            this.selectedRadioButton.setSelected(false);
            this.selectedRadioButton = null;
        }
    }

    public List<Boolean> getBundleState() {
        return this.radioButtons.stream()
                .map(RadioButton::isSelected)
                .toList();
    }

    public int getSelectedRadioButtonIndex() {
        for (int i = 0; i < this.radioButtons.size(); i++) {
            if (this.radioButtons.get(i).equals(this.selectedRadioButton)) {
                return i;
            }
        }
        return -1;
    }

    public void selectAnyRadioButton() {
        this.selectedRadioButton = this.radioButtons.get(0);
    }

    public void addRadioButton(RadioButton radioButton) {
        if (this.radioButtons.contains(radioButton)) {
            return;
        }
        radioButton.setRadioButtonBundle(this);
        this.radioButtons.add(radioButton);
    }

    public void removeRadioButton(RadioButton radioButton) {
        this.radioButtons.remove(radioButton);
    }

}

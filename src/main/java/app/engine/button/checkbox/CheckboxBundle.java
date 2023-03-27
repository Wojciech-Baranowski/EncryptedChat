package app.engine.button.checkbox;

import java.util.LinkedList;
import java.util.List;

public class CheckboxBundle {

    private final List<Checkbox> checkboxes;

    public CheckboxBundle() {
        this.checkboxes = new LinkedList<>();
    }

    public CheckboxBundle(List<Checkbox> checkboxes) {
        this.checkboxes = checkboxes;
    }

    public List<Boolean> getBundleState() {
        return this.checkboxes.stream()
                .map(Checkbox::isSelected)
                .toList();
    }

    public void addCheckbox(Checkbox checkbox) {
        if (this.checkboxes.contains(checkbox)) {
            return;
        }
        this.checkboxes.add(checkbox);
    }

    public void removeCheckbox(Checkbox checkbox) {
        this.checkboxes.remove(checkbox);
    }

}

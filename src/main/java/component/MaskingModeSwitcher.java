package component;

import settings.Keys;
import settings.SettingsProvider;

import javax.swing.*;

public class MaskingModeSwitcher extends JButton {

    private static final ImageIcon AREA_COLORED_ICON = new ImageIcon(MaskingModeSwitcher.class.getResource("/icons/color_area.png"));
    private static final ImageIcon AREA_GREY_ICON = new ImageIcon(MaskingModeSwitcher.class.getResource("/icons/grey_area.png"));

    public MaskingModeSwitcher() {
        super(Boolean.parseBoolean(SettingsProvider.getSetting(Keys.IS_AREA_COLORED)) ? AREA_COLORED_ICON : AREA_GREY_ICON);
        setSelected(Boolean.parseBoolean(SettingsProvider.getSetting(Keys.IS_AREA_COLORED)));

        addActionListener(e -> {
            setSelected(!isSelected());
            setIcon(isSelected() ? AREA_COLORED_ICON : AREA_GREY_ICON);
            SettingsProvider.setSetting(Keys.IS_AREA_COLORED, isSelected());
        });

        setToolTipText("Invert mask for selected area");
    }
}

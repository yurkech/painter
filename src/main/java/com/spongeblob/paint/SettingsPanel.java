package com.spongeblob.paint;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.spongeblob.paint.settings.Settings;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7136804231521726408L;
	private Map<String, Settings> settings;
	private CanvasPanel canvasPanel;

	public void activateSettings(Map<String, Settings> settings) {
		this.settings = settings;
		update();
	}

	public void activateSettings(String key, Settings settings) {
		this.settings = new HashMap<String, Settings>();
		this.settings.put(key, settings);
		update();
	}

	public void update() {
		if (settings != null) {
			removeAll();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			List<Settings> list = new LinkedList<Settings>();
			list.addAll(settings.values());
			Collections.sort(list, new Comparator<Settings>() {

				public int compare(Settings o1, Settings o2) {
					return o1.getTitle().compareTo(o2.getTitle());
				}
			});
			for (Settings item : list) {
				item.setSettingsPanel(this);
				item.activate();
			}
		}
		updateUI();
	}

	public CanvasPanel getCanvasPanel() {
		return canvasPanel;
	}

	public void setCanvasPanel(CanvasPanel canvasPanel) {
		this.canvasPanel = canvasPanel;
	}

	public void updateDrawing() {
		this.canvasPanel.repaint();
	}
}

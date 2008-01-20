/*
 * Jastrology - Copyright 2006 Fengbo Xie, All Rights Reserved.
 *
 * http://www.ivstars.com/jastrology
 * http://sourceforge.net/projects/jastrology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ivstars.astrology.gui;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

/**
 * ImageFilePreviewer is a simple component to previewer image files
 *  @author Fengbo Xie (fengbo.xie at gmail.com)
 *         Everything that has a beginning has an end.
 */
public class ImageFilePreviewer
	extends JComponent
	implements PropertyChangeListener {

	ImageIcon thumbnail = null;

	public ImageFilePreviewer(JFileChooser fc) {
		setPreferredSize(new Dimension(100, 50));
		fc.addPropertyChangeListener(this);
	}

	public void loadImage(File f) {
		if (f == null) {
			thumbnail = null;
		} else {
			ImageIcon tmpIcon = new ImageIcon(f.getPath());
			if (tmpIcon.getIconWidth() > 90) {
				thumbnail =
					new ImageIcon(
						tmpIcon.getImage().getScaledInstance(
							90,
							-1,
							Image.SCALE_DEFAULT));
			} else {
				thumbnail = tmpIcon;
			}
		}
	}

	public void paint(Graphics g) {
		if (thumbnail != null) {
			int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
			int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;
			if (y < 0) {
				y = 0;
			}

			if (x < 5) {
				x = 5;
			}
			thumbnail.paintIcon(this, g, x, y);
		}
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
			if (isShowing()) {
				loadImage((File) evt.getNewValue());
				repaint();
			}
		}
	}

}

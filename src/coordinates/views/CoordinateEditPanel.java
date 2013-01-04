/*
 * The MIT License
 *
 * Copyright 2013 Erik Brännström <erik.brannstrom@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package coordinates.views;

import coordinates.models.Coordinate;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Erik Brännström <erik.brannstrom@gmail.com>
 */
public final class CoordinateEditPanel extends JPanel{
	private Coordinate coordinate;
	private CoordinatesView coordinatesView;
	
	private JTextField txtX, txtY, txtZ;
	private JButton btnSave;
	
	public CoordinateEditPanel(Coordinate coordinate)
	{
		super();
		this.txtX = new JTextField();
		this.txtY = new JTextField();
		this.txtZ = new JTextField();
		this.btnSave = new JButton("Save");
		this.setLayout(new MigLayout("fill, aligny top", "[align left]5[align left]"));
		this.add(new JLabel("X:"));
		this.add(this.txtX, "wrap, wmin 100");
		this.add(new JLabel("Y:"));
		this.add(this.txtY, "wrap, wmin 100");
		this.add(new JLabel("Z:"));
		this.add(this.txtZ, "wrap, wmin 100");
		this.add(this.btnSave);
		
		this.btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				CoordinateEditPanel.this.save();
			}

		});
		
		this.setCoordinate(coordinate);
	}

	public CoordinateEditPanel()
	{
		this(null);
	}
	
	public void setCoordinate(Coordinate coordinate)
	{
		if (coordinate == null) {
			coordinate = new Coordinate(0, 0, 0);
		}
		this.coordinate = coordinate;
		this.txtX.setText(Double.toString(coordinate.x()));
		this.txtY.setText(Double.toString(coordinate.y()));
		this.txtZ.setText(Double.toString(coordinate.z()));
	}

	public void setCoordinatesView(CoordinatesView coordinatesView)
	{
		this.coordinatesView = coordinatesView;
	}

	private void save()
	{
		double x = Double.parseDouble(this.txtX.getText());
		double y = Double.parseDouble(this.txtY.getText());
		double z= Double.parseDouble(this.txtZ.getText());
		this.coordinate.x(x);
		this.coordinate.y(y);
		this.coordinate.z(z);
		if (coordinatesView != null) {
			coordinatesView.repaint();
		}
	}
	
}

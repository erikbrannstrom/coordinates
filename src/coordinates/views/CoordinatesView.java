/*
 * The MIT License
 *
 * Copyright 2012 Erik Brännström <erik.brannstrom@gmail.com>.
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

import coordinates.controllers.CoordinatesListener;
import coordinates.models.Coordinate;
import coordinates.models.Coordinates;
import coordinates.views.Coordinate2D;
import coordinates.views.CoordinateLine2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Erik Brännström <erik.brannstrom@gmail.com>
 */
public class CoordinatesView extends JPanel implements MouseListener, CoordinatesListener {

	private static final int MARGIN = 5;
	private static final int POINT_SIZE = 6;
	private static final int HIT_BOX_SIZE = 4;
	private Coordinates coordinates;
	private List<CoordinateLine2D> lines;
	private List<Coordinate2D> coordinateShapes;
	private Shape currentSelection;
	private CoordinateEditPanel coordinateEditPanel;

	public CoordinatesView(Coordinates coordinates, CoordinateEditPanel coordinateEditPanel)
	{
		super();
		this.coordinates = coordinates;
		this.coordinateEditPanel = coordinateEditPanel;
		this.resetShapes();
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
	}

	public Shape getCurrentSelection()
	{
		return currentSelection;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double xScale = (this.getWidth() - 10) / this.coordinates.xMax();
		double yScale = -(this.getHeight() - 10) / this.coordinates.yMax();
		for (CoordinateLine2D line : this.lines) {
			int xFrom = MARGIN + (int) (line.from().x() * xScale);
			int yFrom = this.getHeight() - MARGIN + (int) (line.from().y() * yScale);
			int xTo = MARGIN + (int) (line.to().x() * xScale);
			int yTo = this.getHeight() - MARGIN + (int) (line.to().y() * yScale);
			line.setLine(xFrom, yFrom, xTo, yTo);
			if (line == this.currentSelection) {
				g2d.setColor(Color.RED);
			}
			g2d.draw(line);
			g2d.setColor(Color.BLACK);
		}
		for (Coordinate2D c2d : this.coordinateShapes) {
			int y = this.getHeight() - MARGIN + (int) (c2d.coordinate().y() * yScale) - (POINT_SIZE / 2);
			int x = MARGIN + (int) (c2d.coordinate().x() * xScale) - (POINT_SIZE / 2);
			c2d.setRect(x, y, POINT_SIZE, POINT_SIZE);
			if (c2d == this.currentSelection) {
				g2d.setColor(Color.RED);
			}
			g2d.fill(c2d);
			g2d.setColor(Color.BLACK);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		for (Coordinate2D coordinate2D : this.coordinateShapes) {
			if (coordinate2D.contains(e.getPoint())) {
				this.currentSelection = coordinate2D;
				this.coordinateEditPanel.setCoordinate(coordinate2D.coordinate());
				this.repaint();
				return;
			}
		}
		for (CoordinateLine2D line : this.lines) {
			int x = e.getPoint().x - HIT_BOX_SIZE / 2;
			int y = e.getPoint().y - HIT_BOX_SIZE / 2;
			if (line.intersects(new Rectangle2D.Double(x, y, HIT_BOX_SIZE, HIT_BOX_SIZE))) {
				this.currentSelection = line;
				this.repaint();
				return;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void coordinatesUpdated()
	{
		this.resetShapes();
		this.repaint();
	}

	private void resetShapes()
	{
		this.coordinateShapes = new LinkedList<>();
		this.lines = new LinkedList<>();
		for (Coordinate coordinate : this.coordinates) {
			this.coordinateShapes.add(new Coordinate2D(coordinate));
			for (Coordinate lineTo : coordinate.linesTo()) {
				this.lines.add(new CoordinateLine2D(coordinate, lineTo));
			}
		}
	}
}

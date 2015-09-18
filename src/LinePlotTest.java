package src;

import java.awt.Color;
import javax.swing.JFrame;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class LinePlotTest extends JFrame
{
	public LinePlotTest(DataTable data)
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 1000);

//		DataTable data = new DataTable(Double.class, Double.class);
//		for (double x = -5.0; x <= 5.0; x += 0.25)
//		{
//			double y = 5.0 * Math.sin(x);
//			data.add(x, y);
//		}
		XYPlot plot = new XYPlot(data);
		getContentPane().add(new InteractivePanel(plot));
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(data, lines);
//		Color color = new Color(0.0f, 0.3f, 1.0f);
//		plot.getPointRenderer(data).setColor(color);
//		plot.getLineRenderer(data).setColor(color);
	}

//	public static void main(String[] args)
//	{
//		LinePlotTest frame = new LinePlotTest();
//		frame.setVisible(true);
//	}
}